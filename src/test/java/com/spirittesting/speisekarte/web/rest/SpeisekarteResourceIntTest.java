package com.spirittesting.speisekarte.web.rest;

import com.spirittesting.speisekarte.SpeisekarteApp;

import com.spirittesting.speisekarte.domain.Speisekarte;
import com.spirittesting.speisekarte.repository.SpeisekarteRepository;
import com.spirittesting.speisekarte.service.SpeisekarteService;
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
 * Test class for the SpeisekarteResource REST controller.
 *
 * @see SpeisekarteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpeisekarteApp.class)
public class SpeisekarteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KUNDE = "AAAAAAAAAA";
    private static final String UPDATED_KUNDE = "BBBBBBBBBB";

    @Autowired
    private SpeisekarteRepository speisekarteRepository;

    @Autowired
    private SpeisekarteService speisekarteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSpeisekarteMockMvc;

    private Speisekarte speisekarte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpeisekarteResource speisekarteResource = new SpeisekarteResource(speisekarteService);
        this.restSpeisekarteMockMvc = MockMvcBuilders.standaloneSetup(speisekarteResource)
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
    public static Speisekarte createEntity() {
        Speisekarte speisekarte = new Speisekarte()
            .name(DEFAULT_NAME)
            .kunde(DEFAULT_KUNDE);
        return speisekarte;
    }

    @Before
    public void initTest() {
        speisekarteRepository.deleteAll();
        speisekarte = createEntity();
    }

    @Test
    public void createSpeisekarte() throws Exception {
        int databaseSizeBeforeCreate = speisekarteRepository.findAll().size();

        // Create the Speisekarte
        restSpeisekarteMockMvc.perform(post("/api/speisekartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speisekarte)))
            .andExpect(status().isCreated());

        // Validate the Speisekarte in the database
        List<Speisekarte> speisekarteList = speisekarteRepository.findAll();
        assertThat(speisekarteList).hasSize(databaseSizeBeforeCreate + 1);
        Speisekarte testSpeisekarte = speisekarteList.get(speisekarteList.size() - 1);
        assertThat(testSpeisekarte.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpeisekarte.getKunde()).isEqualTo(DEFAULT_KUNDE);
    }

    @Test
    public void createSpeisekarteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speisekarteRepository.findAll().size();

        // Create the Speisekarte with an existing ID
        speisekarte.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeisekarteMockMvc.perform(post("/api/speisekartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speisekarte)))
            .andExpect(status().isBadRequest());

        // Validate the Speisekarte in the database
        List<Speisekarte> speisekarteList = speisekarteRepository.findAll();
        assertThat(speisekarteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSpeisekartes() throws Exception {
        // Initialize the database
        speisekarteRepository.save(speisekarte);

        // Get all the speisekarteList
        restSpeisekarteMockMvc.perform(get("/api/speisekartes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speisekarte.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].kunde").value(hasItem(DEFAULT_KUNDE.toString())));
    }
    
    @Test
    public void getSpeisekarte() throws Exception {
        // Initialize the database
        speisekarteRepository.save(speisekarte);

        // Get the speisekarte
        restSpeisekarteMockMvc.perform(get("/api/speisekartes/{id}", speisekarte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speisekarte.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.kunde").value(DEFAULT_KUNDE.toString()));
    }

    @Test
    public void getNonExistingSpeisekarte() throws Exception {
        // Get the speisekarte
        restSpeisekarteMockMvc.perform(get("/api/speisekartes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpeisekarte() throws Exception {
        // Initialize the database
        speisekarteService.save(speisekarte);

        int databaseSizeBeforeUpdate = speisekarteRepository.findAll().size();

        // Update the speisekarte
        Speisekarte updatedSpeisekarte = speisekarteRepository.findById(speisekarte.getId()).get();
        updatedSpeisekarte
            .name(UPDATED_NAME)
            .kunde(UPDATED_KUNDE);

        restSpeisekarteMockMvc.perform(put("/api/speisekartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeisekarte)))
            .andExpect(status().isOk());

        // Validate the Speisekarte in the database
        List<Speisekarte> speisekarteList = speisekarteRepository.findAll();
        assertThat(speisekarteList).hasSize(databaseSizeBeforeUpdate);
        Speisekarte testSpeisekarte = speisekarteList.get(speisekarteList.size() - 1);
        assertThat(testSpeisekarte.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpeisekarte.getKunde()).isEqualTo(UPDATED_KUNDE);
    }

    @Test
    public void updateNonExistingSpeisekarte() throws Exception {
        int databaseSizeBeforeUpdate = speisekarteRepository.findAll().size();

        // Create the Speisekarte

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeisekarteMockMvc.perform(put("/api/speisekartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speisekarte)))
            .andExpect(status().isBadRequest());

        // Validate the Speisekarte in the database
        List<Speisekarte> speisekarteList = speisekarteRepository.findAll();
        assertThat(speisekarteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSpeisekarte() throws Exception {
        // Initialize the database
        speisekarteService.save(speisekarte);

        int databaseSizeBeforeDelete = speisekarteRepository.findAll().size();

        // Get the speisekarte
        restSpeisekarteMockMvc.perform(delete("/api/speisekartes/{id}", speisekarte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Speisekarte> speisekarteList = speisekarteRepository.findAll();
        assertThat(speisekarteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speisekarte.class);
        Speisekarte speisekarte1 = new Speisekarte();
        speisekarte1.setId("id1");
        Speisekarte speisekarte2 = new Speisekarte();
        speisekarte2.setId(speisekarte1.getId());
        assertThat(speisekarte1).isEqualTo(speisekarte2);
        speisekarte2.setId("id2");
        assertThat(speisekarte1).isNotEqualTo(speisekarte2);
        speisekarte1.setId(null);
        assertThat(speisekarte1).isNotEqualTo(speisekarte2);
    }
}
