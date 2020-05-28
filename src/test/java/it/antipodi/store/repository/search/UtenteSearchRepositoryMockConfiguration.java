package it.antipodi.store.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link UtenteSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UtenteSearchRepositoryMockConfiguration {

    @MockBean
    private UtenteSearchRepository mockUtenteSearchRepository;

}
