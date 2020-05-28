package it.antipodi.store.service;

import it.antipodi.store.domain.Utente;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Utente}.
 */
public interface UtenteService {

    /**
     * Save a utente.
     *
     * @param utente the entity to save.
     * @return the persisted entity.
     */
    Utente save(Utente utente);

    /**
     * Get all the utentes.
     *
     * @return the list of entities.
     */
    List<Utente> findAll();


    /**
     * Get the "id" utente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Utente> findOne(Long id);

    /**
     * Delete the "id" utente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the utente corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Utente> search(String query);
}
