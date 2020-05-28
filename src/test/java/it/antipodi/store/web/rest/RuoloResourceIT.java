package it.antipodi.store.web.rest;

import it.antipodi.store.AntipodiStoreApp;
import it.antipodi.store.domain.Ruolo;
import it.antipodi.store.repository.RuoloRepository;
import it.antipodi.store.repository.search.RuoloSearchRepository;
import it.antipodi.store.service.RuoloService;

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
 * Integration tests for the {@link RuoloResource} REST controller.
 */
@SpringBootTest(classes = AntipodiStoreApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuoloResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOME_AZIONE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_AZIONE = "BBBBBBBBBB";

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private RuoloService ruoloService;

    /**
     * This repository is mocked in the it.antipodi.store.repository.search test package.
     *
     * @see it.antipodi.store.repository.search.RuoloSearchRepositoryMockConfiguration
     */
    @Autowired
    private RuoloSearchRepository mockRuoloSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuoloMockMvc;

    private Ruolo ruolo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ruolo createEntity(EntityManager em) {
        Ruolo ruolo = new Ruolo()
            .created(DEFAULT_CREATED)
            .modified(DEFAULT_MODIFIED)
            .nomeAzione(DEFAULT_NOME_AZIONE);
        return ruolo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ruolo createUpdatedEntity(EntityManager em) {
        Ruolo ruolo = new Ruolo()
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .nomeAzione(UPDATED_NOME_AZIONE);
        return ruolo;
    }

    @BeforeEach
    public void initTest() {
        ruolo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuolo() throws Exception {
        int databaseSizeBeforeCreate = ruoloRepository.findAll().size();
        // Create the Ruolo
        restRuoloMockMvc.perform(post("/api/ruolos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruolo)))
            .andExpect(status().isCreated());

        // Validate the Ruolo in the database
        List<Ruolo> ruoloList = ruoloRepository.findAll();
        assertThat(ruoloList).hasSize(databaseSizeBeforeCreate + 1);
        Ruolo testRuolo = ruoloList.get(ruoloList.size() - 1);
        assertThat(testRuolo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRuolo.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testRuolo.getNomeAzione()).isEqualTo(DEFAULT_NOME_AZIONE);

        // Validate the Ruolo in Elasticsearch
        verify(mockRuoloSearchRepository, times(1)).save(testRuolo);
    }

    @Test
    @Transactional
    public void createRuoloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruoloRepository.findAll().size();

        // Create the Ruolo with an existing ID
        ruolo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuoloMockMvc.perform(post("/api/ruolos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruolo)))
            .andExpect(status().isBadRequest());

        // Validate the Ruolo in the database
        List<Ruolo> ruoloList = ruoloRepository.findAll();
        assertThat(ruoloList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ruolo in Elasticsearch
        verify(mockRuoloSearchRepository, times(0)).save(ruolo);
    }


    @Test
    @Transactional
    public void getAllRuolos() throws Exception {
        // Initialize the database
        ruoloRepository.saveAndFlush(ruolo);

        // Get all the ruoloList
        restRuoloMockMvc.perform(get("/api/ruolos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruolo.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].nomeAzione").value(hasItem(DEFAULT_NOME_AZIONE)));
    }
    
    @Test
    @Transactional
    public void getRuolo() throws Exception {
        // Initialize the database
        ruoloRepository.saveAndFlush(ruolo);

        // Get the ruolo
        restRuoloMockMvc.perform(get("/api/ruolos/{id}", ruolo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruolo.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.toString()))
            .andExpect(jsonPath("$.nomeAzione").value(DEFAULT_NOME_AZIONE));
    }
    @Test
    @Transactional
    public void getNonExistingRuolo() throws Exception {
        // Get the ruolo
        restRuoloMockMvc.perform(get("/api/ruolos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuolo() throws Exception {
        // Initialize the database
        ruoloService.save(ruolo);

        int databaseSizeBeforeUpdate = ruoloRepository.findAll().size();

        // Update the ruolo
        Ruolo updatedRuolo = ruoloRepository.findById(ruolo.getId()).get();
        // Disconnect from session so that the updates on updatedRuolo are not directly saved in db
        em.detach(updatedRuolo);
        updatedRuolo
            .created(UPDATED_CREATED)
            .modified(UPDATED_MODIFIED)
            .nomeAzione(UPDATED_NOME_AZIONE);

        restRuoloMockMvc.perform(put("/api/ruolos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRuolo)))
            .andExpect(status().isOk());

        // Validate the Ruolo in the database
        List<Ruolo> ruoloList = ruoloRepository.findAll();
        assertThat(ruoloList).hasSize(databaseSizeBeforeUpdate);
        Ruolo testRuolo = ruoloList.get(ruoloList.size() - 1);
        assertThat(testRuolo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRuolo.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testRuolo.getNomeAzione()).isEqualTo(UPDATED_NOME_AZIONE);

        // Validate the Ruolo in Elasticsearch
        verify(mockRuoloSearchRepository, times(2)).save(testRuolo);
    }

    @Test
    @Transactional
    public void updateNonExistingRuolo() throws Exception {
        int databaseSizeBeforeUpdate = ruoloRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuoloMockMvc.perform(put("/api/ruolos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruolo)))
            .andExpect(status().isBadRequest());

        // Validate the Ruolo in the database
        List<Ruolo> ruoloList = ruoloRepository.findAll();
        assertThat(ruoloList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ruolo in Elasticsearch
        verify(mockRuoloSearchRepository, times(0)).save(ruolo);
    }

    @Test
    @Transactional
    public void deleteRuolo() throws Exception {
        // Initialize the database
        ruoloService.save(ruolo);

        int databaseSizeBeforeDelete = ruoloRepository.findAll().size();

        // Delete the ruolo
        restRuoloMockMvc.perform(delete("/api/ruolos/{id}", ruolo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ruolo> ruoloList = ruoloRepository.findAll();
        assertThat(ruoloList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ruolo in Elasticsearch
        verify(mockRuoloSearchRepository, times(1)).deleteById(ruolo.getId());
    }

    @Test
    @Transactional
    public void searchRuolo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ruoloService.save(ruolo);
        when(mockRuoloSearchRepository.search(queryStringQuery("id:" + ruolo.getId())))
            .thenReturn(Collections.singletonList(ruolo));

        // Search the ruolo
        restRuoloMockMvc.perform(get("/api/_search/ruolos?query=id:" + ruolo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruolo.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].nomeAzione").value(hasItem(DEFAULT_NOME_AZIONE)));
    }
}
