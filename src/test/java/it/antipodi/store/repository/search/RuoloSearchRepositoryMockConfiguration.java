package it.antipodi.store.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RuoloSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RuoloSearchRepositoryMockConfiguration {

    @MockBean
    private RuoloSearchRepository mockRuoloSearchRepository;

}
