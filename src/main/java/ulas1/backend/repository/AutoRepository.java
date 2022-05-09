package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.Afspraak;
import ulas1.backend.domain.Auto;
import ulas1.backend.domain.Klant;
import ulas1.backend.idClass.AfspraakId;

import java.util.List;
import java.util.Optional;

public interface AutoRepository  extends JpaRepository<Auto, String> {
    public Optional<Auto> findAutoByKlant(Klant klant);
}
