package it.antipodi.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.antipodi.store.web.rest.TestUtil;

public class UtenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utente.class);
        Utente utente1 = new Utente();
        utente1.setId(1L);
        Utente utente2 = new Utente();
        utente2.setId(utente1.getId());
        assertThat(utente1).isEqualTo(utente2);
        utente2.setId(2L);
        assertThat(utente1).isNotEqualTo(utente2);
        utente1.setId(null);
        assertThat(utente1).isNotEqualTo(utente2);
    }
}
