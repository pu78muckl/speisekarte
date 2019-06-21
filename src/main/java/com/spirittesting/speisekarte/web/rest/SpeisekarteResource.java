package com.spirittesting.speisekarte.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spirittesting.speisekarte.domain.Speisekarte;
import com.spirittesting.speisekarte.service.SpeisekarteService;
import com.spirittesting.speisekarte.web.rest.errors.BadRequestAlertException;
import com.spirittesting.speisekarte.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Speisekarte.
 */
@RestController
@RequestMapping("/api")
public class SpeisekarteResource {

    private final Logger log = LoggerFactory.getLogger(SpeisekarteResource.class);

    private static final String ENTITY_NAME = "speisekarte";

    private final SpeisekarteService speisekarteService;

    public SpeisekarteResource(SpeisekarteService speisekarteService) {
        this.speisekarteService = speisekarteService;
    }

    /**
     * POST  /speisekartes : Create a new speisekarte.
     *
     * @param speisekarte the speisekarte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new speisekarte, or with status 400 (Bad Request) if the speisekarte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/speisekartes")
    @Timed
    public ResponseEntity<Speisekarte> createSpeisekarte(@RequestBody Speisekarte speisekarte) throws URISyntaxException {
        log.debug("REST request to save Speisekarte : {}", speisekarte);
        if (speisekarte.getId() != null) {
            throw new BadRequestAlertException("A new speisekarte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Speisekarte result = speisekarteService.save(speisekarte);
        return ResponseEntity.created(new URI("/api/speisekartes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /speisekartes : Updates an existing speisekarte.
     *
     * @param speisekarte the speisekarte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated speisekarte,
     * or with status 400 (Bad Request) if the speisekarte is not valid,
     * or with status 500 (Internal Server Error) if the speisekarte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/speisekartes")
    @Timed
    public ResponseEntity<Speisekarte> updateSpeisekarte(@RequestBody Speisekarte speisekarte) throws URISyntaxException {
        log.debug("REST request to update Speisekarte : {}", speisekarte);
        if (speisekarte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Speisekarte result = speisekarteService.save(speisekarte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, speisekarte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /speisekartes : get all the speisekartes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of speisekartes in body
     */
    @GetMapping("/speisekartes")
    @Timed
    public List<Speisekarte> getAllSpeisekartes() {
        log.debug("REST request to get all Speisekartes");
        return speisekarteService.findAll();
    }

    /**
     * GET  /speisekartes/:id : get the "id" speisekarte.
     *
     * @param id the id of the speisekarte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the speisekarte, or with status 404 (Not Found)
     */
    @GetMapping("/speisekartes/{id}")
    @Timed
    public ResponseEntity<Speisekarte> getSpeisekarte(@PathVariable String id) {
        log.debug("REST request to get Speisekarte : {}", id);
        Optional<Speisekarte> speisekarte = speisekarteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speisekarte);
    }

    /**
     * DELETE  /speisekartes/:id : delete the "id" speisekarte.
     *
     * @param id the id of the speisekarte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/speisekartes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpeisekarte(@PathVariable String id) {
        log.debug("REST request to delete Speisekarte : {}", id);
        speisekarteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
