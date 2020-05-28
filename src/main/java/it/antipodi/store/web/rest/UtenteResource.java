package it.antipodi.store.web.rest;

import it.antipodi.store.domain.Utente;
import it.antipodi.store.service.UtenteService;
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
 * REST controller for managing {@link it.antipodi.store.domain.Utente}.
 */
@RestController
@RequestMapping("/api")
public class UtenteResource {

    private final Logger log = LoggerFactory.getLogger(UtenteResource.class);

    private static final String ENTITY_NAME = "utente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UtenteService utenteService;

    public UtenteResource(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    /**
     * {@code POST  /utentes} : Create a new utente.
     *
     * @param utente the utente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new utente, or with status {@code 400 (Bad Request)} if the utente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/utentes")
    public ResponseEntity<Utente> createUtente(@RequestBody Utente utente) throws URISyntaxException {
        log.debug("REST request to save Utente : {}", utente);
        if (utente.getId() != null) {
            throw new BadRequestAlertException("A new utente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Utente result = utenteService.save(utente);
        return ResponseEntity.created(new URI("/api/utentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /utentes} : Updates an existing utente.
     *
     * @param utente the utente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utente,
     * or with status {@code 400 (Bad Request)} if the utente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the utente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/utentes")
    public ResponseEntity<Utente> updateUtente(@RequestBody Utente utente) throws URISyntaxException {
        log.debug("REST request to update Utente : {}", utente);
        if (utente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Utente result = utenteService.save(utente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, utente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /utentes} : get all the utentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of utentes in body.
     */
    @GetMapping("/utentes")
    public List<Utente> getAllUtentes() {
        log.debug("REST request to get all Utentes");
        return utenteService.findAll();
    }

    /**
     * {@code GET  /utentes/:id} : get the "id" utente.
     *
     * @param id the id of the utente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the utente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/utentes/{id}")
    public ResponseEntity<Utente> getUtente(@PathVariable Long id) {
        log.debug("REST request to get Utente : {}", id);
        Optional<Utente> utente = utenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(utente);
    }

    /**
     * {@code DELETE  /utentes/:id} : delete the "id" utente.
     *
     * @param id the id of the utente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/utentes/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
        log.debug("REST request to delete Utente : {}", id);

        utenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/utentes?query=:query} : search for the utente corresponding
     * to the query.
     *
     * @param query the query of the utente search.
     * @return the result of the search.
     */
    @GetMapping("/_search/utentes")
    public List<Utente> searchUtentes(@RequestParam String query) {
        log.debug("REST request to search Utentes for query {}", query);
        return utenteService.search(query);
    }
}
