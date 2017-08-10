package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.IdiomaCat;
import mx.gob.profeco.atlas.repository.IdiomaCatRepository;
import mx.gob.profeco.atlas.service.IdiomaCatService;
import mx.gob.profeco.atlas.repository.search.IdiomaCatSearchRepository;
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
 * Test class for the IdiomaCatResource REST controller.
 *
 * @see IdiomaCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class IdiomaCatResourceIntTest {

    private static final String DEFAULT_IDIOMA_1 = "AAAAAAAAAA";
    private static final String UPDATED_IDIOMA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_IDIOMA_2 = "AAAAAAAAAA";
    private static final String UPDATED_IDIOMA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_IDIOMA_3 = "AAAAAAAAAA";
    private static final String UPDATED_IDIOMA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_IDIOMA_4 = "AAAAAAAAAA";
    private static final String UPDATED_IDIOMA_4 = "BBBBBBBBBB";

    private static final String DEFAULT_IDIOMA_5 = "AAAAAAAAAA";
    private static final String UPDATED_IDIOMA_5 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private IdiomaCatRepository idiomaCatRepository;

    @Autowired
    private IdiomaCatService idiomaCatService;

    @Autowired
    private IdiomaCatSearchRepository idiomaCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIdiomaCatMockMvc;

    private IdiomaCat idiomaCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IdiomaCatResource idiomaCatResource = new IdiomaCatResource(idiomaCatService);
        this.restIdiomaCatMockMvc = MockMvcBuilders.standaloneSetup(idiomaCatResource)
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
    public static IdiomaCat createEntity(EntityManager em) {
        IdiomaCat idiomaCat = new IdiomaCat()
            .idioma1(DEFAULT_IDIOMA_1)
            .idioma2(DEFAULT_IDIOMA_2)
            .idioma3(DEFAULT_IDIOMA_3)
            .idioma4(DEFAULT_IDIOMA_4)
            .idioma5(DEFAULT_IDIOMA_5)
            .activo(DEFAULT_ACTIVO);
        return idiomaCat;
    }

    @Before
    public void initTest() {
        idiomaCatSearchRepository.deleteAll();
        idiomaCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdiomaCat() throws Exception {
        int databaseSizeBeforeCreate = idiomaCatRepository.findAll().size();

        // Create the IdiomaCat
        restIdiomaCatMockMvc.perform(post("/api/idioma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idiomaCat)))
            .andExpect(status().isCreated());

        // Validate the IdiomaCat in the database
        List<IdiomaCat> idiomaCatList = idiomaCatRepository.findAll();
        assertThat(idiomaCatList).hasSize(databaseSizeBeforeCreate + 1);
        IdiomaCat testIdiomaCat = idiomaCatList.get(idiomaCatList.size() - 1);
        assertThat(testIdiomaCat.getIdioma1()).isEqualTo(DEFAULT_IDIOMA_1);
        assertThat(testIdiomaCat.getIdioma2()).isEqualTo(DEFAULT_IDIOMA_2);
        assertThat(testIdiomaCat.getIdioma3()).isEqualTo(DEFAULT_IDIOMA_3);
        assertThat(testIdiomaCat.getIdioma4()).isEqualTo(DEFAULT_IDIOMA_4);
        assertThat(testIdiomaCat.getIdioma5()).isEqualTo(DEFAULT_IDIOMA_5);
        assertThat(testIdiomaCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the IdiomaCat in Elasticsearch
        IdiomaCat idiomaCatEs = idiomaCatSearchRepository.findOne(testIdiomaCat.getId());
        assertThat(idiomaCatEs).isEqualToComparingFieldByField(testIdiomaCat);
    }

    @Test
    @Transactional
    public void createIdiomaCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idiomaCatRepository.findAll().size();

        // Create the IdiomaCat with an existing ID
        idiomaCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdiomaCatMockMvc.perform(post("/api/idioma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idiomaCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IdiomaCat> idiomaCatList = idiomaCatRepository.findAll();
        assertThat(idiomaCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIdiomaCats() throws Exception {
        // Initialize the database
        idiomaCatRepository.saveAndFlush(idiomaCat);

        // Get all the idiomaCatList
        restIdiomaCatMockMvc.perform(get("/api/idioma-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idiomaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].idioma1").value(hasItem(DEFAULT_IDIOMA_1.toString())))
            .andExpect(jsonPath("$.[*].idioma2").value(hasItem(DEFAULT_IDIOMA_2.toString())))
            .andExpect(jsonPath("$.[*].idioma3").value(hasItem(DEFAULT_IDIOMA_3.toString())))
            .andExpect(jsonPath("$.[*].idioma4").value(hasItem(DEFAULT_IDIOMA_4.toString())))
            .andExpect(jsonPath("$.[*].idioma5").value(hasItem(DEFAULT_IDIOMA_5.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getIdiomaCat() throws Exception {
        // Initialize the database
        idiomaCatRepository.saveAndFlush(idiomaCat);

        // Get the idiomaCat
        restIdiomaCatMockMvc.perform(get("/api/idioma-cats/{id}", idiomaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idiomaCat.getId().intValue()))
            .andExpect(jsonPath("$.idioma1").value(DEFAULT_IDIOMA_1.toString()))
            .andExpect(jsonPath("$.idioma2").value(DEFAULT_IDIOMA_2.toString()))
            .andExpect(jsonPath("$.idioma3").value(DEFAULT_IDIOMA_3.toString()))
            .andExpect(jsonPath("$.idioma4").value(DEFAULT_IDIOMA_4.toString()))
            .andExpect(jsonPath("$.idioma5").value(DEFAULT_IDIOMA_5.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIdiomaCat() throws Exception {
        // Get the idiomaCat
        restIdiomaCatMockMvc.perform(get("/api/idioma-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdiomaCat() throws Exception {
        // Initialize the database
        idiomaCatService.save(idiomaCat);

        int databaseSizeBeforeUpdate = idiomaCatRepository.findAll().size();

        // Update the idiomaCat
        IdiomaCat updatedIdiomaCat = idiomaCatRepository.findOne(idiomaCat.getId());
        updatedIdiomaCat
            .idioma1(UPDATED_IDIOMA_1)
            .idioma2(UPDATED_IDIOMA_2)
            .idioma3(UPDATED_IDIOMA_3)
            .idioma4(UPDATED_IDIOMA_4)
            .idioma5(UPDATED_IDIOMA_5)
            .activo(UPDATED_ACTIVO);

        restIdiomaCatMockMvc.perform(put("/api/idioma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdiomaCat)))
            .andExpect(status().isOk());

        // Validate the IdiomaCat in the database
        List<IdiomaCat> idiomaCatList = idiomaCatRepository.findAll();
        assertThat(idiomaCatList).hasSize(databaseSizeBeforeUpdate);
        IdiomaCat testIdiomaCat = idiomaCatList.get(idiomaCatList.size() - 1);
        assertThat(testIdiomaCat.getIdioma1()).isEqualTo(UPDATED_IDIOMA_1);
        assertThat(testIdiomaCat.getIdioma2()).isEqualTo(UPDATED_IDIOMA_2);
        assertThat(testIdiomaCat.getIdioma3()).isEqualTo(UPDATED_IDIOMA_3);
        assertThat(testIdiomaCat.getIdioma4()).isEqualTo(UPDATED_IDIOMA_4);
        assertThat(testIdiomaCat.getIdioma5()).isEqualTo(UPDATED_IDIOMA_5);
        assertThat(testIdiomaCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the IdiomaCat in Elasticsearch
        IdiomaCat idiomaCatEs = idiomaCatSearchRepository.findOne(testIdiomaCat.getId());
        assertThat(idiomaCatEs).isEqualToComparingFieldByField(testIdiomaCat);
    }

    @Test
    @Transactional
    public void updateNonExistingIdiomaCat() throws Exception {
        int databaseSizeBeforeUpdate = idiomaCatRepository.findAll().size();

        // Create the IdiomaCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIdiomaCatMockMvc.perform(put("/api/idioma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idiomaCat)))
            .andExpect(status().isCreated());

        // Validate the IdiomaCat in the database
        List<IdiomaCat> idiomaCatList = idiomaCatRepository.findAll();
        assertThat(idiomaCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIdiomaCat() throws Exception {
        // Initialize the database
        idiomaCatService.save(idiomaCat);

        int databaseSizeBeforeDelete = idiomaCatRepository.findAll().size();

        // Get the idiomaCat
        restIdiomaCatMockMvc.perform(delete("/api/idioma-cats/{id}", idiomaCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean idiomaCatExistsInEs = idiomaCatSearchRepository.exists(idiomaCat.getId());
        assertThat(idiomaCatExistsInEs).isFalse();

        // Validate the database is empty
        List<IdiomaCat> idiomaCatList = idiomaCatRepository.findAll();
        assertThat(idiomaCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIdiomaCat() throws Exception {
        // Initialize the database
        idiomaCatService.save(idiomaCat);

        // Search the idiomaCat
        restIdiomaCatMockMvc.perform(get("/api/_search/idioma-cats?query=id:" + idiomaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idiomaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].idioma1").value(hasItem(DEFAULT_IDIOMA_1.toString())))
            .andExpect(jsonPath("$.[*].idioma2").value(hasItem(DEFAULT_IDIOMA_2.toString())))
            .andExpect(jsonPath("$.[*].idioma3").value(hasItem(DEFAULT_IDIOMA_3.toString())))
            .andExpect(jsonPath("$.[*].idioma4").value(hasItem(DEFAULT_IDIOMA_4.toString())))
            .andExpect(jsonPath("$.[*].idioma5").value(hasItem(DEFAULT_IDIOMA_5.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdiomaCat.class);
        IdiomaCat idiomaCat1 = new IdiomaCat();
        idiomaCat1.setId(1L);
        IdiomaCat idiomaCat2 = new IdiomaCat();
        idiomaCat2.setId(idiomaCat1.getId());
        assertThat(idiomaCat1).isEqualTo(idiomaCat2);
        idiomaCat2.setId(2L);
        assertThat(idiomaCat1).isNotEqualTo(idiomaCat2);
        idiomaCat1.setId(null);
        assertThat(idiomaCat1).isNotEqualTo(idiomaCat2);
    }
}
