package it.antipodi.store.web.rest;

import it.antipodi.store.AntipodiStoreApp;
import it.antipodi.store.domain.Azione;
import it.antipodi.store.repository.AzioneRepository;
import it.antipodi.store.repository.search.AzioneSearchRepository;
import it.antipodi.store.service.AzioneService;

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
 * Integration tests for the {@link AzioneResource} REST controller.
 */
@SpringBootTest(classes = AntipodiStoreApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AzioneResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOME_AZIONE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_AZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private AzioneRepository azioneRepository;

    @Autowired
    private AzioneService azioneService;

    /**
     * This repository is mocked in the it.antipodi.store.repository.search test package.
     *
     * @see it.antipodi.store.repository.search.AzioneSearchRepositoryMockConfiguration
     */
    @Autowired
    private AzioneSearchRepository mockAzioneSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAzioneMockMvc;

    private Azione azione;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Azione createEntity(EntityManager em) {
        Azione azione = new Azione()
            .created(DEFAULT_CREATED)
            .modified(DEFAULT_MODIFIED)
            .nomeAzione(DEFAULT_NOME_AZIONE)
            .descrizione(DEFAULT_DESCRIZIONE);
        return azione;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Azione createUpdatedEntity(EntityManager em) {
        Azione azione = new Azione()
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .nomeAzione(UPDATED_NOME_AZIONE)
            .descrizione(UPDATED_DESCRIZIONE);
        return azione;
    }

    @BeforeEach
    public void initTest() {
        azione = createEntity(em);
    }

    @Test
    @Transactional
    public void createAzione() throws Exception {
        int databaseSizeBeforeCreate = azioneRepository.findAll().size();
        // Create the Azione
        restAzioneMockMvc.perform(post("/api/aziones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(azione)))
            .andExpect(status().isCreated());

        // Validate the Azione in the database
        List<Azione> azioneList = azioneRepository.findAll();
        assertThat(azioneList).hasSize(databaseSizeBeforeCreate + 1);
        Azione testAzione = azioneList.get(azioneList.size() - 1);
        assertThat(testAzione.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAzione.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testAzione.getNomeAzione()).isEqualTo(DEFAULT_NOME_AZIONE);
        assertThat(testAzione.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);

        // Validate the Azione in Elasticsearch
        verify(mockAzioneSearchRepository, times(1)).save(testAzione);
    }

    @Test
    @Transactional
    public void createAzioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = azioneRepository.findAll().size();

        // Create the Azione with an existing ID
        azione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAzioneMockMvc.perform(post("/api/aziones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(azione)))
            .andExpect(status().isBadRequest());

        // Validate the Azione in the database
        List<Azione> azioneList = azioneRepository.findAll();
        assertThat(azioneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Azione in Elasticsearch
        verify(mockAzioneSearchRepository, times(0)).save(azione);
    }


    @Test
    @Transactional
    public void getAllAziones() throws Exception {
        // Initialize the database
        azioneRepository.saveAndFlush(azione);

        // Get all the azioneList
        restAzioneMockMvc.perform(get("/api/aziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(azione.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].nomeAzione").value(hasItem(DEFAULT_NOME_AZIONE)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));
    }
    
    @Test
    @Transactional
    public void getAzione() throws Exception {
        // Initialize the database
        azioneRepository.saveAndFlush(azione);

        // Get the azione
        restAzioneMockMvc.perform(get("/api/aziones/{id}", azione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(azione.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.toString()))
            .andExpect(jsonPath("$.nomeAzione").value(DEFAULT_NOME_AZIONE))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE));
    }
    @Test
    @Transactional
    public void getNonExistingAzione() throws Exception {
        // Get the azione
        restAzioneMockMvc.perform(get("/api/aziones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAzione() throws Exception {
        // Initialize the database
        azioneService.save(azione);

        int databaseSizeBeforeUpdate = azioneRepository.findAll().size();

        // Update the azione
        Azione updatedAzione = azioneRepository.findById(azione.getId()).get();
        // Disconnect from session so that the updates on updatedAzione are not directly saved in db
        em.detach(updatedAzione);
        updatedAzione
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .nomeAzione(UPDATED_NOME_AZIONE)
            .descrizione(UPDATED_DESCRIZIONE);

        restAzioneMockMvc.perform(put("/api/aziones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAzione)))
            .andExpect(status().isOk());

        // Validate the Azione in the database
        List<Azione> azioneList = azioneRepository.findAll();
        assertThat(azioneList).hasSize(databaseSizeBeforeUpdate);
        Azione testAzione = azioneList.get(azioneList.size() - 1);
        assertThat(testAzione.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAzione.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testAzione.getNomeAzione()).isEqualTo(UPDATED_NOME_AZIONE);
        assertThat(testAzione.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);

        // Validate the Azione in Elasticsearch
        verify(mockAzioneSearchRepository, times(2)).save(testAzione);
    }

    @Test
    @Transactional
    public void updateNonExistingAzione() throws Exception {
        int databaseSizeBeforeUpdate = azioneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAzioneMockMvc.perform(put("/api/aziones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(azione)))
            .andExpect(status().isBadRequest());

        // Validate the Azione in the database
        List<Azione> azioneList = azioneRepository.findAll();
        assertThat(azioneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Azione in Elasticsearch
        verify(mockAzioneSearchRepository, times(0)).save(azione);
    }

    @Test
    @Transactional
    public void deleteAzione() throws Exception {
        // Initialize the database
        azioneService.save(azione);

        int databaseSizeBeforeDelete = azioneRepository.findAll().size();

        // Delete the azione
        restAzioneMockMvc.perform(delete("/api/aziones/{id}", azione.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Azione> azioneList = azioneRepository.findAll();
        assertThat(azioneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Azione in Elasticsearch
        verify(mockAzioneSearchRepository, times(1)).deleteById(azione.getId());
    }

    @Test
    @Transactional
    public void searchAzione() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        azioneService.save(azione);
        when(mockAzioneSearchRepository.search(queryStringQuery("id:" + azione.getId())))
            .thenReturn(Collections.singletonList(azione));

        // Search the azione
        restAzioneMockMvc.perform(get("/api/_search/aziones?query=id:" + azione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(azione.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].nomeAzione").value(hasItem(DEFAULT_NOME_AZIONE)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));
    }
}
