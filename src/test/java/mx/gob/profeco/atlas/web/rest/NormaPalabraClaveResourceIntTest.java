package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.NormaPalabraClave;
import mx.gob.profeco.atlas.repository.NormaPalabraClaveRepository;
import mx.gob.profeco.atlas.service.NormaPalabraClaveService;
import mx.gob.profeco.atlas.repository.search.NormaPalabraClaveSearchRepository;
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
 * Test class for the NormaPalabraClaveResource REST controller.
 *
 * @see NormaPalabraClaveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class NormaPalabraClaveResourceIntTest {

    private static final Long DEFAULT_PALABRA_CLAVE_ID = 1L;
    private static final Long UPDATED_PALABRA_CLAVE_ID = 2L;

    private static final Long DEFAULT_IDIOMA_CAT_ID = 1L;
    private static final Long UPDATED_IDIOMA_CAT_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_NORMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_NORMA_ID = 2L;

    @Autowired
    private NormaPalabraClaveRepository normaPalabraClaveRepository;

    @Autowired
    private NormaPalabraClaveService normaPalabraClaveService;

    @Autowired
    private NormaPalabraClaveSearchRepository normaPalabraClaveSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNormaPalabraClaveMockMvc;

    private NormaPalabraClave normaPalabraClave;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NormaPalabraClaveResource normaPalabraClaveResource = new NormaPalabraClaveResource(normaPalabraClaveService);
        this.restNormaPalabraClaveMockMvc = MockMvcBuilders.standaloneSetup(normaPalabraClaveResource)
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
    public static NormaPalabraClave createEntity(EntityManager em) {
        NormaPalabraClave normaPalabraClave = new NormaPalabraClave()
            .palabraClaveId(DEFAULT_PALABRA_CLAVE_ID)
            .idiomaCatId(DEFAULT_IDIOMA_CAT_ID)
            .normaIdiomaId(DEFAULT_NORMA_IDIOMA_ID)
            .normaIdiomaNormaId(DEFAULT_NORMA_IDIOMA_NORMA_ID);
        return normaPalabraClave;
    }

    @Before
    public void initTest() {
        normaPalabraClaveSearchRepository.deleteAll();
        normaPalabraClave = createEntity(em);
    }

    @Test
    @Transactional
    public void createNormaPalabraClave() throws Exception {
        int databaseSizeBeforeCreate = normaPalabraClaveRepository.findAll().size();

        // Create the NormaPalabraClave
        restNormaPalabraClaveMockMvc.perform(post("/api/norma-palabra-claves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaPalabraClave)))
            .andExpect(status().isCreated());

        // Validate the NormaPalabraClave in the database
        List<NormaPalabraClave> normaPalabraClaveList = normaPalabraClaveRepository.findAll();
        assertThat(normaPalabraClaveList).hasSize(databaseSizeBeforeCreate + 1);
        NormaPalabraClave testNormaPalabraClave = normaPalabraClaveList.get(normaPalabraClaveList.size() - 1);
        assertThat(testNormaPalabraClave.getPalabraClaveId()).isEqualTo(DEFAULT_PALABRA_CLAVE_ID);
        assertThat(testNormaPalabraClave.getIdiomaCatId()).isEqualTo(DEFAULT_IDIOMA_CAT_ID);
        assertThat(testNormaPalabraClave.getNormaIdiomaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_ID);
        assertThat(testNormaPalabraClave.getNormaIdiomaNormaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_NORMA_ID);

        // Validate the NormaPalabraClave in Elasticsearch
        NormaPalabraClave normaPalabraClaveEs = normaPalabraClaveSearchRepository.findOne(testNormaPalabraClave.getId());
        assertThat(normaPalabraClaveEs).isEqualToComparingFieldByField(testNormaPalabraClave);
    }

    @Test
    @Transactional
    public void createNormaPalabraClaveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaPalabraClaveRepository.findAll().size();

        // Create the NormaPalabraClave with an existing ID
        normaPalabraClave.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaPalabraClaveMockMvc.perform(post("/api/norma-palabra-claves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaPalabraClave)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NormaPalabraClave> normaPalabraClaveList = normaPalabraClaveRepository.findAll();
        assertThat(normaPalabraClaveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNormaPalabraClaves() throws Exception {
        // Initialize the database
        normaPalabraClaveRepository.saveAndFlush(normaPalabraClave);

        // Get all the normaPalabraClaveList
        restNormaPalabraClaveMockMvc.perform(get("/api/norma-palabra-claves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaPalabraClave.getId().intValue())))
            .andExpect(jsonPath("$.[*].palabraClaveId").value(hasItem(DEFAULT_PALABRA_CLAVE_ID.intValue())))
            .andExpect(jsonPath("$.[*].idiomaCatId").value(hasItem(DEFAULT_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaId").value(hasItem(DEFAULT_NORMA_IDIOMA_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void getNormaPalabraClave() throws Exception {
        // Initialize the database
        normaPalabraClaveRepository.saveAndFlush(normaPalabraClave);

        // Get the normaPalabraClave
        restNormaPalabraClaveMockMvc.perform(get("/api/norma-palabra-claves/{id}", normaPalabraClave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(normaPalabraClave.getId().intValue()))
            .andExpect(jsonPath("$.palabraClaveId").value(DEFAULT_PALABRA_CLAVE_ID.intValue()))
            .andExpect(jsonPath("$.idiomaCatId").value(DEFAULT_IDIOMA_CAT_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaId").value(DEFAULT_NORMA_IDIOMA_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaNormaId").value(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNormaPalabraClave() throws Exception {
        // Get the normaPalabraClave
        restNormaPalabraClaveMockMvc.perform(get("/api/norma-palabra-claves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNormaPalabraClave() throws Exception {
        // Initialize the database
        normaPalabraClaveService.save(normaPalabraClave);

        int databaseSizeBeforeUpdate = normaPalabraClaveRepository.findAll().size();

        // Update the normaPalabraClave
        NormaPalabraClave updatedNormaPalabraClave = normaPalabraClaveRepository.findOne(normaPalabraClave.getId());
        updatedNormaPalabraClave
            .palabraClaveId(UPDATED_PALABRA_CLAVE_ID)
            .idiomaCatId(UPDATED_IDIOMA_CAT_ID)
            .normaIdiomaId(UPDATED_NORMA_IDIOMA_ID)
            .normaIdiomaNormaId(UPDATED_NORMA_IDIOMA_NORMA_ID);

        restNormaPalabraClaveMockMvc.perform(put("/api/norma-palabra-claves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNormaPalabraClave)))
            .andExpect(status().isOk());

        // Validate the NormaPalabraClave in the database
        List<NormaPalabraClave> normaPalabraClaveList = normaPalabraClaveRepository.findAll();
        assertThat(normaPalabraClaveList).hasSize(databaseSizeBeforeUpdate);
        NormaPalabraClave testNormaPalabraClave = normaPalabraClaveList.get(normaPalabraClaveList.size() - 1);
        assertThat(testNormaPalabraClave.getPalabraClaveId()).isEqualTo(UPDATED_PALABRA_CLAVE_ID);
        assertThat(testNormaPalabraClave.getIdiomaCatId()).isEqualTo(UPDATED_IDIOMA_CAT_ID);
        assertThat(testNormaPalabraClave.getNormaIdiomaId()).isEqualTo(UPDATED_NORMA_IDIOMA_ID);
        assertThat(testNormaPalabraClave.getNormaIdiomaNormaId()).isEqualTo(UPDATED_NORMA_IDIOMA_NORMA_ID);

        // Validate the NormaPalabraClave in Elasticsearch
        NormaPalabraClave normaPalabraClaveEs = normaPalabraClaveSearchRepository.findOne(testNormaPalabraClave.getId());
        assertThat(normaPalabraClaveEs).isEqualToComparingFieldByField(testNormaPalabraClave);
    }

    @Test
    @Transactional
    public void updateNonExistingNormaPalabraClave() throws Exception {
        int databaseSizeBeforeUpdate = normaPalabraClaveRepository.findAll().size();

        // Create the NormaPalabraClave

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNormaPalabraClaveMockMvc.perform(put("/api/norma-palabra-claves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaPalabraClave)))
            .andExpect(status().isCreated());

        // Validate the NormaPalabraClave in the database
        List<NormaPalabraClave> normaPalabraClaveList = normaPalabraClaveRepository.findAll();
        assertThat(normaPalabraClaveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNormaPalabraClave() throws Exception {
        // Initialize the database
        normaPalabraClaveService.save(normaPalabraClave);

        int databaseSizeBeforeDelete = normaPalabraClaveRepository.findAll().size();

        // Get the normaPalabraClave
        restNormaPalabraClaveMockMvc.perform(delete("/api/norma-palabra-claves/{id}", normaPalabraClave.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean normaPalabraClaveExistsInEs = normaPalabraClaveSearchRepository.exists(normaPalabraClave.getId());
        assertThat(normaPalabraClaveExistsInEs).isFalse();

        // Validate the database is empty
        List<NormaPalabraClave> normaPalabraClaveList = normaPalabraClaveRepository.findAll();
        assertThat(normaPalabraClaveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNormaPalabraClave() throws Exception {
        // Initialize the database
        normaPalabraClaveService.save(normaPalabraClave);

        // Search the normaPalabraClave
        restNormaPalabraClaveMockMvc.perform(get("/api/_search/norma-palabra-claves?query=id:" + normaPalabraClave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaPalabraClave.getId().intValue())))
            .andExpect(jsonPath("$.[*].palabraClaveId").value(hasItem(DEFAULT_PALABRA_CLAVE_ID.intValue())))
            .andExpect(jsonPath("$.[*].idiomaCatId").value(hasItem(DEFAULT_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaId").value(hasItem(DEFAULT_NORMA_IDIOMA_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NormaPalabraClave.class);
        NormaPalabraClave normaPalabraClave1 = new NormaPalabraClave();
        normaPalabraClave1.setId(1L);
        NormaPalabraClave normaPalabraClave2 = new NormaPalabraClave();
        normaPalabraClave2.setId(normaPalabraClave1.getId());
        assertThat(normaPalabraClave1).isEqualTo(normaPalabraClave2);
        normaPalabraClave2.setId(2L);
        assertThat(normaPalabraClave1).isNotEqualTo(normaPalabraClave2);
        normaPalabraClave1.setId(null);
        assertThat(normaPalabraClave1).isNotEqualTo(normaPalabraClave2);
    }
}
