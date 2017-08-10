package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.NormaTema;
import mx.gob.profeco.atlas.repository.NormaTemaRepository;
import mx.gob.profeco.atlas.service.NormaTemaService;
import mx.gob.profeco.atlas.repository.search.NormaTemaSearchRepository;
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
 * Test class for the NormaTemaResource REST controller.
 *
 * @see NormaTemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class NormaTemaResourceIntTest {

    private static final Long DEFAULT_TEMA_CAT_ID = 1L;
    private static final Long UPDATED_TEMA_CAT_ID = 2L;

    private static final Long DEFAULT_IDIOMA_CAT_ID = 1L;
    private static final Long UPDATED_IDIOMA_CAT_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_NORMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_NORMA_ID = 2L;

    @Autowired
    private NormaTemaRepository normaTemaRepository;

    @Autowired
    private NormaTemaService normaTemaService;

    @Autowired
    private NormaTemaSearchRepository normaTemaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNormaTemaMockMvc;

    private NormaTema normaTema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NormaTemaResource normaTemaResource = new NormaTemaResource(normaTemaService);
        this.restNormaTemaMockMvc = MockMvcBuilders.standaloneSetup(normaTemaResource)
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
    public static NormaTema createEntity(EntityManager em) {
        NormaTema normaTema = new NormaTema()
            .temaCatId(DEFAULT_TEMA_CAT_ID)
            .idiomaCatId(DEFAULT_IDIOMA_CAT_ID)
            .normaIdiomaId(DEFAULT_NORMA_IDIOMA_ID)
            .normaIdiomaNormaId(DEFAULT_NORMA_IDIOMA_NORMA_ID);
        return normaTema;
    }

    @Before
    public void initTest() {
        normaTemaSearchRepository.deleteAll();
        normaTema = createEntity(em);
    }

    @Test
    @Transactional
    public void createNormaTema() throws Exception {
        int databaseSizeBeforeCreate = normaTemaRepository.findAll().size();

        // Create the NormaTema
        restNormaTemaMockMvc.perform(post("/api/norma-temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaTema)))
            .andExpect(status().isCreated());

        // Validate the NormaTema in the database
        List<NormaTema> normaTemaList = normaTemaRepository.findAll();
        assertThat(normaTemaList).hasSize(databaseSizeBeforeCreate + 1);
        NormaTema testNormaTema = normaTemaList.get(normaTemaList.size() - 1);
        assertThat(testNormaTema.getTemaCatId()).isEqualTo(DEFAULT_TEMA_CAT_ID);
        assertThat(testNormaTema.getIdiomaCatId()).isEqualTo(DEFAULT_IDIOMA_CAT_ID);
        assertThat(testNormaTema.getNormaIdiomaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_ID);
        assertThat(testNormaTema.getNormaIdiomaNormaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_NORMA_ID);

        // Validate the NormaTema in Elasticsearch
        NormaTema normaTemaEs = normaTemaSearchRepository.findOne(testNormaTema.getId());
        assertThat(normaTemaEs).isEqualToComparingFieldByField(testNormaTema);
    }

    @Test
    @Transactional
    public void createNormaTemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaTemaRepository.findAll().size();

        // Create the NormaTema with an existing ID
        normaTema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaTemaMockMvc.perform(post("/api/norma-temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaTema)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NormaTema> normaTemaList = normaTemaRepository.findAll();
        assertThat(normaTemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNormaTemas() throws Exception {
        // Initialize the database
        normaTemaRepository.saveAndFlush(normaTema);

        // Get all the normaTemaList
        restNormaTemaMockMvc.perform(get("/api/norma-temas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaTema.getId().intValue())))
            .andExpect(jsonPath("$.[*].temaCatId").value(hasItem(DEFAULT_TEMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].idiomaCatId").value(hasItem(DEFAULT_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaId").value(hasItem(DEFAULT_NORMA_IDIOMA_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void getNormaTema() throws Exception {
        // Initialize the database
        normaTemaRepository.saveAndFlush(normaTema);

        // Get the normaTema
        restNormaTemaMockMvc.perform(get("/api/norma-temas/{id}", normaTema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(normaTema.getId().intValue()))
            .andExpect(jsonPath("$.temaCatId").value(DEFAULT_TEMA_CAT_ID.intValue()))
            .andExpect(jsonPath("$.idiomaCatId").value(DEFAULT_IDIOMA_CAT_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaId").value(DEFAULT_NORMA_IDIOMA_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaNormaId").value(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNormaTema() throws Exception {
        // Get the normaTema
        restNormaTemaMockMvc.perform(get("/api/norma-temas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNormaTema() throws Exception {
        // Initialize the database
        normaTemaService.save(normaTema);

        int databaseSizeBeforeUpdate = normaTemaRepository.findAll().size();

        // Update the normaTema
        NormaTema updatedNormaTema = normaTemaRepository.findOne(normaTema.getId());
        updatedNormaTema
            .temaCatId(UPDATED_TEMA_CAT_ID)
            .idiomaCatId(UPDATED_IDIOMA_CAT_ID)
            .normaIdiomaId(UPDATED_NORMA_IDIOMA_ID)
            .normaIdiomaNormaId(UPDATED_NORMA_IDIOMA_NORMA_ID);

        restNormaTemaMockMvc.perform(put("/api/norma-temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNormaTema)))
            .andExpect(status().isOk());

        // Validate the NormaTema in the database
        List<NormaTema> normaTemaList = normaTemaRepository.findAll();
        assertThat(normaTemaList).hasSize(databaseSizeBeforeUpdate);
        NormaTema testNormaTema = normaTemaList.get(normaTemaList.size() - 1);
        assertThat(testNormaTema.getTemaCatId()).isEqualTo(UPDATED_TEMA_CAT_ID);
        assertThat(testNormaTema.getIdiomaCatId()).isEqualTo(UPDATED_IDIOMA_CAT_ID);
        assertThat(testNormaTema.getNormaIdiomaId()).isEqualTo(UPDATED_NORMA_IDIOMA_ID);
        assertThat(testNormaTema.getNormaIdiomaNormaId()).isEqualTo(UPDATED_NORMA_IDIOMA_NORMA_ID);

        // Validate the NormaTema in Elasticsearch
        NormaTema normaTemaEs = normaTemaSearchRepository.findOne(testNormaTema.getId());
        assertThat(normaTemaEs).isEqualToComparingFieldByField(testNormaTema);
    }

    @Test
    @Transactional
    public void updateNonExistingNormaTema() throws Exception {
        int databaseSizeBeforeUpdate = normaTemaRepository.findAll().size();

        // Create the NormaTema

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNormaTemaMockMvc.perform(put("/api/norma-temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaTema)))
            .andExpect(status().isCreated());

        // Validate the NormaTema in the database
        List<NormaTema> normaTemaList = normaTemaRepository.findAll();
        assertThat(normaTemaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNormaTema() throws Exception {
        // Initialize the database
        normaTemaService.save(normaTema);

        int databaseSizeBeforeDelete = normaTemaRepository.findAll().size();

        // Get the normaTema
        restNormaTemaMockMvc.perform(delete("/api/norma-temas/{id}", normaTema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean normaTemaExistsInEs = normaTemaSearchRepository.exists(normaTema.getId());
        assertThat(normaTemaExistsInEs).isFalse();

        // Validate the database is empty
        List<NormaTema> normaTemaList = normaTemaRepository.findAll();
        assertThat(normaTemaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNormaTema() throws Exception {
        // Initialize the database
        normaTemaService.save(normaTema);

        // Search the normaTema
        restNormaTemaMockMvc.perform(get("/api/_search/norma-temas?query=id:" + normaTema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaTema.getId().intValue())))
            .andExpect(jsonPath("$.[*].temaCatId").value(hasItem(DEFAULT_TEMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].idiomaCatId").value(hasItem(DEFAULT_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaId").value(hasItem(DEFAULT_NORMA_IDIOMA_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NormaTema.class);
        NormaTema normaTema1 = new NormaTema();
        normaTema1.setId(1L);
        NormaTema normaTema2 = new NormaTema();
        normaTema2.setId(normaTema1.getId());
        assertThat(normaTema1).isEqualTo(normaTema2);
        normaTema2.setId(2L);
        assertThat(normaTema1).isNotEqualTo(normaTema2);
        normaTema1.setId(null);
        assertThat(normaTema1).isNotEqualTo(normaTema2);
    }
}
