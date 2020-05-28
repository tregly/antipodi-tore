package it.antipodi.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.antipodi.store.web.rest.TestUtil;

public class RuoloTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ruolo.class);
        Ruolo ruolo1 = new Ruolo();
        ruolo1.setId(1L);
        Ruolo ruolo2 = new Ruolo();
        ruolo2.setId(ruolo1.getId());
        assertThat(ruolo1).isEqualTo(ruolo2);
        ruolo2.setId(2L);
        assertThat(ruolo1).isNotEqualTo(ruolo2);
        ruolo1.setId(null);
        assertThat(ruolo1).isNotEqualTo(ruolo2);
    }
}
