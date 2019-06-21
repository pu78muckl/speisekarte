package com.spirittesting.speisekarte.web.rest;

import com.spirittesting.speisekarte.SpeisekarteApp;

import com.spirittesting.speisekarte.domain.Kunde;
import com.spirittesting.speisekarte.repository.KundeRepository;
import com.spirittesting.speisekarte.service.KundeService;
import com.spirittesting.speisekarte.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import java.util.List;


import static com.spirittesting.speisekarte.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KundeResource REST controller.
 *
 * @see KundeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpeisekarteApp.class)
public class KundeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private KundeService kundeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restKundeMockMvc;

    private Kunde kunde;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KundeResource kundeResource = new KundeResource(kundeService);
        this.restKundeMockMvc = MockMvcBuilders.standaloneSetup(kundeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kunde createEntity() {
        Kunde kunde = new Kunde()
            .name(DEFAULT_NAME)
            .vorname(DEFAULT_VORNAME);
        return kunde;
    }

    @Before
    public void initTest() {
        kundeRepository.deleteAll();
        kunde = createEntity();
    }

    @Test
    public void createKunde() throws Exception {
        int databaseSizeBeforeCreate = kundeRepository.findAll().size();

        // Create the Kunde
        restKundeMockMvc.perform(post("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kunde)))
            .andExpect(status().isCreated());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeCreate + 1);
        Kunde testKunde = kundeList.get(kundeList.size() - 1);
        assertThat(testKunde.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKunde.getVorname()).isEqualTo(DEFAULT_VORNAME);
    }

    @Test
    public void createKundeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kundeRepository.findAll().size();

        // Create the Kunde with an existing ID
        kunde.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restKundeMockMvc.perform(post("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kunde)))
            .andExpect(status().isBadRequest());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kundeRepository.findAll().size();
        // set the field null
        kunde.setName(null);

        // Create the Kunde, which fails.

        restKundeMockMvc.perform(post("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kunde)))
            .andExpect(status().isBadRequest());

        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVornameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kundeRepository.findAll().size();
        // set the field null
        kunde.setVorname(null);

        // Create the Kunde, which fails.

        restKundeMockMvc.perform(post("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kunde)))
            .andExpect(status().isBadRequest());

        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllKundes() throws Exception {
        // Initialize the database
        kundeRepository.save(kunde);

        // Get all the kundeList
        restKundeMockMvc.perform(get("/api/kundes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kunde.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME.toString())));
    }
    
    @Test
    public void getKunde() throws Exception {
        // Initialize the database
        kundeRepository.save(kunde);

        // Get the kunde
        restKundeMockMvc.perform(get("/api/kundes/{id}", kunde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kunde.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME.toString()));
    }

    @Test
    public void getNonExistingKunde() throws Exception {
        // Get the kunde
        restKundeMockMvc.perform(get("/api/kundes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKunde() throws Exception {
        // Initialize the database
        kundeService.save(kunde);

        int databaseSizeBeforeUpdate = kundeRepository.findAll().size();

        // Update the kunde
        Kunde updatedKunde = kundeRepository.findById(kunde.getId()).get();
        updatedKunde
            .name(UPDATED_NAME)
            .vorname(UPDATED_VORNAME);

        restKundeMockMvc.perform(put("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKunde)))
            .andExpect(status().isOk());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeUpdate);
        Kunde testKunde = kundeList.get(kundeList.size() - 1);
        assertThat(testKunde.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKunde.getVorname()).isEqualTo(UPDATED_VORNAME);
    }

    @Test
    public void updateNonExistingKunde() throws Exception {
        int databaseSizeBeforeUpdate = kundeRepository.findAll().size();

        // Create the Kunde

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKundeMockMvc.perform(put("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kunde)))
            .andExpect(status().isBadRequest());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteKunde() throws Exception {
        // Initialize the database
        kundeService.save(kunde);

        int databaseSizeBeforeDelete = kundeRepository.findAll().size();

        // Get the kunde
        restKundeMockMvc.perform(delete("/api/kundes/{id}", kunde.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kunde.class);
        Kunde kunde1 = new Kunde();
        kunde1.setId("id1");
        Kunde kunde2 = new Kunde();
        kunde2.setId(kunde1.getId());
        assertThat(kunde1).isEqualTo(kunde2);
        kunde2.setId("id2");
        assertThat(kunde1).isNotEqualTo(kunde2);
        kunde1.setId(null);
        assertThat(kunde1).isNotEqualTo(kunde2);
    }
}
