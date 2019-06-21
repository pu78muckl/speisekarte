package com.spirittesting.speisekarte.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spirittesting.speisekarte.domain.Speise;
import com.spirittesting.speisekarte.service.SpeiseService;
import com.spirittesting.speisekarte.web.rest.errors.BadRequestAlertException;
import com.spirittesting.speisekarte.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Speise.
 */
@RestController
@RequestMapping("/api")
public class SpeiseResource {

    private final Logger log = LoggerFactory.getLogger(SpeiseResource.class);

    private static final String ENTITY_NAME = "speise";

    private final SpeiseService speiseService;

    public SpeiseResource(SpeiseService speiseService) {
        this.speiseService = speiseService;
    }

    /**
     * POST  /speises : Create a new speise.
     *
     * @param speise the speise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new speise, or with status 400 (Bad Request) if the speise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/speises")
    @Timed
    public ResponseEntity<Speise> createSpeise(@Valid @RequestBody Speise speise) throws URISyntaxException {
        log.debug("REST request to save Speise : {}", speise);
        if (speise.getId() != null) {
            throw new BadRequestAlertException("A new speise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Speise result = speiseService.save(speise);
        return ResponseEntity.created(new URI("/api/speises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /speises : Updates an existing speise.
     *
     * @param speise the speise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated speise,
     * or with status 400 (Bad Request) if the speise is not valid,
     * or with status 500 (Internal Server Error) if the speise couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/speises")
    @Timed
    public ResponseEntity<Speise> updateSpeise(@Valid @RequestBody Speise speise) throws URISyntaxException {
        log.debug("REST request to update Speise : {}", speise);
        if (speise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Speise result = speiseService.save(speise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, speise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /speises : get all the speises.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of speises in body
     */
    @GetMapping("/speises")
    @Timed
    public List<Speise> getAllSpeises(@RequestParam(required = false) String filter) {
        if ("kunde-is-null".equals(filter)) {
            log.debug("REST request to get all Speises where kunde is null");
            return speiseService.findAllWhereKundeIsNull();
        }
        log.debug("REST request to get all Speises");
        return speiseService.findAll();
    }

    /**
     * GET  /speises/:id : get the "id" speise.
     *
     * @param id the id of the speise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the speise, or with status 404 (Not Found)
     */
    @GetMapping("/speises/{id}")
    @Timed
    public ResponseEntity<Speise> getSpeise(@PathVariable String id) {
        log.debug("REST request to get Speise : {}", id);
        Optional<Speise> speise = speiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speise);
    }

    /**
     * DELETE  /speises/:id : delete the "id" speise.
     *
     * @param id the id of the speise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/speises/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpeise(@PathVariable String id) {
        log.debug("REST request to delete Speise : {}", id);
        speiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
