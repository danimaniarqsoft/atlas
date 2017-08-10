package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.NormaSubtema;
import mx.gob.profeco.atlas.repository.NormaSubtemaRepository;
import mx.gob.profeco.atlas.service.NormaSubtemaService;
import mx.gob.profeco.atlas.repository.search.NormaSubtemaSearchRepository;
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
 * Test class for the NormaSubtemaResource REST controller.
 *
 * @see NormaSubtemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class NormaSubtemaResourceIntTest {

    private static final Long DEFAULT_SUBTEMA_CAT_ID = 1L;
    private static final Long UPDATED_SUBTEMA_CAT_ID = 2L;

    private static final Long DEFAULT_IDIOMA_CAT_ID = 1L;
    private static final Long UPDATED_IDIOMA_CAT_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_IDIOMA_CAT_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_IDIOMA_CAT_ID = 2L;

    private static final Long DEFAULT_NORMA_IDIOMA_NORMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_NORMA_ID = 2L;

    @Autowired
    private NormaSubtemaRepository normaSubtemaRepository;

    @Autowired
    private NormaSubtemaService normaSubtemaService;

    @Autowired
    private NormaSubtemaSearchRepository normaSubtemaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNormaSubtemaMockMvc;

    private NormaSubtema normaSubtema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NormaSubtemaResource normaSubtemaResource = new NormaSubtemaResource(normaSubtemaService);
        this.restNormaSubtemaMockMvc = MockMvcBuilders.standaloneSetup(normaSubtemaResource)
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
    public static NormaSubtema createEntity(EntityManager em) {
        NormaSubtema normaSubtema = new NormaSubtema()
            .subtemaCatId(DEFAULT_SUBTEMA_CAT_ID)
            .idiomaCatId(DEFAULT_IDIOMA_CAT_ID)
            .normaIdiomaId(DEFAULT_NORMA_IDIOMA_ID)
            .normaIdiomaIdiomaCatId(DEFAULT_NORMA_IDIOMA_IDIOMA_CAT_ID)
            .normaIdiomaNormaId(DEFAULT_NORMA_IDIOMA_NORMA_ID);
        return normaSubtema;
    }

    @Before
    public void initTest() {
        normaSubtemaSearchRepository.deleteAll();
        normaSubtema = createEntity(em);
    }

    @Test
    @Transactional
    public void createNormaSubtema() throws Exception {
        int databaseSizeBeforeCreate = normaSubtemaRepository.findAll().size();

        // Create the NormaSubtema
        restNormaSubtemaMockMvc.perform(post("/api/norma-subtemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaSubtema)))
            .andExpect(status().isCreated());

        // Validate the NormaSubtema in the database
        List<NormaSubtema> normaSubtemaList = normaSubtemaRepository.findAll();
        assertThat(normaSubtemaList).hasSize(databaseSizeBeforeCreate + 1);
        NormaSubtema testNormaSubtema = normaSubtemaList.get(normaSubtemaList.size() - 1);
        assertThat(testNormaSubtema.getSubtemaCatId()).isEqualTo(DEFAULT_SUBTEMA_CAT_ID);
        assertThat(testNormaSubtema.getIdiomaCatId()).isEqualTo(DEFAULT_IDIOMA_CAT_ID);
        assertThat(testNormaSubtema.getNormaIdiomaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_ID);
        assertThat(testNormaSubtema.getNormaIdiomaIdiomaCatId()).isEqualTo(DEFAULT_NORMA_IDIOMA_IDIOMA_CAT_ID);
        assertThat(testNormaSubtema.getNormaIdiomaNormaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_NORMA_ID);

        // Validate the NormaSubtema in Elasticsearch
        NormaSubtema normaSubtemaEs = normaSubtemaSearchRepository.findOne(testNormaSubtema.getId());
        assertThat(normaSubtemaEs).isEqualToComparingFieldByField(testNormaSubtema);
    }

    @Test
    @Transactional
    public void createNormaSubtemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaSubtemaRepository.findAll().size();

        // Create the NormaSubtema with an existing ID
        normaSubtema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaSubtemaMockMvc.perform(post("/api/norma-subtemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaSubtema)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NormaSubtema> normaSubtemaList = normaSubtemaRepository.findAll();
        assertThat(normaSubtemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNormaSubtemas() throws Exception {
        // Initialize the database
        normaSubtemaRepository.saveAndFlush(normaSubtema);

        // Get all the normaSubtemaList
        restNormaSubtemaMockMvc.perform(get("/api/norma-subtemas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaSubtema.getId().intValue())))
            .andExpect(jsonPath("$.[*].subtemaCatId").value(hasItem(DEFAULT_SUBTEMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].idiomaCatId").value(hasItem(DEFAULT_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaId").value(hasItem(DEFAULT_NORMA_IDIOMA_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaIdiomaCatId").value(hasItem(DEFAULT_NORMA_IDIOMA_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void getNormaSubtema() throws Exception {
        // Initialize the database
        normaSubtemaRepository.saveAndFlush(normaSubtema);

        // Get the normaSubtema
        restNormaSubtemaMockMvc.perform(get("/api/norma-subtemas/{id}", normaSubtema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(normaSubtema.getId().intValue()))
            .andExpect(jsonPath("$.subtemaCatId").value(DEFAULT_SUBTEMA_CAT_ID.intValue()))
            .andExpect(jsonPath("$.idiomaCatId").value(DEFAULT_IDIOMA_CAT_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaId").value(DEFAULT_NORMA_IDIOMA_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaIdiomaCatId").value(DEFAULT_NORMA_IDIOMA_IDIOMA_CAT_ID.intValue()))
            .andExpect(jsonPath("$.normaIdiomaNormaId").value(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNormaSubtema() throws Exception {
        // Get the normaSubtema
        restNormaSubtemaMockMvc.perform(get("/api/norma-subtemas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNormaSubtema() throws Exception {
        // Initialize the database
        normaSubtemaService.save(normaSubtema);

        int databaseSizeBeforeUpdate = normaSubtemaRepository.findAll().size();

        // Update the normaSubtema
        NormaSubtema updatedNormaSubtema = normaSubtemaRepository.findOne(normaSubtema.getId());
        updatedNormaSubtema
            .subtemaCatId(UPDATED_SUBTEMA_CAT_ID)
            .idiomaCatId(UPDATED_IDIOMA_CAT_ID)
            .normaIdiomaId(UPDATED_NORMA_IDIOMA_ID)
            .normaIdiomaIdiomaCatId(UPDATED_NORMA_IDIOMA_IDIOMA_CAT_ID)
            .normaIdiomaNormaId(UPDATED_NORMA_IDIOMA_NORMA_ID);

        restNormaSubtemaMockMvc.perform(put("/api/norma-subtemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNormaSubtema)))
            .andExpect(status().isOk());

        // Validate the NormaSubtema in the database
        List<NormaSubtema> normaSubtemaList = normaSubtemaRepository.findAll();
        assertThat(normaSubtemaList).hasSize(databaseSizeBeforeUpdate);
        NormaSubtema testNormaSubtema = normaSubtemaList.get(normaSubtemaList.size() - 1);
        assertThat(testNormaSubtema.getSubtemaCatId()).isEqualTo(UPDATED_SUBTEMA_CAT_ID);
        assertThat(testNormaSubtema.getIdiomaCatId()).isEqualTo(UPDATED_IDIOMA_CAT_ID);
        assertThat(testNormaSubtema.getNormaIdiomaId()).isEqualTo(UPDATED_NORMA_IDIOMA_ID);
        assertThat(testNormaSubtema.getNormaIdiomaIdiomaCatId()).isEqualTo(UPDATED_NORMA_IDIOMA_IDIOMA_CAT_ID);
        assertThat(testNormaSubtema.getNormaIdiomaNormaId()).isEqualTo(UPDATED_NORMA_IDIOMA_NORMA_ID);

        // Validate the NormaSubtema in Elasticsearch
        NormaSubtema normaSubtemaEs = normaSubtemaSearchRepository.findOne(testNormaSubtema.getId());
        assertThat(normaSubtemaEs).isEqualToComparingFieldByField(testNormaSubtema);
    }

    @Test
    @Transactional
    public void updateNonExistingNormaSubtema() throws Exception {
        int databaseSizeBeforeUpdate = normaSubtemaRepository.findAll().size();

        // Create the NormaSubtema

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNormaSubtemaMockMvc.perform(put("/api/norma-subtemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaSubtema)))
            .andExpect(status().isCreated());

        // Validate the NormaSubtema in the database
        List<NormaSubtema> normaSubtemaList = normaSubtemaRepository.findAll();
        assertThat(normaSubtemaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNormaSubtema() throws Exception {
        // Initialize the database
        normaSubtemaService.save(normaSubtema);

        int databaseSizeBeforeDelete = normaSubtemaRepository.findAll().size();

        // Get the normaSubtema
        restNormaSubtemaMockMvc.perform(delete("/api/norma-subtemas/{id}", normaSubtema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean normaSubtemaExistsInEs = normaSubtemaSearchRepository.exists(normaSubtema.getId());
        assertThat(normaSubtemaExistsInEs).isFalse();

        // Validate the database is empty
        List<NormaSubtema> normaSubtemaList = normaSubtemaRepository.findAll();
        assertThat(normaSubtemaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNormaSubtema() throws Exception {
        // Initialize the database
        normaSubtemaService.save(normaSubtema);

        // Search the normaSubtema
        restNormaSubtemaMockMvc.perform(get("/api/_search/norma-subtemas?query=id:" + normaSubtema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaSubtema.getId().intValue())))
            .andExpect(jsonPath("$.[*].subtemaCatId").value(hasItem(DEFAULT_SUBTEMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].idiomaCatId").value(hasItem(DEFAULT_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaId").value(hasItem(DEFAULT_NORMA_IDIOMA_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaIdiomaCatId").value(hasItem(DEFAULT_NORMA_IDIOMA_IDIOMA_CAT_ID.intValue())))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NormaSubtema.class);
        NormaSubtema normaSubtema1 = new NormaSubtema();
        normaSubtema1.setId(1L);
        NormaSubtema normaSubtema2 = new NormaSubtema();
        normaSubtema2.setId(normaSubtema1.getId());
        assertThat(normaSubtema1).isEqualTo(normaSubtema2);
        normaSubtema2.setId(2L);
        assertThat(normaSubtema1).isNotEqualTo(normaSubtema2);
        normaSubtema1.setId(null);
        assertThat(normaSubtema1).isNotEqualTo(normaSubtema2);
    }
}
