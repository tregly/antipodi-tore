package it.antipodi.store.repository.search;

import it.antipodi.store.domain.Azione;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Azione} entity.
 */
public interface AzioneSearchRepository extends ElasticsearchRepository<Azione, Long> {
}
