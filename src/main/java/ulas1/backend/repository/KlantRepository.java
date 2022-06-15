package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.Klant;

// Dit is de laag die met de database communiceert
public interface KlantRepository extends JpaRepository<Klant, Integer> {

}
