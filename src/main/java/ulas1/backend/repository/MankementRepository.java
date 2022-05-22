package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.Auto;
import ulas1.backend.domain.Klant;
import ulas1.backend.domain.Mankement;

import java.util.List;
import java.util.Optional;

public interface MankementRepository extends JpaRepository<Mankement, Integer> {
    public Optional<List<Mankement>>findMankementenByAuto(Auto auto);
}
