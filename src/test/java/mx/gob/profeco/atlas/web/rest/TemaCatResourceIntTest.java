package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.TemaCat;
import mx.gob.profeco.atlas.repository.TemaCatRepository;
import mx.gob.profeco.atlas.service.TemaCatService;
import mx.gob.profeco.atlas.repository.search.TemaCatSearchRepository;
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
 * Test class for the TemaCatResource REST controller.
 *
 * @see TemaCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class TemaCatResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private TemaCatRepository temaCatRepository;

    @Autowired
    private TemaCatService temaCatService;

    @Autowired
    private TemaCatSearchRepository temaCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemaCatMockMvc;

    private TemaCat temaCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TemaCatResource temaCatResource = new TemaCatResource(temaCatService);
        this.restTemaCatMockMvc = MockMvcBuilders.standaloneSetup(temaCatResource)
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
    public static TemaCat createEntity(EntityManager em) {
        TemaCat temaCat = new TemaCat()
            .nombre(DEFAULT_NOMBRE)
            .activo(DEFAULT_ACTIVO);
        return temaCat;
    }

    @Before
    public void initTest() {
        temaCatSearchRepository.deleteAll();
        temaCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemaCat() throws Exception {
        int databaseSizeBeforeCreate = temaCatRepository.findAll().size();

        // Create the TemaCat
        restTemaCatMockMvc.perform(post("/api/tema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaCat)))
            .andExpect(status().isCreated());

        // Validate the TemaCat in the database
        List<TemaCat> temaCatList = temaCatRepository.findAll();
        assertThat(temaCatList).hasSize(databaseSizeBeforeCreate + 1);
        TemaCat testTemaCat = temaCatList.get(temaCatList.size() - 1);
        assertThat(testTemaCat.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTemaCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the TemaCat in Elasticsearch
        TemaCat temaCatEs = temaCatSearchRepository.findOne(testTemaCat.getId());
        assertThat(temaCatEs).isEqualToComparingFieldByField(testTemaCat);
    }

    @Test
    @Transactional
    public void createTemaCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temaCatRepository.findAll().size();

        // Create the TemaCat with an existing ID
        temaCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemaCatMockMvc.perform(post("/api/tema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TemaCat> temaCatList = temaCatRepository.findAll();
        assertThat(temaCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemaCats() throws Exception {
        // Initialize the database
        temaCatRepository.saveAndFlush(temaCat);

        // Get all the temaCatList
        restTemaCatMockMvc.perform(get("/api/tema-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTemaCat() throws Exception {
        // Initialize the database
        temaCatRepository.saveAndFlush(temaCat);

        // Get the temaCat
        restTemaCatMockMvc.perform(get("/api/tema-cats/{id}", temaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(temaCat.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTemaCat() throws Exception {
        // Get the temaCat
        restTemaCatMockMvc.perform(get("/api/tema-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemaCat() throws Exception {
        // Initialize the database
        temaCatService.save(temaCat);

        int databaseSizeBeforeUpdate = temaCatRepository.findAll().size();

        // Update the temaCat
        TemaCat updatedTemaCat = temaCatRepository.findOne(temaCat.getId());
        updatedTemaCat
            .nombre(UPDATED_NOMBRE)
            .activo(UPDATED_ACTIVO);

        restTemaCatMockMvc.perform(put("/api/tema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemaCat)))
            .andExpect(status().isOk());

        // Validate the TemaCat in the database
        List<TemaCat> temaCatList = temaCatRepository.findAll();
        assertThat(temaCatList).hasSize(databaseSizeBeforeUpdate);
        TemaCat testTemaCat = temaCatList.get(temaCatList.size() - 1);
        assertThat(testTemaCat.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTemaCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the TemaCat in Elasticsearch
        TemaCat temaCatEs = temaCatSearchRepository.findOne(testTemaCat.getId());
        assertThat(temaCatEs).isEqualToComparingFieldByField(testTemaCat);
    }

    @Test
    @Transactional
    public void updateNonExistingTemaCat() throws Exception {
        int databaseSizeBeforeUpdate = temaCatRepository.findAll().size();

        // Create the TemaCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemaCatMockMvc.perform(put("/api/tema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(temaCat)))
            .andExpect(status().isCreated());

        // Validate the TemaCat in the database
        List<TemaCat> temaCatList = temaCatRepository.findAll();
        assertThat(temaCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTemaCat() throws Exception {
        // Initialize the database
        temaCatService.save(temaCat);

        int databaseSizeBeforeDelete = temaCatRepository.findAll().size();

        // Get the temaCat
        restTemaCatMockMvc.perform(delete("/api/tema-cats/{id}", temaCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean temaCatExistsInEs = temaCatSearchRepository.exists(temaCat.getId());
        assertThat(temaCatExistsInEs).isFalse();

        // Validate the database is empty
        List<TemaCat> temaCatList = temaCatRepository.findAll();
        assertThat(temaCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTemaCat() throws Exception {
        // Initialize the database
        temaCatService.save(temaCat);

        // Search the temaCat
        restTemaCatMockMvc.perform(get("/api/_search/tema-cats?query=id:" + temaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemaCat.class);
        TemaCat temaCat1 = new TemaCat();
        temaCat1.setId(1L);
        TemaCat temaCat2 = new TemaCat();
        temaCat2.setId(temaCat1.getId());
        assertThat(temaCat1).isEqualTo(temaCat2);
        temaCat2.setId(2L);
        assertThat(temaCat1).isNotEqualTo(temaCat2);
        temaCat1.setId(null);
        assertThat(temaCat1).isNotEqualTo(temaCat2);
    }
}
