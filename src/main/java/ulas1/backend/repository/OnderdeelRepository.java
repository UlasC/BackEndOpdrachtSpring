package ulas1.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulas1.backend.domain.entity.Onderdeel;

public interface OnderdeelRepository extends JpaRepository<Onderdeel, Integer> {

}
