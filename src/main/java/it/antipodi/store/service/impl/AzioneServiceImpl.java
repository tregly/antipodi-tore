package it.antipodi.store.service.impl;

import it.antipodi.store.service.AzioneService;
import it.antipodi.store.domain.Azione;
import it.antipodi.store.repository.AzioneRepository;
import it.antipodi.store.repository.search.AzioneSearchRepository;
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
 * Service Implementation for managing {@link Azione}.
 */
@Service
@Transactional
public class AzioneServiceImpl implements AzioneService {

    private final Logger log = LoggerFactory.getLogger(AzioneServiceImpl.class);

    private final AzioneRepository azioneRepository;

    private final AzioneSearchRepository azioneSearchRepository;

    public AzioneServiceImpl(AzioneRepository azioneRepository, AzioneSearchRepository azioneSearchRepository) {
        this.azioneRepository = azioneRepository;
        this.azioneSearchRepository = azioneSearchRepository;
    }

    /**
     * Save a azione.
     *
     * @param azione the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Azione save(Azione azione) {
        log.debug("Request to save Azione : {}", azione);
        Azione result = azioneRepository.save(azione);
        azioneSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the aziones.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Azione> findAll() {
        log.debug("Request to get all Aziones");
        return azioneRepository.findAll();
    }


    /**
     * Get one azione by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Azione> findOne(Long id) {
        log.debug("Request to get Azione : {}", id);
        return azioneRepository.findById(id);
    }

    /**
     * Delete the azione by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Azione : {}", id);

        azioneRepository.deleteById(id);
        azioneSearchRepository.deleteById(id);
    }

    /**
     * Search for the azione corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Azione> search(String query) {
        log.debug("Request to search Aziones for query {}", query);
        return StreamSupport
            .stream(azioneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
