package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.Archivo;
import mx.gob.profeco.atlas.repository.ArchivoRepository;
import mx.gob.profeco.atlas.service.ArchivoService;
import mx.gob.profeco.atlas.repository.search.ArchivoSearchRepository;
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
 * Test class for the ArchivoResource REST controller.
 *
 * @see ArchivoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class ArchivoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final Long DEFAULT_JHI_USER_ID = 1L;
    private static final Long UPDATED_JHI_USER_ID = 2L;

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_NORMA_IDIOMA_NORMA_ID = 1L;
    private static final Long UPDATED_NORMA_IDIOMA_NORMA_ID = 2L;

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private ArchivoSearchRepository archivoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArchivoMockMvc;

    private Archivo archivo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArchivoResource archivoResource = new ArchivoResource(archivoService);
        this.restArchivoMockMvc = MockMvcBuilders.standaloneSetup(archivoResource)
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
    public static Archivo createEntity(EntityManager em) {
        Archivo archivo = new Archivo()
            .nombre(DEFAULT_NOMBRE)
            .path(DEFAULT_PATH)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .activo(DEFAULT_ACTIVO)
            .jhiUserId(DEFAULT_JHI_USER_ID)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .normaIdiomaNormaId(DEFAULT_NORMA_IDIOMA_NORMA_ID);
        return archivo;
    }

    @Before
    public void initTest() {
        archivoSearchRepository.deleteAll();
        archivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArchivo() throws Exception {
        int databaseSizeBeforeCreate = archivoRepository.findAll().size();

        // Create the Archivo
        restArchivoMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isCreated());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeCreate + 1);
        Archivo testArchivo = archivoList.get(archivoList.size() - 1);
        assertThat(testArchivo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testArchivo.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testArchivo.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testArchivo.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testArchivo.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testArchivo.getJhiUserId()).isEqualTo(DEFAULT_JHI_USER_ID);
        assertThat(testArchivo.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testArchivo.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testArchivo.getNormaIdiomaNormaId()).isEqualTo(DEFAULT_NORMA_IDIOMA_NORMA_ID);

        // Validate the Archivo in Elasticsearch
        Archivo archivoEs = archivoSearchRepository.findOne(testArchivo.getId());
        assertThat(archivoEs).isEqualToComparingFieldByField(testArchivo);
    }

    @Test
    @Transactional
    public void createArchivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = archivoRepository.findAll().size();

        // Create the Archivo with an existing ID
        archivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivoMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArchivos() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        // Get all the archivoList
        restArchivoMockMvc.perform(get("/api/archivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].jhiUserId").value(hasItem(DEFAULT_JHI_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void getArchivo() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        // Get the archivo
        restArchivoMockMvc.perform(get("/api/archivos/{id}", archivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(archivo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.jhiUserId").value(DEFAULT_JHI_USER_ID.intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.normaIdiomaNormaId").value(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingArchivo() throws Exception {
        // Get the archivo
        restArchivoMockMvc.perform(get("/api/archivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchivo() throws Exception {
        // Initialize the database
        archivoService.save(archivo);

        int databaseSizeBeforeUpdate = archivoRepository.findAll().size();

        // Update the archivo
        Archivo updatedArchivo = archivoRepository.findOne(archivo.getId());
        updatedArchivo
            .nombre(UPDATED_NOMBRE)
            .path(UPDATED_PATH)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .activo(UPDATED_ACTIVO)
            .jhiUserId(UPDATED_JHI_USER_ID)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .normaIdiomaNormaId(UPDATED_NORMA_IDIOMA_NORMA_ID);

        restArchivoMockMvc.perform(put("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArchivo)))
            .andExpect(status().isOk());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeUpdate);
        Archivo testArchivo = archivoList.get(archivoList.size() - 1);
        assertThat(testArchivo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testArchivo.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testArchivo.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testArchivo.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testArchivo.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testArchivo.getJhiUserId()).isEqualTo(UPDATED_JHI_USER_ID);
        assertThat(testArchivo.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testArchivo.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testArchivo.getNormaIdiomaNormaId()).isEqualTo(UPDATED_NORMA_IDIOMA_NORMA_ID);

        // Validate the Archivo in Elasticsearch
        Archivo archivoEs = archivoSearchRepository.findOne(testArchivo.getId());
        assertThat(archivoEs).isEqualToComparingFieldByField(testArchivo);
    }

    @Test
    @Transactional
    public void updateNonExistingArchivo() throws Exception {
        int databaseSizeBeforeUpdate = archivoRepository.findAll().size();

        // Create the Archivo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArchivoMockMvc.perform(put("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isCreated());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArchivo() throws Exception {
        // Initialize the database
        archivoService.save(archivo);

        int databaseSizeBeforeDelete = archivoRepository.findAll().size();

        // Get the archivo
        restArchivoMockMvc.perform(delete("/api/archivos/{id}", archivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean archivoExistsInEs = archivoSearchRepository.exists(archivo.getId());
        assertThat(archivoExistsInEs).isFalse();

        // Validate the database is empty
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchArchivo() throws Exception {
        // Initialize the database
        archivoService.save(archivo);

        // Search the archivo
        restArchivoMockMvc.perform(get("/api/_search/archivos?query=id:" + archivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].jhiUserId").value(hasItem(DEFAULT_JHI_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].normaIdiomaNormaId").value(hasItem(DEFAULT_NORMA_IDIOMA_NORMA_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archivo.class);
        Archivo archivo1 = new Archivo();
        archivo1.setId(1L);
        Archivo archivo2 = new Archivo();
        archivo2.setId(archivo1.getId());
        assertThat(archivo1).isEqualTo(archivo2);
        archivo2.setId(2L);
        assertThat(archivo1).isNotEqualTo(archivo2);
        archivo1.setId(null);
        assertThat(archivo1).isNotEqualTo(archivo2);
    }
}
