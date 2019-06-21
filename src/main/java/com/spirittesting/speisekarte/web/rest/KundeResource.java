package com.spirittesting.speisekarte.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spirittesting.speisekarte.domain.Kunde;
import com.spirittesting.speisekarte.service.KundeService;
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

/**
 * REST controller for managing Kunde.
 */
@RestController
@RequestMapping("/api")
public class KundeResource {

    private final Logger log = LoggerFactory.getLogger(KundeResource.class);

    private static final String ENTITY_NAME = "kunde";

    private final KundeService kundeService;

    public KundeResource(KundeService kundeService) {
        this.kundeService = kundeService;
    }

    /**
     * POST  /kundes : Create a new kunde.
     *
     * @param kunde the kunde to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kunde, or with status 400 (Bad Request) if the kunde has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kundes")
    @Timed
    public ResponseEntity<Kunde> createKunde(@Valid @RequestBody Kunde kunde) throws URISyntaxException {
        log.debug("REST request to save Kunde : {}", kunde);
        if (kunde.getId() != null) {
            throw new BadRequestAlertException("A new kunde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kunde result = kundeService.save(kunde);
        return ResponseEntity.created(new URI("/api/kundes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kundes : Updates an existing kunde.
     *
     * @param kunde the kunde to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kunde,
     * or with status 400 (Bad Request) if the kunde is not valid,
     * or with status 500 (Internal Server Error) if the kunde couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kundes")
    @Timed
    public ResponseEntity<Kunde> updateKunde(@Valid @RequestBody Kunde kunde) throws URISyntaxException {
        log.debug("REST request to update Kunde : {}", kunde);
        if (kunde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kunde result = kundeService.save(kunde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kunde.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kundes : get all the kundes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kundes in body
     */
    @GetMapping("/kundes")
    @Timed
    public List<Kunde> getAllKundes() {
        log.debug("REST request to get all Kundes");
        return kundeService.findAll();
    }

    /**
     * GET  /kundes/:id : get the "id" kunde.
     *
     * @param id the id of the kunde to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kunde, or with status 404 (Not Found)
     */
    @GetMapping("/kundes/{id}")
    @Timed
    public ResponseEntity<Kunde> getKunde(@PathVariable String id) {
        log.debug("REST request to get Kunde : {}", id);
        Optional<Kunde> kunde = kundeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kunde);
    }

    /**
     * DELETE  /kundes/:id : delete the "id" kunde.
     *
     * @param id the id of the kunde to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kundes/{id}")
    @Timed
    public ResponseEntity<Void> deleteKunde(@PathVariable String id) {
        log.debug("REST request to delete Kunde : {}", id);
        kundeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
