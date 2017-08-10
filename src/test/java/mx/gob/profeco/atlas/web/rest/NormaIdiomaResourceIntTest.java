package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.NormaIdioma;
import mx.gob.profeco.atlas.repository.NormaIdiomaRepository;
import mx.gob.profeco.atlas.service.NormaIdiomaService;
import mx.gob.profeco.atlas.repository.search.NormaIdiomaSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NormaIdiomaResource REST controller.
 *
 * @see NormaIdiomaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class NormaIdiomaResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NormaIdiomaRepository normaIdiomaRepository;

    @Autowired
    private NormaIdiomaService normaIdiomaService;

    @Autowired
    private NormaIdiomaSearchRepository normaIdiomaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNormaIdiomaMockMvc;

    private NormaIdioma normaIdioma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NormaIdiomaResource normaIdiomaResource = new NormaIdiomaResource(normaIdiomaService);
        this.restNormaIdiomaMockMvc = MockMvcBuilders.standaloneSetup(normaIdiomaResource)
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
    public static NormaIdioma createEntity(EntityManager em) {
        NormaIdioma normaIdioma = new NormaIdioma()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .texto(DEFAULT_TEXTO)
            .link(DEFAULT_LINK)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
        return normaIdioma;
    }

    @Before
    public void initTest() {
        normaIdiomaSearchRepository.deleteAll();
        normaIdioma = createEntity(em);
    }

    @Test
    @Transactional
    public void createNormaIdioma() throws Exception {
        int databaseSizeBeforeCreate = normaIdiomaRepository.findAll().size();

        // Create the NormaIdioma
        restNormaIdiomaMockMvc.perform(post("/api/norma-idiomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaIdioma)))
            .andExpect(status().isCreated());

        // Validate the NormaIdioma in the database
        List<NormaIdioma> normaIdiomaList = normaIdiomaRepository.findAll();
        assertThat(normaIdiomaList).hasSize(databaseSizeBeforeCreate + 1);
        NormaIdioma testNormaIdioma = normaIdiomaList.get(normaIdiomaList.size() - 1);
        assertThat(testNormaIdioma.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNormaIdioma.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNormaIdioma.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testNormaIdioma.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testNormaIdioma.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);

        // Validate the NormaIdioma in Elasticsearch
        NormaIdioma normaIdiomaEs = normaIdiomaSearchRepository.findOne(testNormaIdioma.getId());
        assertThat(normaIdiomaEs).isEqualToComparingFieldByField(testNormaIdioma);
    }

    @Test
    @Transactional
    public void createNormaIdiomaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaIdiomaRepository.findAll().size();

        // Create the NormaIdioma with an existing ID
        normaIdioma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaIdiomaMockMvc.perform(post("/api/norma-idiomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaIdioma)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NormaIdioma> normaIdiomaList = normaIdiomaRepository.findAll();
        assertThat(normaIdiomaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNormaIdiomas() throws Exception {
        // Initialize the database
        normaIdiomaRepository.saveAndFlush(normaIdioma);

        // Get all the normaIdiomaList
        restNormaIdiomaMockMvc.perform(get("/api/norma-idiomas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaIdioma.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }

    @Test
    @Transactional
    public void getNormaIdioma() throws Exception {
        // Initialize the database
        normaIdiomaRepository.saveAndFlush(normaIdioma);

        // Get the normaIdioma
        restNormaIdiomaMockMvc.perform(get("/api/norma-idiomas/{id}", normaIdioma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(normaIdioma.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNormaIdioma() throws Exception {
        // Get the normaIdioma
        restNormaIdiomaMockMvc.perform(get("/api/norma-idiomas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNormaIdioma() throws Exception {
        // Initialize the database
        normaIdiomaService.save(normaIdioma);

        int databaseSizeBeforeUpdate = normaIdiomaRepository.findAll().size();

        // Update the normaIdioma
        NormaIdioma updatedNormaIdioma = normaIdiomaRepository.findOne(normaIdioma.getId());
        updatedNormaIdioma
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .texto(UPDATED_TEXTO)
            .link(UPDATED_LINK)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restNormaIdiomaMockMvc.perform(put("/api/norma-idiomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNormaIdioma)))
            .andExpect(status().isOk());

        // Validate the NormaIdioma in the database
        List<NormaIdioma> normaIdiomaList = normaIdiomaRepository.findAll();
        assertThat(normaIdiomaList).hasSize(databaseSizeBeforeUpdate);
        NormaIdioma testNormaIdioma = normaIdiomaList.get(normaIdiomaList.size() - 1);
        assertThat(testNormaIdioma.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNormaIdioma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNormaIdioma.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testNormaIdioma.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testNormaIdioma.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);

        // Validate the NormaIdioma in Elasticsearch
        NormaIdioma normaIdiomaEs = normaIdiomaSearchRepository.findOne(testNormaIdioma.getId());
        assertThat(normaIdiomaEs).isEqualToComparingFieldByField(testNormaIdioma);
    }

    @Test
    @Transactional
    public void updateNonExistingNormaIdioma() throws Exception {
        int databaseSizeBeforeUpdate = normaIdiomaRepository.findAll().size();

        // Create the NormaIdioma

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNormaIdiomaMockMvc.perform(put("/api/norma-idiomas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaIdioma)))
            .andExpect(status().isCreated());

        // Validate the NormaIdioma in the database
        List<NormaIdioma> normaIdiomaList = normaIdiomaRepository.findAll();
        assertThat(normaIdiomaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNormaIdioma() throws Exception {
        // Initialize the database
        normaIdiomaService.save(normaIdioma);

        int databaseSizeBeforeDelete = normaIdiomaRepository.findAll().size();

        // Get the normaIdioma
        restNormaIdiomaMockMvc.perform(delete("/api/norma-idiomas/{id}", normaIdioma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean normaIdiomaExistsInEs = normaIdiomaSearchRepository.exists(normaIdioma.getId());
        assertThat(normaIdiomaExistsInEs).isFalse();

        // Validate the database is empty
        List<NormaIdioma> normaIdiomaList = normaIdiomaRepository.findAll();
        assertThat(normaIdiomaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNormaIdioma() throws Exception {
        // Initialize the database
        normaIdiomaService.save(normaIdioma);

        // Search the normaIdioma
        restNormaIdiomaMockMvc.perform(get("/api/_search/norma-idiomas?query=id:" + normaIdioma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaIdioma.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NormaIdioma.class);
        NormaIdioma normaIdioma1 = new NormaIdioma();
        normaIdioma1.setId(1L);
        NormaIdioma normaIdioma2 = new NormaIdioma();
        normaIdioma2.setId(normaIdioma1.getId());
        assertThat(normaIdioma1).isEqualTo(normaIdioma2);
        normaIdioma2.setId(2L);
        assertThat(normaIdioma1).isNotEqualTo(normaIdioma2);
        normaIdioma1.setId(null);
        assertThat(normaIdioma1).isNotEqualTo(normaIdioma2);
    }
}
