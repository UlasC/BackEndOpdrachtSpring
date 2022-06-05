package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.BestaandeHandeling;

public interface HandelingRepository extends JpaRepository<BestaandeHandeling, Integer> {

}
