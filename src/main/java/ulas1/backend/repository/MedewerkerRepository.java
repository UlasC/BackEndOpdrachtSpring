package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.Medewerker;

public interface MedewerkerRepository extends JpaRepository<Medewerker, String> {
}
