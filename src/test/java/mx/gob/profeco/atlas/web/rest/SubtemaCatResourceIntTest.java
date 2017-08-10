package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.SubtemaCat;
import mx.gob.profeco.atlas.repository.SubtemaCatRepository;
import mx.gob.profeco.atlas.service.SubtemaCatService;
import mx.gob.profeco.atlas.repository.search.SubtemaCatSearchRepository;
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
 * Test class for the SubtemaCatResource REST controller.
 *
 * @see SubtemaCatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class SubtemaCatResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private SubtemaCatRepository subtemaCatRepository;

    @Autowired
    private SubtemaCatService subtemaCatService;

    @Autowired
    private SubtemaCatSearchRepository subtemaCatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubtemaCatMockMvc;

    private SubtemaCat subtemaCat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubtemaCatResource subtemaCatResource = new SubtemaCatResource(subtemaCatService);
        this.restSubtemaCatMockMvc = MockMvcBuilders.standaloneSetup(subtemaCatResource)
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
    public static SubtemaCat createEntity(EntityManager em) {
        SubtemaCat subtemaCat = new SubtemaCat()
            .nombre(DEFAULT_NOMBRE)
            .activo(DEFAULT_ACTIVO);
        return subtemaCat;
    }

    @Before
    public void initTest() {
        subtemaCatSearchRepository.deleteAll();
        subtemaCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubtemaCat() throws Exception {
        int databaseSizeBeforeCreate = subtemaCatRepository.findAll().size();

        // Create the SubtemaCat
        restSubtemaCatMockMvc.perform(post("/api/subtema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subtemaCat)))
            .andExpect(status().isCreated());

        // Validate the SubtemaCat in the database
        List<SubtemaCat> subtemaCatList = subtemaCatRepository.findAll();
        assertThat(subtemaCatList).hasSize(databaseSizeBeforeCreate + 1);
        SubtemaCat testSubtemaCat = subtemaCatList.get(subtemaCatList.size() - 1);
        assertThat(testSubtemaCat.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubtemaCat.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the SubtemaCat in Elasticsearch
        SubtemaCat subtemaCatEs = subtemaCatSearchRepository.findOne(testSubtemaCat.getId());
        assertThat(subtemaCatEs).isEqualToComparingFieldByField(testSubtemaCat);
    }

    @Test
    @Transactional
    public void createSubtemaCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subtemaCatRepository.findAll().size();

        // Create the SubtemaCat with an existing ID
        subtemaCat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubtemaCatMockMvc.perform(post("/api/subtema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subtemaCat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SubtemaCat> subtemaCatList = subtemaCatRepository.findAll();
        assertThat(subtemaCatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubtemaCats() throws Exception {
        // Initialize the database
        subtemaCatRepository.saveAndFlush(subtemaCat);

        // Get all the subtemaCatList
        restSubtemaCatMockMvc.perform(get("/api/subtema-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subtemaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getSubtemaCat() throws Exception {
        // Initialize the database
        subtemaCatRepository.saveAndFlush(subtemaCat);

        // Get the subtemaCat
        restSubtemaCatMockMvc.perform(get("/api/subtema-cats/{id}", subtemaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subtemaCat.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSubtemaCat() throws Exception {
        // Get the subtemaCat
        restSubtemaCatMockMvc.perform(get("/api/subtema-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubtemaCat() throws Exception {
        // Initialize the database
        subtemaCatService.save(subtemaCat);

        int databaseSizeBeforeUpdate = subtemaCatRepository.findAll().size();

        // Update the subtemaCat
        SubtemaCat updatedSubtemaCat = subtemaCatRepository.findOne(subtemaCat.getId());
        updatedSubtemaCat
            .nombre(UPDATED_NOMBRE)
            .activo(UPDATED_ACTIVO);

        restSubtemaCatMockMvc.perform(put("/api/subtema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubtemaCat)))
            .andExpect(status().isOk());

        // Validate the SubtemaCat in the database
        List<SubtemaCat> subtemaCatList = subtemaCatRepository.findAll();
        assertThat(subtemaCatList).hasSize(databaseSizeBeforeUpdate);
        SubtemaCat testSubtemaCat = subtemaCatList.get(subtemaCatList.size() - 1);
        assertThat(testSubtemaCat.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubtemaCat.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the SubtemaCat in Elasticsearch
        SubtemaCat subtemaCatEs = subtemaCatSearchRepository.findOne(testSubtemaCat.getId());
        assertThat(subtemaCatEs).isEqualToComparingFieldByField(testSubtemaCat);
    }

    @Test
    @Transactional
    public void updateNonExistingSubtemaCat() throws Exception {
        int databaseSizeBeforeUpdate = subtemaCatRepository.findAll().size();

        // Create the SubtemaCat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubtemaCatMockMvc.perform(put("/api/subtema-cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subtemaCat)))
            .andExpect(status().isCreated());

        // Validate the SubtemaCat in the database
        List<SubtemaCat> subtemaCatList = subtemaCatRepository.findAll();
        assertThat(subtemaCatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubtemaCat() throws Exception {
        // Initialize the database
        subtemaCatService.save(subtemaCat);

        int databaseSizeBeforeDelete = subtemaCatRepository.findAll().size();

        // Get the subtemaCat
        restSubtemaCatMockMvc.perform(delete("/api/subtema-cats/{id}", subtemaCat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean subtemaCatExistsInEs = subtemaCatSearchRepository.exists(subtemaCat.getId());
        assertThat(subtemaCatExistsInEs).isFalse();

        // Validate the database is empty
        List<SubtemaCat> subtemaCatList = subtemaCatRepository.findAll();
        assertThat(subtemaCatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSubtemaCat() throws Exception {
        // Initialize the database
        subtemaCatService.save(subtemaCat);

        // Search the subtemaCat
        restSubtemaCatMockMvc.perform(get("/api/_search/subtema-cats?query=id:" + subtemaCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subtemaCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubtemaCat.class);
        SubtemaCat subtemaCat1 = new SubtemaCat();
        subtemaCat1.setId(1L);
        SubtemaCat subtemaCat2 = new SubtemaCat();
        subtemaCat2.setId(subtemaCat1.getId());
        assertThat(subtemaCat1).isEqualTo(subtemaCat2);
        subtemaCat2.setId(2L);
        assertThat(subtemaCat1).isNotEqualTo(subtemaCat2);
        subtemaCat1.setId(null);
        assertThat(subtemaCat1).isNotEqualTo(subtemaCat2);
    }
}
