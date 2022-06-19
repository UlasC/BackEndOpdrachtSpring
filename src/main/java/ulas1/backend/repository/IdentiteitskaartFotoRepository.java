package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.IdentiteitskaartFoto;
import ulas1.backend.domain.entity.Klant;

import java.util.List;
import java.util.Optional;

public interface IdentiteitskaartFotoRepository extends JpaRepository<IdentiteitskaartFoto, Integer> {
    Optional<List<IdentiteitskaartFoto>> findIdentiteitskaartFotosByKlant(Klant klant);
}
