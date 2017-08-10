package mx.gob.profeco.atlas.web.rest;

import mx.gob.profeco.atlas.AtlasApp;

import mx.gob.profeco.atlas.domain.Norma;
import mx.gob.profeco.atlas.repository.NormaRepository;
import mx.gob.profeco.atlas.service.NormaService;
import mx.gob.profeco.atlas.repository.search.NormaSearchRepository;
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
 * Test class for the NormaResource REST controller.
 *
 * @see NormaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasApp.class)
public class NormaResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_FIRMA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIRMA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_RATIFICA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_RATIFICA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_INI_VIGOR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INI_VIGOR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN_VIGOR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN_VIGOR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FIRMANTES = "AAAAAAAAAA";
    private static final String UPDATED_FIRMANTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_JHI_USER_ID = 1L;
    private static final Long UPDATED_JHI_USER_ID = 2L;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private NormaRepository normaRepository;

    @Autowired
    private NormaService normaService;

    @Autowired
    private NormaSearchRepository normaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNormaMockMvc;

    private Norma norma;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NormaResource normaResource = new NormaResource(normaService);
        this.restNormaMockMvc = MockMvcBuilders.standaloneSetup(normaResource)
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
    public static Norma createEntity(EntityManager em) {
        Norma norma = new Norma()
            .fechaFirma(DEFAULT_FECHA_FIRMA)
            .fechaRatifica(DEFAULT_FECHA_RATIFICA)
            .fechaIniVigor(DEFAULT_FECHA_INI_VIGOR)
            .fechaFinVigor(DEFAULT_FECHA_FIN_VIGOR)
            .firmantes(DEFAULT_FIRMANTES)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .jhiUserId(DEFAULT_JHI_USER_ID)
            .activo(DEFAULT_ACTIVO);
        return norma;
    }

    @Before
    public void initTest() {
        normaSearchRepository.deleteAll();
        norma = createEntity(em);
    }

    @Test
    @Transactional
    public void createNorma() throws Exception {
        int databaseSizeBeforeCreate = normaRepository.findAll().size();

        // Create the Norma
        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isCreated());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeCreate + 1);
        Norma testNorma = normaList.get(normaList.size() - 1);
        assertThat(testNorma.getFechaFirma()).isEqualTo(DEFAULT_FECHA_FIRMA);
        assertThat(testNorma.getFechaRatifica()).isEqualTo(DEFAULT_FECHA_RATIFICA);
        assertThat(testNorma.getFechaIniVigor()).isEqualTo(DEFAULT_FECHA_INI_VIGOR);
        assertThat(testNorma.getFechaFinVigor()).isEqualTo(DEFAULT_FECHA_FIN_VIGOR);
        assertThat(testNorma.getFirmantes()).isEqualTo(DEFAULT_FIRMANTES);
        assertThat(testNorma.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testNorma.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testNorma.getJhiUserId()).isEqualTo(DEFAULT_JHI_USER_ID);
        assertThat(testNorma.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the Norma in Elasticsearch
        Norma normaEs = normaSearchRepository.findOne(testNorma.getId());
        assertThat(normaEs).isEqualToComparingFieldByField(testNorma);
    }

    @Test
    @Transactional
    public void createNormaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaRepository.findAll().size();

        // Create the Norma with an existing ID
        norma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNormas() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList
        restNormaMockMvc.perform(get("/api/normas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(norma.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaFirma").value(hasItem(DEFAULT_FECHA_FIRMA.toString())))
            .andExpect(jsonPath("$.[*].fechaRatifica").value(hasItem(DEFAULT_FECHA_RATIFICA.toString())))
            .andExpect(jsonPath("$.[*].fechaIniVigor").value(hasItem(DEFAULT_FECHA_INI_VIGOR.toString())))
            .andExpect(jsonPath("$.[*].fechaFinVigor").value(hasItem(DEFAULT_FECHA_FIN_VIGOR.toString())))
            .andExpect(jsonPath("$.[*].firmantes").value(hasItem(DEFAULT_FIRMANTES.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].jhiUserId").value(hasItem(DEFAULT_JHI_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getNorma() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get the norma
        restNormaMockMvc.perform(get("/api/normas/{id}", norma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(norma.getId().intValue()))
            .andExpect(jsonPath("$.fechaFirma").value(DEFAULT_FECHA_FIRMA.toString()))
            .andExpect(jsonPath("$.fechaRatifica").value(DEFAULT_FECHA_RATIFICA.toString()))
            .andExpect(jsonPath("$.fechaIniVigor").value(DEFAULT_FECHA_INI_VIGOR.toString()))
            .andExpect(jsonPath("$.fechaFinVigor").value(DEFAULT_FECHA_FIN_VIGOR.toString()))
            .andExpect(jsonPath("$.firmantes").value(DEFAULT_FIRMANTES.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.jhiUserId").value(DEFAULT_JHI_USER_ID.intValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNorma() throws Exception {
        // Get the norma
        restNormaMockMvc.perform(get("/api/normas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNorma() throws Exception {
        // Initialize the database
        normaService.save(norma);

        int databaseSizeBeforeUpdate = normaRepository.findAll().size();

        // Update the norma
        Norma updatedNorma = normaRepository.findOne(norma.getId());
        updatedNorma
            .fechaFirma(UPDATED_FECHA_FIRMA)
            .fechaRatifica(UPDATED_FECHA_RATIFICA)
            .fechaIniVigor(UPDATED_FECHA_INI_VIGOR)
            .fechaFinVigor(UPDATED_FECHA_FIN_VIGOR)
            .firmantes(UPDATED_FIRMANTES)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .jhiUserId(UPDATED_JHI_USER_ID)
            .activo(UPDATED_ACTIVO);

        restNormaMockMvc.perform(put("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNorma)))
            .andExpect(status().isOk());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeUpdate);
        Norma testNorma = normaList.get(normaList.size() - 1);
        assertThat(testNorma.getFechaFirma()).isEqualTo(UPDATED_FECHA_FIRMA);
        assertThat(testNorma.getFechaRatifica()).isEqualTo(UPDATED_FECHA_RATIFICA);
        assertThat(testNorma.getFechaIniVigor()).isEqualTo(UPDATED_FECHA_INI_VIGOR);
        assertThat(testNorma.getFechaFinVigor()).isEqualTo(UPDATED_FECHA_FIN_VIGOR);
        assertThat(testNorma.getFirmantes()).isEqualTo(UPDATED_FIRMANTES);
        assertThat(testNorma.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testNorma.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testNorma.getJhiUserId()).isEqualTo(UPDATED_JHI_USER_ID);
        assertThat(testNorma.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the Norma in Elasticsearch
        Norma normaEs = normaSearchRepository.findOne(testNorma.getId());
        assertThat(normaEs).isEqualToComparingFieldByField(testNorma);
    }

    @Test
    @Transactional
    public void updateNonExistingNorma() throws Exception {
        int databaseSizeBeforeUpdate = normaRepository.findAll().size();

        // Create the Norma

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNormaMockMvc.perform(put("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isCreated());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNorma() throws Exception {
        // Initialize the database
        normaService.save(norma);

        int databaseSizeBeforeDelete = normaRepository.findAll().size();

        // Get the norma
        restNormaMockMvc.perform(delete("/api/normas/{id}", norma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean normaExistsInEs = normaSearchRepository.exists(norma.getId());
        assertThat(normaExistsInEs).isFalse();

        // Validate the database is empty
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNorma() throws Exception {
        // Initialize the database
        normaService.save(norma);

        // Search the norma
        restNormaMockMvc.perform(get("/api/_search/normas?query=id:" + norma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(norma.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaFirma").value(hasItem(DEFAULT_FECHA_FIRMA.toString())))
            .andExpect(jsonPath("$.[*].fechaRatifica").value(hasItem(DEFAULT_FECHA_RATIFICA.toString())))
            .andExpect(jsonPath("$.[*].fechaIniVigor").value(hasItem(DEFAULT_FECHA_INI_VIGOR.toString())))
            .andExpect(jsonPath("$.[*].fechaFinVigor").value(hasItem(DEFAULT_FECHA_FIN_VIGOR.toString())))
            .andExpect(jsonPath("$.[*].firmantes").value(hasItem(DEFAULT_FIRMANTES.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].jhiUserId").value(hasItem(DEFAULT_JHI_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Norma.class);
        Norma norma1 = new Norma();
        norma1.setId(1L);
        Norma norma2 = new Norma();
        norma2.setId(norma1.getId());
        assertThat(norma1).isEqualTo(norma2);
        norma2.setId(2L);
        assertThat(norma1).isNotEqualTo(norma2);
        norma1.setId(null);
        assertThat(norma1).isNotEqualTo(norma2);
    }
}
