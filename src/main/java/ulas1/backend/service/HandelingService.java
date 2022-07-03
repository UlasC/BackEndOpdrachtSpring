package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.entity.BestaandeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.domain.entity.Mankement;
import ulas1.backend.exception.HandelingNotFoundException;
import ulas1.backend.repository.HandelingRepository;
import ulas1.backend.repository.MankementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HandelingService {
    private final HandelingRepository handelingRepository;
    private final MankementRepository mankementRepository;

    @Autowired
    public HandelingService(HandelingRepository handelingRepository, MankementRepository mankementRepository) {
        this.handelingRepository = handelingRepository;
        this.mankementRepository = mankementRepository;
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
        removeHandelingFromMankementen(handeling); //verwijder de referentie naar de handeling uit alle mankementen
        handelingRepository.delete(handeling);
    }

    //RemoveHandelingFromMankementen roept zelf de MankementRepository aan in plaats van dit uit te besteden aan de MankementService
    // om te voorkomen dat twee beans circulair naar elkaar gaan verwijzen
    private void removeHandelingFromMankementen(BestaandeHandeling handeling_to_delete){
        List<Mankement> mankementen = handeling_to_delete.getMankementen();
        for(Mankement mankement: mankementen){
            List<BestaandeHandeling> bestaandeHandelingen = mankement.getBestaandeHandelingen();
            bestaandeHandelingen.remove(handeling_to_delete);
            mankement.setBestaandeHandelingen(bestaandeHandelingen);
            mankementRepository.save(mankement);
        }
    }
}
