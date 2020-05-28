package it.antipodi.store.service.impl;

import it.antipodi.store.service.RuoloService;
import it.antipodi.store.domain.Ruolo;
import it.antipodi.store.repository.RuoloRepository;
import it.antipodi.store.repository.search.RuoloSearchRepository;
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
 * Service Implementation for managing {@link Ruolo}.
 */
@Service
@Transactional
public class RuoloServiceImpl implements RuoloService {

    private final Logger log = LoggerFactory.getLogger(RuoloServiceImpl.class);

    private final RuoloRepository ruoloRepository;

    private final RuoloSearchRepository ruoloSearchRepository;

    public RuoloServiceImpl(RuoloRepository ruoloRepository, RuoloSearchRepository ruoloSearchRepository) {
        this.ruoloRepository = ruoloRepository;
        this.ruoloSearchRepository = ruoloSearchRepository;
    }

    /**
     * Save a ruolo.
     *
     * @param ruolo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Ruolo save(Ruolo ruolo) {
        log.debug("Request to save Ruolo : {}", ruolo);
        Ruolo result = ruoloRepository.save(ruolo);
        ruoloSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ruolos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ruolo> findAll() {
        log.debug("Request to get all Ruolos");
        return ruoloRepository.findAll();
    }


    /**
     * Get one ruolo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Ruolo> findOne(Long id) {
        log.debug("Request to get Ruolo : {}", id);
        return ruoloRepository.findById(id);
    }

    /**
     * Delete the ruolo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ruolo : {}", id);

        ruoloRepository.deleteById(id);
        ruoloSearchRepository.deleteById(id);
    }

    /**
     * Search for the ruolo corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ruolo> search(String query) {
        log.debug("Request to search Ruolos for query {}", query);
        return StreamSupport
            .stream(ruoloSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
