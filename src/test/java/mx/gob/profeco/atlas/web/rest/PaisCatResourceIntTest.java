package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.PaisCat;
import mx.gob.profeco.atlas.repository.PaisCatRepository;
import mx.gob.profeco.atlas.service.PaisCatService;
import mx.gob.profeco.atlas.repository.search.PaisCatSearchRepository;
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
 * Test class for the PaisCatResource REST controller.
 *
 * @see PaisCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class PaisCatResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private PaisCatRepository paisCatRepository;

    @Autowired
    private PaisCatService paisCatService;

    @Autowired
    private PaisCatSearchRepository paisCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaisCatMockMvc;

    private PaisCat paisCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaisCatResource paisCatResource = new PaisCatResource(paisCatService);
        this.restPaisCatMockMvc = MockMvcBuilders.standaloneSetup(paisCatResource)
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
    public static PaisCat createEntity(EntityManager em) {
        PaisCat paisCat = new PaisCat()
            .nombre(DEFAULT_NOMBRE)
            .activo(DEFAULT_ACTIVO);
        return paisCat;
    }

    @Before
    public void initTest() {
        paisCatSearchRepository.deleteAll();
        paisCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaisCat() throws Exception {
        int databaseSizeBeforeCreate = paisCatRepository.findAll().size();

        // Create the PaisCat
        restPaisCatMockMvc.perform(post("/api/pais-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paisCat)))
            .andExpect(status().isCreated());

        // Validate the PaisCat in the database
        List<PaisCat> paisCatList = paisCatRepository.findAll();
        assertThat(paisCatList).hasSize(databaseSizeBeforeCreate + 1);
        PaisCat testPaisCat = paisCatList.get(paisCatList.size() - 1);
        assertThat(testPaisCat.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPaisCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the PaisCat in Elasticsearch
        PaisCat paisCatEs = paisCatSearchRepository.findOne(testPaisCat.getId());
        assertThat(paisCatEs).isEqualToComparingFieldByField(testPaisCat);
    }

    @Test
    @Transactional
    public void createPaisCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paisCatRepository.findAll().size();

        // Create the PaisCat with an existing ID
        paisCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaisCatMockMvc.perform(post("/api/pais-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paisCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaisCat> paisCatList = paisCatRepository.findAll();
        assertThat(paisCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaisCats() throws Exception {
        // Initialize the database
        paisCatRepository.saveAndFlush(paisCat);

        // Get all the paisCatList
        restPaisCatMockMvc.perform(get("/api/pais-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paisCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPaisCat() throws Exception {
        // Initialize the database
        paisCatRepository.saveAndFlush(paisCat);

        // Get the paisCat
        restPaisCatMockMvc.perform(get("/api/pais-cats/{id}", paisCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paisCat.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaisCat() throws Exception {
        // Get the paisCat
        restPaisCatMockMvc.perform(get("/api/pais-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaisCat() throws Exception {
        // Initialize the database
        paisCatService.save(paisCat);

        int databaseSizeBeforeUpdate = paisCatRepository.findAll().size();

        // Update the paisCat
        PaisCat updatedPaisCat = paisCatRepository.findOne(paisCat.getId());
        updatedPaisCat
            .nombre(UPDATED_NOMBRE)
            .activo(UPDATED_ACTIVO);

        restPaisCatMockMvc.perform(put("/api/pais-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaisCat)))
            .andExpect(status().isOk());

        // Validate the PaisCat in the database
        List<PaisCat> paisCatList = paisCatRepository.findAll();
        assertThat(paisCatList).hasSize(databaseSizeBeforeUpdate);
        PaisCat testPaisCat = paisCatList.get(paisCatList.size() - 1);
        assertThat(testPaisCat.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPaisCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the PaisCat in Elasticsearch
        PaisCat paisCatEs = paisCatSearchRepository.findOne(testPaisCat.getId());
        assertThat(paisCatEs).isEqualToComparingFieldByField(testPaisCat);
    }

    @Test
    @Transactional
    public void updateNonExistingPaisCat() throws Exception {
        int databaseSizeBeforeUpdate = paisCatRepository.findAll().size();

        // Create the PaisCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaisCatMockMvc.perform(put("/api/pais-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paisCat)))
            .andExpect(status().isCreated());

        // Validate the PaisCat in the database
        List<PaisCat> paisCatList = paisCatRepository.findAll();
        assertThat(paisCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaisCat() throws Exception {
        // Initialize the database
        paisCatService.save(paisCat);

        int databaseSizeBeforeDelete = paisCatRepository.findAll().size();

        // Get the paisCat
        restPaisCatMockMvc.perform(delete("/api/pais-cats/{id}", paisCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean paisCatExistsInEs = paisCatSearchRepository.exists(paisCat.getId());
        assertThat(paisCatExistsInEs).isFalse();

        // Validate the database is empty
        List<PaisCat> paisCatList = paisCatRepository.findAll();
        assertThat(paisCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPaisCat() throws Exception {
        // Initialize the database
        paisCatService.save(paisCat);

        // Search the paisCat
        restPaisCatMockMvc.perform(get("/api/_search/pais-cats?query=id:" + paisCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paisCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaisCat.class);
        PaisCat paisCat1 = new PaisCat();
        paisCat1.setId(1L);
        PaisCat paisCat2 = new PaisCat();
        paisCat2.setId(paisCat1.getId());
        assertThat(paisCat1).isEqualTo(paisCat2);
        paisCat2.setId(2L);
        assertThat(paisCat1).isNotEqualTo(paisCat2);
        paisCat1.setId(null);
        assertThat(paisCat1).isNotEqualTo(paisCat2);
    }
}
