package it.antipodi.store.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Azione.
 */
@Entity
@Table(name = "azione")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "azione")
public class Azione implements Serializable {

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

    @Column(name = "descrizione")
    private String descrizione;

    @OneToMany(mappedBy = "azioni")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Ruolo> ruolos = new HashSet<>();

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

    public Azione created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public Azione modified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public String getNomeAzione() {
        return nomeAzione;
    }

    public Azione nomeAzione(String nomeAzione) {
        this.nomeAzione = nomeAzione;
        return this;
    }

    public void setNomeAzione(String nomeAzione) {
        this.nomeAzione = nomeAzione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Azione descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<Ruolo> getRuolos() {
        return ruolos;
    }

    public Azione ruolos(Set<Ruolo> ruolos) {
        this.ruolos = ruolos;
        return this;
    }

    public Azione addRuolo(Ruolo ruolo) {
        this.ruolos.add(ruolo);
        ruolo.setAzioni(this);
        return this;
    }

    public Azione removeRuolo(Ruolo ruolo) {
        this.ruolos.remove(ruolo);
        ruolo.setAzioni(null);
        return this;
    }

    public void setRuolos(Set<Ruolo> ruolos) {
        this.ruolos = ruolos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Azione)) {
            return false;
        }
        return id != null && id.equals(((Azione) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Azione{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", nomeAzione='" + getNomeAzione() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
