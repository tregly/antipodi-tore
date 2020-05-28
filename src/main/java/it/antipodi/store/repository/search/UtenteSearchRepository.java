package it.antipodi.store.repository.search;

import it.antipodi.store.domain.Utente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Utente} entity.
 */
public interface UtenteSearchRepository extends ElasticsearchRepository<Utente, Long> {
}
