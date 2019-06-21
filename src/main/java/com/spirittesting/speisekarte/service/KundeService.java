package com.spirittesting.speisekarte.service;

import com.spirittesting.speisekarte.domain.Kunde;
import com.spirittesting.speisekarte.repository.KundeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Kunde.
 */
@Service
public class KundeService {

    private final Logger log = LoggerFactory.getLogger(KundeService.class);

    private final KundeRepository kundeRepository;

    public KundeService(KundeRepository kundeRepository) {
        this.kundeRepository = kundeRepository;
    }

    /**
     * Save a kunde.
     *
     * @param kunde the entity to save
     * @return the persisted entity
     */
    public Kunde save(Kunde kunde) {
        log.debug("Request to save Kunde : {}", kunde);
        return kundeRepository.save(kunde);
    }

    /**
     * Get all the kundes.
     *
     * @return the list of entities
     */
    public List<Kunde> findAll() {
        log.debug("Request to get all Kundes");
        return kundeRepository.findAll();
    }


    /**
     * Get one kunde by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Kunde> findOne(String id) {
        log.debug("Request to get Kunde : {}", id);
        return kundeRepository.findById(id);
    }

    /**
     * Delete the kunde by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Kunde : {}", id);
        kundeRepository.deleteById(id);
    }
}
