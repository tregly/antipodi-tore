package it.antipodi.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.antipodi.store.web.rest.TestUtil;

public class AzioneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Azione.class);
        Azione azione1 = new Azione();
        azione1.setId(1L);
        Azione azione2 = new Azione();
        azione2.setId(azione1.getId());
        assertThat(azione1).isEqualTo(azione2);
        azione2.setId(2L);
        assertThat(azione1).isNotEqualTo(azione2);
        azione1.setId(null);
        assertThat(azione1).isNotEqualTo(azione2);
    }
}
