package com.spirittesting.speisekarte.service;

import com.spirittesting.speisekarte.domain.Speise;
import com.spirittesting.speisekarte.repository.SpeiseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Speise.
 */
@Service
public class SpeiseService {

    private final Logger log = LoggerFactory.getLogger(SpeiseService.class);

    private final SpeiseRepository speiseRepository;

    public SpeiseService(SpeiseRepository speiseRepository) {
        this.speiseRepository = speiseRepository;
    }

    /**
     * Save a speise.
     *
     * @param speise the entity to save
     * @return the persisted entity
     */
    public Speise save(Speise speise) {
        log.debug("Request to save Speise : {}", speise);
        return speiseRepository.save(speise);
    }

    /**
     * Get all the speises.
     *
     * @return the list of entities
     */
    public List<Speise> findAll() {
        log.debug("Request to get all Speises");
        return speiseRepository.findAll();
    }



    /**
     *  get all the speises where Kunde is null.
     *  @return the list of entities
     */
    public List<Speise> findAllWhereKundeIsNull() {
        log.debug("Request to get all speises where Kunde is null");
        return StreamSupport
            .stream(speiseRepository.findAll().spliterator(), false)
            .filter(speise -> speise.getKunde() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one speise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Speise> findOne(String id) {
        log.debug("Request to get Speise : {}", id);
        return speiseRepository.findById(id);
    }

    /**
     * Delete the speise by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Speise : {}", id);
        speiseRepository.deleteById(id);
    }
}
