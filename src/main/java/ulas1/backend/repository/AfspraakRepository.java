package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.idClass.AfspraakId;

import java.util.List;
import java.util.Optional;

public interface AfspraakRepository extends JpaRepository<Afspraak, AfspraakId> {
    public Optional<List<Afspraak>> findAfsprakenByKlant(Klant klant);

    public Optional<List<Afspraak>> findAfsprakenByMedewerker(Medewerker medewerker);
}
