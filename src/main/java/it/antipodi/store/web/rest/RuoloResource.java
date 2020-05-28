package it.antipodi.store.web.rest;

import it.antipodi.store.domain.Ruolo;
import it.antipodi.store.service.RuoloService;
import it.antipodi.store.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link it.antipodi.store.domain.Ruolo}.
 */
@RestController
@RequestMapping("/api")
public class RuoloResource {

    private final Logger log = LoggerFactory.getLogger(RuoloResource.class);

    private static final String ENTITY_NAME = "ruolo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuoloService ruoloService;

    public RuoloResource(RuoloService ruoloService) {
        this.ruoloService = ruoloService;
    }

    /**
     * {@code POST  /ruolos} : Create a new ruolo.
     *
     * @param ruolo the ruolo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruolo, or with status {@code 400 (Bad Request)} if the ruolo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ruolos")
    public ResponseEntity<Ruolo> createRuolo(@RequestBody Ruolo ruolo) throws URISyntaxException {
        log.debug("REST request to save Ruolo : {}", ruolo);
        if (ruolo.getId() != null) {
            throw new BadRequestAlertException("A new ruolo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ruolo result = ruoloService.save(ruolo);
        return ResponseEntity.created(new URI("/api/ruolos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ruolos} : Updates an existing ruolo.
     *
     * @param ruolo the ruolo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruolo,
     * or with status {@code 400 (Bad Request)} if the ruolo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruolo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ruolos")
    public ResponseEntity<Ruolo> updateRuolo(@RequestBody Ruolo ruolo) throws URISyntaxException {
        log.debug("REST request to update Ruolo : {}", ruolo);
        if (ruolo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ruolo result = ruoloService.save(ruolo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ruolo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ruolos} : get all the ruolos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruolos in body.
     */
    @GetMapping("/ruolos")
    public List<Ruolo> getAllRuolos() {
        log.debug("REST request to get all Ruolos");
        return ruoloService.findAll();
    }

    /**
     * {@code GET  /ruolos/:id} : get the "id" ruolo.
     *
     * @param id the id of the ruolo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruolo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ruolos/{id}")
    public ResponseEntity<Ruolo> getRuolo(@PathVariable Long id) {
        log.debug("REST request to get Ruolo : {}", id);
        Optional<Ruolo> ruolo = ruoloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruolo);
    }

    /**
     * {@code DELETE  /ruolos/:id} : delete the "id" ruolo.
     *
     * @param id the id of the ruolo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ruolos/{id}")
    public ResponseEntity<Void> deleteRuolo(@PathVariable Long id) {
        log.debug("REST request to delete Ruolo : {}", id);

        ruoloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ruolos?query=:query} : search for the ruolo corresponding
     * to the query.
     *
     * @param query the query of the ruolo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/ruolos")
    public List<Ruolo> searchRuolos(@RequestParam String query) {
        log.debug("REST request to search Ruolos for query {}", query);
        return ruoloService.search(query);
    }
}
