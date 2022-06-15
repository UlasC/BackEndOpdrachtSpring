package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.entity.BestaandeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.exception.HandelingNotFoundException;
import ulas1.backend.repository.HandelingRepository;

import java.util.Optional;

    @Service
    public class HandelingService {
        private final HandelingRepository handelingRepository;

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

        public BestaandeHandeling getHandelingByHandelingsnummer(Integer handelingsnummer) {
            Optional<BestaandeHandeling> handeling = handelingRepository.findById(handelingsnummer);
            if(handeling.isEmpty()){
                throw new HandelingNotFoundException(handelingsnummer);
            }
            return handeling.get();
        }

        public BestaandeHandeling updatePrijs(Integer handelingsnummer, double nieuwePrijs){
            BestaandeHandeling handeling = getHandelingByHandelingsnummer(handelingsnummer);
            handeling.setPrijs(nieuwePrijs);
            handelingRepository.save(handeling);
            return handeling;
        }

        public void deleteHandeling(Integer handelingsnummer){
            BestaandeHandeling handeling = getHandelingByHandelingsnummer(handelingsnummer);
            handelingRepository.delete(handeling);
        }
}
