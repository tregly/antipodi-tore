package it.antipodi.store.repository.search;

import it.antipodi.store.domain.Ruolo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Ruolo} entity.
 */
public interface RuoloSearchRepository extends ElasticsearchRepository<Ruolo, Long> {
}
