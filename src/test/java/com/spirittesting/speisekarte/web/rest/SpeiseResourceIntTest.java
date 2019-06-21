package com.spirittesting.speisekarte.web.rest;

import com.spirittesting.speisekarte.SpeisekarteApp;

import com.spirittesting.speisekarte.domain.Speise;
import com.spirittesting.speisekarte.repository.SpeiseRepository;
import com.spirittesting.speisekarte.service.SpeiseService;
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
 * Test class for the SpeiseResource REST controller.
 *
 * @see SpeiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpeisekarteApp.class)
public class SpeiseResourceIntTest {

    private static final String DEFAULT_BEZEICHNUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEZEICHNUNG = "BBBBBBBBBB";

    private static final Float DEFAULT_PREIS = 1F;
    private static final Float UPDATED_PREIS = 2F;

    private static final String DEFAULT_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_BESCHREIBUNG = "BBBBBBBBBB";

    private static final String DEFAULT_KUNDENID = "AAAAAAAAAA";
    private static final String UPDATED_KUNDENID = "BBBBBBBBBB";

    private static final String DEFAULT_SPEISEKARTENID = "AAAAAAAAAA";
    private static final String UPDATED_SPEISEKARTENID = "BBBBBBBBBB";

    @Autowired
    private SpeiseRepository speiseRepository;

    @Autowired
    private SpeiseService speiseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSpeiseMockMvc;

    private Speise speise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpeiseResource speiseResource = new SpeiseResource(speiseService);
        this.restSpeiseMockMvc = MockMvcBuilders.standaloneSetup(speiseResource)
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
    public static Speise createEntity() {
        Speise speise = new Speise()
            .bezeichnung(DEFAULT_BEZEICHNUNG)
            .preis(DEFAULT_PREIS)
            .beschreibung(DEFAULT_BESCHREIBUNG)
            .kundenid(DEFAULT_KUNDENID)
            .speisekartenid(DEFAULT_SPEISEKARTENID);
        return speise;
    }

    @Before
    public void initTest() {
        speiseRepository.deleteAll();
        speise = createEntity();
    }

