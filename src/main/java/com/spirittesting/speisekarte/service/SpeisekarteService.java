package com.spirittesting.speisekarte.service;

import com.spirittesting.speisekarte.domain.Speisekarte;
import com.spirittesting.speisekarte.repository.SpeisekarteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Speisekarte.
 */
@Service
public class SpeisekarteService {

    private final Logger log = LoggerFactory.getLogger(SpeisekarteService.class);

    private final SpeisekarteRepository speisekarteRepository;

    public SpeisekarteService(SpeisekarteRepository speisekarteRepository) {
        this.speisekarteRepository = speisekarteRepository;
    }

    /**
     * Save a speisekarte.
     *
     * @param speisekarte the entity to save
     * @return the persisted entity
     */
    public Speisekarte save(Speisekarte speisekarte) {
        log.debug("Request to save Speisekarte : {}", speisekarte);
        return speisekarteRepository.save(speisekarte);
    }

    /**
     * Get all the speisekartes.
     *
     * @return the list of entities
     */
    public List<Speisekarte> findAll() {
        log.debug("Request to get all Speisekartes");
        return speisekarteRepository.findAll();
    }


    /**
     * Get one speisekarte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Speisekarte> findOne(String id) {
        log.debug("Request to get Speisekarte : {}", id);
        return speisekarteRepository.findById(id);
    }

    /**
     * Delete the speisekarte by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Speisekarte : {}", id);
        speisekarteRepository.deleteById(id);
    }
}
