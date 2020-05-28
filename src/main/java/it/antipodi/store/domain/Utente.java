package it.antipodi.store.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Utente.
 */
@Entity
@Table(name = "utente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "utente")
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "modified")
    private LocalDate modified;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "google")
    private String google;

    @Column(name = "instangram")
    private String instangram;

    @Column(name = "provider")
    private String provider;

    @Column(name = "attivo")
    private Boolean attivo;

    @Column(name = "motivo_bolocco")
    private String motivoBolocco;

    @Column(name = "data_bolocco")
    private LocalDate dataBolocco;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "last_access")
    private LocalDate lastAccess;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruolo ruolo;

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

    public Utente created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public Utente modified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public String getUsername() {
        return username;
    }

    public Utente username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Utente password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public Utente mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public Utente mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFacebook() {
        return facebook;
    }

    public Utente facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public Utente google(String google) {
        this.google = google;
        return this;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getInstangram() {
        return instangram;
    }

    public Utente instangram(String instangram) {
        this.instangram = instangram;
        return this;
    }

    public void setInstangram(String instangram) {
        this.instangram = instangram;
    }

    public String getProvider() {
        return provider;
    }

    public Utente provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Boolean isAttivo() {
        return attivo;
    }

    public Utente attivo(Boolean attivo) {
        this.attivo = attivo;
        return this;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }

    public String getMotivoBolocco() {
        return motivoBolocco;
    }

    public Utente motivoBolocco(String motivoBolocco) {
        this.motivoBolocco = motivoBolocco;
        return this;
    }

    public void setMotivoBolocco(String motivoBolocco) {
        this.motivoBolocco = motivoBolocco;
    }

    public LocalDate getDataBolocco() {
        return dataBolocco;
    }

    public Utente dataBolocco(LocalDate dataBolocco) {
        this.dataBolocco = dataBolocco;
        return this;
    }

    public void setDataBolocco(LocalDate dataBolocco) {
        this.dataBolocco = dataBolocco;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Utente registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastAccess() {
        return lastAccess;
    }

    public Utente lastAccess(LocalDate lastAccess) {
        this.lastAccess = lastAccess;
        return this;
    }

    public void setLastAccess(LocalDate lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public Utente ruolo(Ruolo ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utente)) {
            return false;
        }
        return id != null && id.equals(((Utente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utente{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", mail='" + getMail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", google='" + getGoogle() + "'" +
            ", instangram='" + getInstangram() + "'" +
            ", provider='" + getProvider() + "'" +
            ", attivo='" + isAttivo() + "'" +
            ", motivoBolocco='" + getMotivoBolocco() + "'" +
            ", dataBolocco='" + getDataBolocco() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", lastAccess='" + getLastAccess() + "'" +
            "}";
    }
}
