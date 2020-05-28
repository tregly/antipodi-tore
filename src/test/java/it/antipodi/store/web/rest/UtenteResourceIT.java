package it.antipodi.store.web.rest;

import it.antipodi.store.AntipodiStoreApp;
import it.antipodi.store.domain.Utente;
import it.antipodi.store.repository.UtenteRepository;
import it.antipodi.store.repository.search.UtenteSearchRepository;
import it.antipodi.store.service.UtenteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UtenteResource} REST controller.
 */
@SpringBootTest(classes = AntipodiStoreApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UtenteResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTANGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTANGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATTIVO = false;
    private static final Boolean UPDATED_ATTIVO = true;

    private static final String DEFAULT_MOTIVO_BOLOCCO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_BOLOCCO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_BOLOCCO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_BOLOCCO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_ACCESS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_ACCESS = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteService utenteService;

    /**
     * This repository is mocked in the it.antipodi.store.repository.search test package.
     *
     * @see it.antipodi.store.repository.search.UtenteSearchRepositoryMockConfiguration
     */
    @Autowired
    private UtenteSearchRepository mockUtenteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUtenteMockMvc;

    private Utente utente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utente createEntity(EntityManager em) {
        Utente utente = new Utente()
            .created(DEFAULT_CREATED)
            .modified(DEFAULT_MODIFIED)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .mail(DEFAULT_MAIL)
            .mobile(DEFAULT_MOBILE)
            .facebook(DEFAULT_FACEBOOK)
            .google(DEFAULT_GOOGLE)
            .instangram(DEFAULT_INSTANGRAM)
            .provider(DEFAULT_PROVIDER)
            .attivo(DEFAULT_ATTIVO)
            .motivoBolocco(DEFAULT_MOTIVO_BOLOCCO)
            .dataBolocco(DEFAULT_DATA_BOLOCCO)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .lastAccess(DEFAULT_LAST_ACCESS);
        return utente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utente createUpdatedEntity(EntityManager em) {
        Utente utente = new Utente()
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .mail(UPDATED_MAIL)
            .mobile(UPDATED_MOBILE)
            .facebook(UPDATED_FACEBOOK)
            .google(UPDATED_GOOGLE)
            .instangram(UPDATED_INSTANGRAM)
            .provider(UPDATED_PROVIDER)
            .attivo(UPDATED_ATTIVO)
            .motivoBolocco(UPDATED_MOTIVO_BOLOCCO)
            .dataBolocco(UPDATED_DATA_BOLOCCO)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .lastAccess(UPDATED_LAST_ACCESS);
        return utente;
    }

    @BeforeEach
    public void initTest() {
        utente = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtente() throws Exception {
        int databaseSizeBeforeCreate = utenteRepository.findAll().size();
        // Create the Utente
        restUtenteMockMvc.perform(post("/api/utentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utente)))
            .andExpect(status().isCreated());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeCreate + 1);
        Utente testUtente = utenteList.get(utenteList.size() - 1);
        assertThat(testUtente.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testUtente.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testUtente.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUtente.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUtente.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testUtente.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUtente.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testUtente.getGoogle()).isEqualTo(DEFAULT_GOOGLE);
        assertThat(testUtente.getInstangram()).isEqualTo(DEFAULT_INSTANGRAM);
        assertThat(testUtente.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testUtente.isAttivo()).isEqualTo(DEFAULT_ATTIVO);
        assertThat(testUtente.getMotivoBolocco()).isEqualTo(DEFAULT_MOTIVO_BOLOCCO);
        assertThat(testUtente.getDataBolocco()).isEqualTo(DEFAULT_DATA_BOLOCCO);
        assertThat(testUtente.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testUtente.getLastAccess()).isEqualTo(DEFAULT_LAST_ACCESS);

        // Validate the Utente in Elasticsearch
        verify(mockUtenteSearchRepository, times(1)).save(testUtente);
    }

    @Test
    @Transactional
    public void createUtenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utenteRepository.findAll().size();

        // Create the Utente with an existing ID
        utente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtenteMockMvc.perform(post("/api/utentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utente)))
            .andExpect(status().isBadRequest());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Utente in Elasticsearch
        verify(mockUtenteSearchRepository, times(0)).save(utente);
    }


    @Test
    @Transactional
    public void getAllUtentes() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get all the utenteList
        restUtenteMockMvc.perform(get("/api/utentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utente.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].google").value(hasItem(DEFAULT_GOOGLE)))
            .andExpect(jsonPath("$.[*].instangram").value(hasItem(DEFAULT_INSTANGRAM)))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].attivo").value(hasItem(DEFAULT_ATTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].motivoBolocco").value(hasItem(DEFAULT_MOTIVO_BOLOCCO)))
            .andExpect(jsonPath("$.[*].dataBolocco").value(hasItem(DEFAULT_DATA_BOLOCCO.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastAccess").value(hasItem(DEFAULT_LAST_ACCESS.toString())));
    }
    
    @Test
    @Transactional
    public void getUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get the utente
        restUtenteMockMvc.perform(get("/api/utentes/{id}", utente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(utente.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.google").value(DEFAULT_GOOGLE))
            .andExpect(jsonPath("$.instangram").value(DEFAULT_INSTANGRAM))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER))
            .andExpect(jsonPath("$.attivo").value(DEFAULT_ATTIVO.booleanValue()))
            .andExpect(jsonPath("$.motivoBolocco").value(DEFAULT_MOTIVO_BOLOCCO))
            .andExpect(jsonPath("$.dataBolocco").value(DEFAULT_DATA_BOLOCCO.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.lastAccess").value(DEFAULT_LAST_ACCESS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUtente() throws Exception {
        // Get the utente
        restUtenteMockMvc.perform(get("/api/utentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtente() throws Exception {
        // Initialize the database
        utenteService.save(utente);

        int databaseSizeBeforeUpdate = utenteRepository.findAll().size();

        // Update the utente
        Utente updatedUtente = utenteRepository.findById(utente.getId()).get();
        // Disconnect from session so that the updates on updatedUtente are not directly saved in db
        em.detach(updatedUtente);
        updatedUtente
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .mail(UPDATED_MAIL)
            .mobile(UPDATED_MOBILE)
            .facebook(UPDATED_FACEBOOK)
            .google(UPDATED_GOOGLE)
            .instangram(UPDATED_INSTANGRAM)
            .provider(UPDATED_PROVIDER)
            .attivo(UPDATED_ATTIVO)
            .motivoBolocco(UPDATED_MOTIVO_BOLOCCO)
            .dataBolocco(UPDATED_DATA_BOLOCCO)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .lastAccess(UPDATED_LAST_ACCESS);

        restUtenteMockMvc.perform(put("/api/utentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUtente)))
            .andExpect(status().isOk());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeUpdate);
        Utente testUtente = utenteList.get(utenteList.size() - 1);
        assertThat(testUtente.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testUtente.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testUtente.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUtente.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUtente.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testUtente.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUtente.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testUtente.getGoogle()).isEqualTo(UPDATED_GOOGLE);
        assertThat(testUtente.getInstangram()).isEqualTo(UPDATED_INSTANGRAM);
        assertThat(testUtente.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testUtente.isAttivo()).isEqualTo(UPDATED_ATTIVO);
        assertThat(testUtente.getMotivoBolocco()).isEqualTo(UPDATED_MOTIVO_BOLOCCO);
        assertThat(testUtente.getDataBolocco()).isEqualTo(UPDATED_DATA_BOLOCCO);
        assertThat(testUtente.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testUtente.getLastAccess()).isEqualTo(UPDATED_LAST_ACCESS);

        // Validate the Utente in Elasticsearch
        verify(mockUtenteSearchRepository, times(2)).save(testUtente);
    }

    @Test
    @Transactional
    public void updateNonExistingUtente() throws Exception {
        int databaseSizeBeforeUpdate = utenteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtenteMockMvc.perform(put("/api/utentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(utente)))
            .andExpect(status().isBadRequest());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Utente in Elasticsearch
        verify(mockUtenteSearchRepository, times(0)).save(utente);
    }

    @Test
    @Transactional
    public void deleteUtente() throws Exception {
        // Initialize the database
        utenteService.save(utente);

        int databaseSizeBeforeDelete = utenteRepository.findAll().size();

        // Delete the utente
        restUtenteMockMvc.perform(delete("/api/utentes/{id}", utente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Utente in Elasticsearch
        verify(mockUtenteSearchRepository, times(1)).deleteById(utente.getId());
    }

    @Test
    @Transactional
    public void searchUtente() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        utenteService.save(utente);
        when(mockUtenteSearchRepository.search(queryStringQuery("id:" + utente.getId())))
            .thenReturn(Collections.singletonList(utente));

        // Search the utente
        restUtenteMockMvc.perform(get("/api/_search/utentes?query=id:" + utente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utente.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].google").value(hasItem(DEFAULT_GOOGLE)))
            .andExpect(jsonPath("$.[*].instangram").value(hasItem(DEFAULT_INSTANGRAM)))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].attivo").value(hasItem(DEFAULT_ATTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].motivoBolocco").value(hasItem(DEFAULT_MOTIVO_BOLOCCO)))
            .andExpect(jsonPath("$.[*].dataBolocco").value(hasItem(DEFAULT_DATA_BOLOCCO.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastAccess").value(hasItem(DEFAULT_LAST_ACCESS.toString())));
    }
}
