package it.antipodi.store.web.rest;

import it.antipodi.store.domain.Azione;
import it.antipodi.store.service.AzioneService;
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
 * REST controller for managing {@link it.antipodi.store.domain.Azione}.
 */
@RestController
@RequestMapping("/api")
public class AzioneResource {

    private final Logger log = LoggerFactory.getLogger(AzioneResource.class);

    private static final String ENTITY_NAME = "azione";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AzioneService azioneService;

    public AzioneResource(AzioneService azioneService) {
        this.azioneService = azioneService;
    }

    /**
     * {@code POST  /aziones} : Create a new azione.
     *
     * @param azione the azione to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new azione, or with status {@code 400 (Bad Request)} if the azione has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aziones")
    public ResponseEntity<Azione> createAzione(@RequestBody Azione azione) throws URISyntaxException {
        log.debug("REST request to save Azione : {}", azione);
        if (azione.getId() != null) {
            throw new BadRequestAlertException("A new azione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Azione result = azioneService.save(azione);
        return ResponseEntity.created(new URI("/api/aziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aziones} : Updates an existing azione.
     *
     * @param azione the azione to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated azione,
     * or with status {@code 400 (Bad Request)} if the azione is not valid,
     * or with status {@code 500 (Internal Server Error)} if the azione couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aziones")
    public ResponseEntity<Azione> updateAzione(@RequestBody Azione azione) throws URISyntaxException {
        log.debug("REST request to update Azione : {}", azione);
        if (azione.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Azione result = azioneService.save(azione);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, azione.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aziones} : get all the aziones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aziones in body.
     */
    @GetMapping("/aziones")
    public List<Azione> getAllAziones() {
        log.debug("REST request to get all Aziones");
        return azioneService.findAll();
    }

    /**
     * {@code GET  /aziones/:id} : get the "id" azione.
     *
     * @param id the id of the azione to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the azione, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aziones/{id}")
    public ResponseEntity<Azione> getAzione(@PathVariable Long id) {
        log.debug("REST request to get Azione : {}", id);
        Optional<Azione> azione = azioneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(azione);
    }

    /**
     * {@code DELETE  /aziones/:id} : delete the "id" azione.
     *
     * @param id the id of the azione to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aziones/{id}")
    public ResponseEntity<Void> deleteAzione(@PathVariable Long id) {
        log.debug("REST request to delete Azione : {}", id);

        azioneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/aziones?query=:query} : search for the azione corresponding
     * to the query.
     *
     * @param query the query of the azione search.
     * @return the result of the search.
     */
    @GetMapping("/_search/aziones")
    public List<Azione> searchAziones(@RequestParam String query) {
        log.debug("REST request to search Aziones for query {}", query);
        return azioneService.search(query);
    }
}
