package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.PalabraClaveCat;
import mx.gob.profeco.atlas.repository.PalabraClaveCatRepository;
import mx.gob.profeco.atlas.service.PalabraClaveCatService;
import mx.gob.profeco.atlas.repository.search.PalabraClaveCatSearchRepository;
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
 * Test class for the PalabraClaveCatResource REST controller.
 *
 * @see PalabraClaveCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class PalabraClaveCatResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private PalabraClaveCatRepository palabraClaveCatRepository;

    @Autowired
    private PalabraClaveCatService palabraClaveCatService;

    @Autowired
    private PalabraClaveCatSearchRepository palabraClaveCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPalabraClaveCatMockMvc;

    private PalabraClaveCat palabraClaveCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PalabraClaveCatResource palabraClaveCatResource = new PalabraClaveCatResource(palabraClaveCatService);
        this.restPalabraClaveCatMockMvc = MockMvcBuilders.standaloneSetup(palabraClaveCatResource)
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
    public static PalabraClaveCat createEntity(EntityManager em) {
        PalabraClaveCat palabraClaveCat = new PalabraClaveCat()
            .nombre(DEFAULT_NOMBRE)
            .activo(DEFAULT_ACTIVO);
        return palabraClaveCat;
    }

    @Before
    public void initTest() {
        palabraClaveCatSearchRepository.deleteAll();
        palabraClaveCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createPalabraClaveCat() throws Exception {
        int databaseSizeBeforeCreate = palabraClaveCatRepository.findAll().size();

        // Create the PalabraClaveCat
        restPalabraClaveCatMockMvc.perform(post("/api/palabra-clave-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(palabraClaveCat)))
            .andExpect(status().isCreated());

        // Validate the PalabraClaveCat in the database
        List<PalabraClaveCat> palabraClaveCatList = palabraClaveCatRepository.findAll();
        assertThat(palabraClaveCatList).hasSize(databaseSizeBeforeCreate + 1);
        PalabraClaveCat testPalabraClaveCat = palabraClaveCatList.get(palabraClaveCatList.size() - 1);
        assertThat(testPalabraClaveCat.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPalabraClaveCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the PalabraClaveCat in Elasticsearch
        PalabraClaveCat palabraClaveCatEs = palabraClaveCatSearchRepository.findOne(testPalabraClaveCat.getId());
        assertThat(palabraClaveCatEs).isEqualToComparingFieldByField(testPalabraClaveCat);
    }

    @Test
    @Transactional
    public void createPalabraClaveCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = palabraClaveCatRepository.findAll().size();

        // Create the PalabraClaveCat with an existing ID
        palabraClaveCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPalabraClaveCatMockMvc.perform(post("/api/palabra-clave-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(palabraClaveCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PalabraClaveCat> palabraClaveCatList = palabraClaveCatRepository.findAll();
        assertThat(palabraClaveCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPalabraClaveCats() throws Exception {
        // Initialize the database
        palabraClaveCatRepository.saveAndFlush(palabraClaveCat);

        // Get all the palabraClaveCatList
        restPalabraClaveCatMockMvc.perform(get("/api/palabra-clave-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palabraClaveCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPalabraClaveCat() throws Exception {
        // Initialize the database
        palabraClaveCatRepository.saveAndFlush(palabraClaveCat);

        // Get the palabraClaveCat
        restPalabraClaveCatMockMvc.perform(get("/api/palabra-clave-cats/{id}", palabraClaveCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(palabraClaveCat.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPalabraClaveCat() throws Exception {
        // Get the palabraClaveCat
        restPalabraClaveCatMockMvc.perform(get("/api/palabra-clave-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePalabraClaveCat() throws Exception {
        // Initialize the database
        palabraClaveCatService.save(palabraClaveCat);

        int databaseSizeBeforeUpdate = palabraClaveCatRepository.findAll().size();

        // Update the palabraClaveCat
        PalabraClaveCat updatedPalabraClaveCat = palabraClaveCatRepository.findOne(palabraClaveCat.getId());
        updatedPalabraClaveCat
            .nombre(UPDATED_NOMBRE)
            .activo(UPDATED_ACTIVO);

        restPalabraClaveCatMockMvc.perform(put("/api/palabra-clave-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPalabraClaveCat)))
            .andExpect(status().isOk());

        // Validate the PalabraClaveCat in the database
        List<PalabraClaveCat> palabraClaveCatList = palabraClaveCatRepository.findAll();
        assertThat(palabraClaveCatList).hasSize(databaseSizeBeforeUpdate);
        PalabraClaveCat testPalabraClaveCat = palabraClaveCatList.get(palabraClaveCatList.size() - 1);
        assertThat(testPalabraClaveCat.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPalabraClaveCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the PalabraClaveCat in Elasticsearch
        PalabraClaveCat palabraClaveCatEs = palabraClaveCatSearchRepository.findOne(testPalabraClaveCat.getId());
        assertThat(palabraClaveCatEs).isEqualToComparingFieldByField(testPalabraClaveCat);
    }

    @Test
    @Transactional
    public void updateNonExistingPalabraClaveCat() throws Exception {
        int databaseSizeBeforeUpdate = palabraClaveCatRepository.findAll().size();

        // Create the PalabraClaveCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPalabraClaveCatMockMvc.perform(put("/api/palabra-clave-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(palabraClaveCat)))
            .andExpect(status().isCreated());

        // Validate the PalabraClaveCat in the database
        List<PalabraClaveCat> palabraClaveCatList = palabraClaveCatRepository.findAll();
        assertThat(palabraClaveCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePalabraClaveCat() throws Exception {
        // Initialize the database
        palabraClaveCatService.save(palabraClaveCat);

        int databaseSizeBeforeDelete = palabraClaveCatRepository.findAll().size();

        // Get the palabraClaveCat
        restPalabraClaveCatMockMvc.perform(delete("/api/palabra-clave-cats/{id}", palabraClaveCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean palabraClaveCatExistsInEs = palabraClaveCatSearchRepository.exists(palabraClaveCat.getId());
        assertThat(palabraClaveCatExistsInEs).isFalse();

        // Validate the database is empty
        List<PalabraClaveCat> palabraClaveCatList = palabraClaveCatRepository.findAll();
        assertThat(palabraClaveCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPalabraClaveCat() throws Exception {
        // Initialize the database
        palabraClaveCatService.save(palabraClaveCat);

        // Search the palabraClaveCat
        restPalabraClaveCatMockMvc.perform(get("/api/_search/palabra-clave-cats?query=id:" + palabraClaveCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palabraClaveCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PalabraClaveCat.class);
        PalabraClaveCat palabraClaveCat1 = new PalabraClaveCat();
        palabraClaveCat1.setId(1L);
        PalabraClaveCat palabraClaveCat2 = new PalabraClaveCat();
        palabraClaveCat2.setId(palabraClaveCat1.getId());
        assertThat(palabraClaveCat1).isEqualTo(palabraClaveCat2);
        palabraClaveCat2.setId(2L);
        assertThat(palabraClaveCat1).isNotEqualTo(palabraClaveCat2);
        palabraClaveCat1.setId(null);
        assertThat(palabraClaveCat1).isNotEqualTo(palabraClaveCat2);
    }
}
