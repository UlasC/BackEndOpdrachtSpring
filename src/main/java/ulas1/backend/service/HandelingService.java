package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.Handeling;
import ulas1.backend.domain.dto.UpdateVoorraadDto;
import ulas1.backend.repository.HandelingRepository;

import java.util.Optional;

    @Service
    public class HandelingService {
        private HandelingRepository handelingRepository;

        @Autowired
        public HandelingService(HandelingRepository handelingRepository) {
            this.handelingRepository = handelingRepository;
        }

        public Handeling addHandeling(Handeling handeling){
            handelingRepository.save(handeling);
            return handeling;
        }

        public Optional<Handeling> getHandelingByHandelingsnummer(Integer handelingsnummer) {
            Optional<Handeling> handeling = handelingRepository.findById(handelingsnummer);
            return handeling;
        }
}
