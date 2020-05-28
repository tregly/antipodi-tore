package it.antipodi.store.repository;

import it.antipodi.store.domain.Ruolo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ruolo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
}
