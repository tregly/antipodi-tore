package it.antipodi.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ruolo.
 */
@Entity
@Table(name = "ruolo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ruolo")
public class Ruolo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "modified")
    private LocalDate modified;

    @Column(name = "nome_azione")
    private String nomeAzione;

    @ManyToOne
    @JsonIgnoreProperties(value = "ruolos", allowSetters = true)
    private Azione azioni;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Ruolo created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public Ruolo modified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public String getNomeAzione() {
        return nomeAzione;
    }

    public Ruolo nomeAzione(String nomeAzione) {
        this.nomeAzione = nomeAzione;
        return this;
    }

    public void setNomeAzione(String nomeAzione) {
        this.nomeAzione = nomeAzione;
    }

    public Azione getAzioni() {
        return azioni;
    }

    public Ruolo azioni(Azione azione) {
        this.azioni = azione;
        return this;
    }

    public void setAzioni(Azione azione) {
        this.azioni = azione;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ruolo)) {
            return false;
        }
        return id != null && id.equals(((Ruolo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ruolo{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", nomeAzione='" + getNomeAzione() + "'" +
            "}";
    }
}
