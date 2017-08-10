package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.TipoNormaCat;
import mx.gob.profeco.atlas.repository.TipoNormaCatRepository;
import mx.gob.profeco.atlas.service.TipoNormaCatService;
import mx.gob.profeco.atlas.repository.search.TipoNormaCatSearchRepository;
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
 * Test class for the TipoNormaCatResource REST controller.
 *
 * @see TipoNormaCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class TipoNormaCatResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private TipoNormaCatRepository tipoNormaCatRepository;

    @Autowired
    private TipoNormaCatService tipoNormaCatService;

    @Autowired
    private TipoNormaCatSearchRepository tipoNormaCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoNormaCatMockMvc;

    private TipoNormaCat tipoNormaCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoNormaCatResource tipoNormaCatResource = new TipoNormaCatResource(tipoNormaCatService);
        this.restTipoNormaCatMockMvc = MockMvcBuilders.standaloneSetup(tipoNormaCatResource)
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
    public static TipoNormaCat createEntity(EntityManager em) {
        TipoNormaCat tipoNormaCat = new TipoNormaCat()
            .nombre(DEFAULT_NOMBRE)
            .activo(DEFAULT_ACTIVO);
        return tipoNormaCat;
    }

    @Before
    public void initTest() {
        tipoNormaCatSearchRepository.deleteAll();
        tipoNormaCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoNormaCat() throws Exception {
        int databaseSizeBeforeCreate = tipoNormaCatRepository.findAll().size();

        // Create the TipoNormaCat
        restTipoNormaCatMockMvc.perform(post("/api/tipo-norma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoNormaCat)))
            .andExpect(status().isCreated());

        // Validate the TipoNormaCat in the database
        List<TipoNormaCat> tipoNormaCatList = tipoNormaCatRepository.findAll();
        assertThat(tipoNormaCatList).hasSize(databaseSizeBeforeCreate + 1);
        TipoNormaCat testTipoNormaCat = tipoNormaCatList.get(tipoNormaCatList.size() - 1);
        assertThat(testTipoNormaCat.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoNormaCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the TipoNormaCat in Elasticsearch
        TipoNormaCat tipoNormaCatEs = tipoNormaCatSearchRepository.findOne(testTipoNormaCat.getId());
        assertThat(tipoNormaCatEs).isEqualToComparingFieldByField(testTipoNormaCat);
    }

    @Test
    @Transactional
    public void createTipoNormaCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoNormaCatRepository.findAll().size();

        // Create the TipoNormaCat with an existing ID
        tipoNormaCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoNormaCatMockMvc.perform(post("/api/tipo-norma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoNormaCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TipoNormaCat> tipoNormaCatList = tipoNormaCatRepository.findAll();
        assertThat(tipoNormaCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoNormaCats() throws Exception {
        // Initialize the database
        tipoNormaCatRepository.saveAndFlush(tipoNormaCat);

        // Get all the tipoNormaCatList
        restTipoNormaCatMockMvc.perform(get("/api/tipo-norma-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoNormaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTipoNormaCat() throws Exception {
        // Initialize the database
        tipoNormaCatRepository.saveAndFlush(tipoNormaCat);

        // Get the tipoNormaCat
        restTipoNormaCatMockMvc.perform(get("/api/tipo-norma-cats/{id}", tipoNormaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoNormaCat.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoNormaCat() throws Exception {
        // Get the tipoNormaCat
        restTipoNormaCatMockMvc.perform(get("/api/tipo-norma-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoNormaCat() throws Exception {
        // Initialize the database
        tipoNormaCatService.save(tipoNormaCat);

        int databaseSizeBeforeUpdate = tipoNormaCatRepository.findAll().size();

        // Update the tipoNormaCat
        TipoNormaCat updatedTipoNormaCat = tipoNormaCatRepository.findOne(tipoNormaCat.getId());
        updatedTipoNormaCat
            .nombre(UPDATED_NOMBRE)
            .activo(UPDATED_ACTIVO);

        restTipoNormaCatMockMvc.perform(put("/api/tipo-norma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoNormaCat)))
            .andExpect(status().isOk());

        // Validate the TipoNormaCat in the database
        List<TipoNormaCat> tipoNormaCatList = tipoNormaCatRepository.findAll();
        assertThat(tipoNormaCatList).hasSize(databaseSizeBeforeUpdate);
        TipoNormaCat testTipoNormaCat = tipoNormaCatList.get(tipoNormaCatList.size() - 1);
        assertThat(testTipoNormaCat.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoNormaCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the TipoNormaCat in Elasticsearch
        TipoNormaCat tipoNormaCatEs = tipoNormaCatSearchRepository.findOne(testTipoNormaCat.getId());
        assertThat(tipoNormaCatEs).isEqualToComparingFieldByField(testTipoNormaCat);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoNormaCat() throws Exception {
        int databaseSizeBeforeUpdate = tipoNormaCatRepository.findAll().size();

        // Create the TipoNormaCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoNormaCatMockMvc.perform(put("/api/tipo-norma-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoNormaCat)))
            .andExpect(status().isCreated());

        // Validate the TipoNormaCat in the database
        List<TipoNormaCat> tipoNormaCatList = tipoNormaCatRepository.findAll();
        assertThat(tipoNormaCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoNormaCat() throws Exception {
        // Initialize the database
        tipoNormaCatService.save(tipoNormaCat);

        int databaseSizeBeforeDelete = tipoNormaCatRepository.findAll().size();

        // Get the tipoNormaCat
        restTipoNormaCatMockMvc.perform(delete("/api/tipo-norma-cats/{id}", tipoNormaCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoNormaCatExistsInEs = tipoNormaCatSearchRepository.exists(tipoNormaCat.getId());
        assertThat(tipoNormaCatExistsInEs).isFalse();

        // Validate the database is empty
        List<TipoNormaCat> tipoNormaCatList = tipoNormaCatRepository.findAll();
        assertThat(tipoNormaCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipoNormaCat() throws Exception {
        // Initialize the database
        tipoNormaCatService.save(tipoNormaCat);

        // Search the tipoNormaCat
        restTipoNormaCatMockMvc.perform(get("/api/_search/tipo-norma-cats?query=id:" + tipoNormaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoNormaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoNormaCat.class);
        TipoNormaCat tipoNormaCat1 = new TipoNormaCat();
        tipoNormaCat1.setId(1L);
        TipoNormaCat tipoNormaCat2 = new TipoNormaCat();
        tipoNormaCat2.setId(tipoNormaCat1.getId());
        assertThat(tipoNormaCat1).isEqualTo(tipoNormaCat2);
        tipoNormaCat2.setId(2L);
        assertThat(tipoNormaCat1).isNotEqualTo(tipoNormaCat2);
        tipoNormaCat1.setId(null);
        assertThat(tipoNormaCat1).isNotEqualTo(tipoNormaCat2);
    }
}
