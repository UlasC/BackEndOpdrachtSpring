package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.entity.Klant;

import java.util.Optional;

public interface AutoRepository  extends JpaRepository<Auto, String> {
    public Optional<Auto> findAutoByKlant(Klant klant);
}
