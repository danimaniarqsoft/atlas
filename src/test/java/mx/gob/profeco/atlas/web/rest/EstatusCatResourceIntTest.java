package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.EstatusCat;
import mx.gob.profeco.atlas.repository.EstatusCatRepository;
import mx.gob.profeco.atlas.service.EstatusCatService;
import mx.gob.profeco.atlas.repository.search.EstatusCatSearchRepository;
import mx.gob.profeco.atlas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstatusCatResource REST controller.
 *
 * @see EstatusCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class EstatusCatResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private EstatusCatRepository estatusCatRepository;

    @Autowired
    private EstatusCatService estatusCatService;

    @Autowired
    private EstatusCatSearchRepository estatusCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstatusCatMockMvc;

    private EstatusCat estatusCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EstatusCatResource estatusCatResource = new EstatusCatResource(estatusCatService);
        this.restEstatusCatMockMvc = MockMvcBuilders.standaloneSetup(estatusCatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusCat createEntity(EntityManager em) {
        EstatusCat estatusCat = new EstatusCat()
            .nombre(DEFAULT_NOMBRE)
            .activo(DEFAULT_ACTIVO);
        return estatusCat;
    }

    @Before
    public void initTest() {
        estatusCatSearchRepository.deleteAll();
        estatusCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusCat() throws Exception {
        int databaseSizeBeforeCreate = estatusCatRepository.findAll().size();

        // Create the EstatusCat
        restEstatusCatMockMvc.perform(post("/api/estatus-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCat)))
            .andExpect(status().isCreated());

        // Validate the EstatusCat in the database
        List<EstatusCat> estatusCatList = estatusCatRepository.findAll();
        assertThat(estatusCatList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusCat testEstatusCat = estatusCatList.get(estatusCatList.size() - 1);
        assertThat(testEstatusCat.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstatusCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the EstatusCat in Elasticsearch
        EstatusCat estatusCatEs = estatusCatSearchRepository.findOne(testEstatusCat.getId());
        assertThat(estatusCatEs).isEqualToComparingFieldByField(testEstatusCat);
    }

    @Test
    @Transactional
    public void createEstatusCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusCatRepository.findAll().size();

        // Create the EstatusCat with an existing ID
        estatusCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusCatMockMvc.perform(post("/api/estatus-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EstatusCat> estatusCatList = estatusCatRepository.findAll();
        assertThat(estatusCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstatusCats() throws Exception {
        // Initialize the database
        estatusCatRepository.saveAndFlush(estatusCat);

        // Get all the estatusCatList
        restEstatusCatMockMvc.perform(get("/api/estatus-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getEstatusCat() throws Exception {
        // Initialize the database
        estatusCatRepository.saveAndFlush(estatusCat);

        // Get the estatusCat
        restEstatusCatMockMvc.perform(get("/api/estatus-cats/{id}", estatusCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusCat.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstatusCat() throws Exception {
        // Get the estatusCat
        restEstatusCatMockMvc.perform(get("/api/estatus-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusCat() throws Exception {
        // Initialize the database
        estatusCatService.save(estatusCat);

        int databaseSizeBeforeUpdate = estatusCatRepository.findAll().size();

        // Update the estatusCat
        EstatusCat updatedEstatusCat = estatusCatRepository.findOne(estatusCat.getId());
        updatedEstatusCat
            .nombre(UPDATED_NOMBRE)
            .activo(UPDATED_ACTIVO);

        restEstatusCatMockMvc.perform(put("/api/estatus-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstatusCat)))
            .andExpect(status().isOk());

        // Validate the EstatusCat in the database
        List<EstatusCat> estatusCatList = estatusCatRepository.findAll();
        assertThat(estatusCatList).hasSize(databaseSizeBeforeUpdate);
        EstatusCat testEstatusCat = estatusCatList.get(estatusCatList.size() - 1);
        assertThat(testEstatusCat.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstatusCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the EstatusCat in Elasticsearch
        EstatusCat estatusCatEs = estatusCatSearchRepository.findOne(testEstatusCat.getId());
        assertThat(estatusCatEs).isEqualToComparingFieldByField(testEstatusCat);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusCat() throws Exception {
        int databaseSizeBeforeUpdate = estatusCatRepository.findAll().size();

        // Create the EstatusCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstatusCatMockMvc.perform(put("/api/estatus-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCat)))
            .andExpect(status().isCreated());

        // Validate the EstatusCat in the database
        List<EstatusCat> estatusCatList = estatusCatRepository.findAll();
        assertThat(estatusCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstatusCat() throws Exception {
        // Initialize the database
        estatusCatService.save(estatusCat);

        int databaseSizeBeforeDelete = estatusCatRepository.findAll().size();

        // Get the estatusCat
        restEstatusCatMockMvc.perform(delete("/api/estatus-cats/{id}", estatusCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean estatusCatExistsInEs = estatusCatSearchRepository.exists(estatusCat.getId());
        assertThat(estatusCatExistsInEs).isFalse();

        // Validate the database is empty
        List<EstatusCat> estatusCatList = estatusCatRepository.findAll();
        assertThat(estatusCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEstatusCat() throws Exception {
        // Initialize the database
        estatusCatService.save(estatusCat);

        // Search the estatusCat
        restEstatusCatMockMvc.perform(get("/api/_search/estatus-cats?query=id:" + estatusCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusCat.class);
        EstatusCat estatusCat1 = new EstatusCat();
        estatusCat1.setId(1L);
        EstatusCat estatusCat2 = new EstatusCat();
        estatusCat2.setId(estatusCat1.getId());
        assertThat(estatusCat1).isEqualTo(estatusCat2);
        estatusCat2.setId(2L);
        assertThat(estatusCat1).isNotEqualTo(estatusCat2);
        estatusCat1.setId(null);
        assertThat(estatusCat1).isNotEqualTo(estatusCat2);
    }
}