    @Test
    public void createSpeise() throws Exception {
        int databaseSizeBeforeCreate = speiseRepository.findAll().size();

        // Create the Speise
        restSpeiseMockMvc.perform(post("/api/speises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speise)))
            .andExpect(status().isCreated());

        // Validate the Speise in the database
        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeCreate + 1);
        Speise testSpeise = speiseList.get(speiseList.size() - 1);
        assertThat(testSpeise.getBezeichnung()).isEqualTo(DEFAULT_BEZEICHNUNG);
        assertThat(testSpeise.getPreis()).isEqualTo(DEFAULT_PREIS);
        assertThat(testSpeise.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
        assertThat(testSpeise.getKundenid()).isEqualTo(DEFAULT_KUNDENID);
        assertThat(testSpeise.getSpeisekartenid()).isEqualTo(DEFAULT_SPEISEKARTENID);
    }

    @Test
    public void createSpeiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speiseRepository.findAll().size();

        // Create the Speise with an existing ID
        speise.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeiseMockMvc.perform(post("/api/speises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speise)))
            .andExpect(status().isBadRequest());

        // Validate the Speise in the database
        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkBezeichnungIsRequired() throws Exception {
        int databaseSizeBeforeTest = speiseRepository.findAll().size();
        // set the field null
        speise.setBezeichnung(null);

        // Create the Speise, which fails.

        restSpeiseMockMvc.perform(post("/api/speises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speise)))
            .andExpect(status().isBadRequest());

        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPreisIsRequired() throws Exception {
        int databaseSizeBeforeTest = speiseRepository.findAll().size();
        // set the field null
        speise.setPreis(null);

        // Create the Speise, which fails.

        restSpeiseMockMvc.perform(post("/api/speises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speise)))
            .andExpect(status().isBadRequest());

        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSpeises() throws Exception {
        // Initialize the database
        speiseRepository.save(speise);

        // Get all the speiseList
        restSpeiseMockMvc.perform(get("/api/speises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speise.getId())))
            .andExpect(jsonPath("$.[*].bezeichnung").value(hasItem(DEFAULT_BEZEICHNUNG.toString())))
            .andExpect(jsonPath("$.[*].preis").value(hasItem(DEFAULT_PREIS.doubleValue())))
            .andExpect(jsonPath("$.[*].beschreibung").value(hasItem(DEFAULT_BESCHREIBUNG.toString())))
            .andExpect(jsonPath("$.[*].kundenid").value(hasItem(DEFAULT_KUNDENID.toString())))
            .andExpect(jsonPath("$.[*].speisekartenid").value(hasItem(DEFAULT_SPEISEKARTENID.toString())));
    }
    
    @Test
    public void getSpeise() throws Exception {
        // Initialize the database
        speiseRepository.save(speise);

        // Get the speise
        restSpeiseMockMvc.perform(get("/api/speises/{id}", speise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speise.getId()))
            .andExpect(jsonPath("$.bezeichnung").value(DEFAULT_BEZEICHNUNG.toString()))
            .andExpect(jsonPath("$.preis").value(DEFAULT_PREIS.doubleValue()))
            .andExpect(jsonPath("$.beschreibung").value(DEFAULT_BESCHREIBUNG.toString()))
            .andExpect(jsonPath("$.kundenid").value(DEFAULT_KUNDENID.toString()))
            .andExpect(jsonPath("$.speisekartenid").value(DEFAULT_SPEISEKARTENID.toString()));
    }

    @Test
    public void getNonExistingSpeise() throws Exception {
        // Get the speise
        restSpeiseMockMvc.perform(get("/api/speises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpeise() throws Exception {
        // Initialize the database
        speiseService.save(speise);

        int databaseSizeBeforeUpdate = speiseRepository.findAll().size();

        // Update the speise
        Speise updatedSpeise = speiseRepository.findById(speise.getId()).get();
        updatedSpeise
            .bezeichnung(UPDATED_BEZEICHNUNG)
            .preis(UPDATED_PREIS)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .kundenid(UPDATED_KUNDENID)
            .speisekartenid(UPDATED_SPEISEKARTENID);

        restSpeiseMockMvc.perform(put("/api/speises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeise)))
            .andExpect(status().isOk());

        // Validate the Speise in the database
        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeUpdate);
        Speise testSpeise = speiseList.get(speiseList.size() - 1);
        assertThat(testSpeise.getBezeichnung()).isEqualTo(UPDATED_BEZEICHNUNG);
        assertThat(testSpeise.getPreis()).isEqualTo(UPDATED_PREIS);
        assertThat(testSpeise.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testSpeise.getKundenid()).isEqualTo(UPDATED_KUNDENID);
        assertThat(testSpeise.getSpeisekartenid()).isEqualTo(UPDATED_SPEISEKARTENID);
    }

    @Test
    public void updateNonExistingSpeise() throws Exception {
        int databaseSizeBeforeUpdate = speiseRepository.findAll().size();

        // Create the Speise

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeiseMockMvc.perform(put("/api/speises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speise)))
            .andExpect(status().isBadRequest());

        // Validate the Speise in the database
        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSpeise() throws Exception {
        // Initialize the database
        speiseService.save(speise);

        int databaseSizeBeforeDelete = speiseRepository.findAll().size();

        // Get the speise
        restSpeiseMockMvc.perform(delete("/api/speises/{id}", speise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Speise> speiseList = speiseRepository.findAll();
        assertThat(speiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speise.class);
        Speise speise1 = new Speise();
        speise1.setId("id1");
        Speise speise2 = new Speise();
        speise2.setId(speise1.getId());
        assertThat(speise1).isEqualTo(speise2);
        speise2.setId("id2");
        assertThat(speise1).isNotEqualTo(speise2);
        speise1.setId(null);
        assertThat(speise1).isNotEqualTo(speise2);
    }
}
