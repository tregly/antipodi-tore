package it.antipodi.store.service.impl;

import it.antipodi.store.service.UtenteService;
import it.antipodi.store.domain.Utente;
import it.antipodi.store.repository.UtenteRepository;
import it.antipodi.store.repository.search.UtenteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Utente}.
 */
@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

    private final Logger log = LoggerFactory.getLogger(UtenteServiceImpl.class);

    private final UtenteRepository utenteRepository;

    private final UtenteSearchRepository utenteSearchRepository;

    public UtenteServiceImpl(UtenteRepository utenteRepository, UtenteSearchRepository utenteSearchRepository) {
        this.utenteRepository = utenteRepository;
        this.utenteSearchRepository = utenteSearchRepository;
    }

    /**
     * Save a utente.
     *
     * @param utente the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Utente save(Utente utente) {
        log.debug("Request to save Utente : {}", utente);
        Utente result = utenteRepository.save(utente);
        utenteSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the utentes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Utente> findAll() {
        log.debug("Request to get all Utentes");
        return utenteRepository.findAll();
    }


    /**
     * Get one utente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Utente> findOne(Long id) {
        log.debug("Request to get Utente : {}", id);
        return utenteRepository.findById(id);
    }

    /**
     * Delete the utente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Utente : {}", id);

        utenteRepository.deleteById(id);
        utenteSearchRepository.deleteById(id);
    }

    /**
     * Search for the utente corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Utente> search(String query) {
        log.debug("Request to search Utentes for query {}", query);
        return StreamSupport
            .stream(utenteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
