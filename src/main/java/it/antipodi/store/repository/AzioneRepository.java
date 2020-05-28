package it.antipodi.store.repository;

import it.antipodi.store.domain.Azione;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Azione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AzioneRepository extends JpaRepository<Azione, Long> {
}
