package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.entity.Mankement;

import java.util.List;
import java.util.Optional;

public interface MankementRepository extends JpaRepository<Mankement, Integer> {
    Optional<List<Mankement>> findMankementenByAuto(Auto auto);
}
