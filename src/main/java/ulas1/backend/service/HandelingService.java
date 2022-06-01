package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.BestaandeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.repository.HandelingRepository;

import java.util.Optional;

    @Service
    public class HandelingService {
        private HandelingRepository handelingRepository;

        @Autowired
        public HandelingService(HandelingRepository handelingRepository) {
            this.handelingRepository = handelingRepository;
        }

        public BestaandeHandeling addHandeling(CreateHandelingDto createHandelingDto){
            BestaandeHandeling handeling = new BestaandeHandeling();
            handeling.setHandeling(createHandelingDto.getHandeling());
            handeling.setPrijs(createHandelingDto.getPrijs());

            handelingRepository.save(handeling);
            return handeling;
        }

        public Optional<BestaandeHandeling> getHandelingByHandelingsnummer(Integer handelingsnummer) {
            Optional<BestaandeHandeling> handeling = handelingRepository.findById(handelingsnummer);
            return handeling;
        }
}
