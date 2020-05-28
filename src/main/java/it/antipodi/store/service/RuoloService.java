package it.antipodi.store.service;

import it.antipodi.store.domain.Ruolo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Ruolo}.
 */
public interface RuoloService {

    /**
     * Save a ruolo.
     *
     * @param ruolo the entity to save.
     * @return the persisted entity.
     */
    Ruolo save(Ruolo ruolo);

    /**
     * Get all the ruolos.
     *
     * @return the list of entities.
     */
    List<Ruolo> findAll();


    /**
     * Get the "id" ruolo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ruolo> findOne(Long id);

    /**
     * Delete the "id" ruolo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the ruolo corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Ruolo> search(String query);
}
