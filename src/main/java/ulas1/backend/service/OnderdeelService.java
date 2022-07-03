package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ulas1.backend.domain.entity.Mankement;
import ulas1.backend.domain.entity.Onderdeel;

import ulas1.backend.exception.OnderdeelNotFoundException;
import ulas1.backend.repository.MankementRepository;
import ulas1.backend.repository.OnderdeelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OnderdeelService {
    private final OnderdeelRepository onderdeelRepository;
    private final MankementRepository mankementRepository;

    @Autowired
    public OnderdeelService(OnderdeelRepository onderdeelRepository, MankementRepository mankementRepository) {
        this.onderdeelRepository = onderdeelRepository;
        this.mankementRepository = mankementRepository;
    }

    public Onderdeel addOnderdeel(Onderdeel onderdeel){
        onderdeelRepository.save(onderdeel);
        return onderdeel;
    }

    public Onderdeel getOnderdeelByArtikelnummer(Integer artikelnummer) {
        Optional<Onderdeel> onderdeel = onderdeelRepository.findById(artikelnummer);
        if(onderdeel.isEmpty()){
            throw new OnderdeelNotFoundException(artikelnummer);
        }
        return onderdeel.get();
    }

    public Onderdeel updateVoorraad(int artikelnummer, int verschil){
        Onderdeel onderdeel = getOnderdeelByArtikelnummer(artikelnummer);
        onderdeel.updateVoorraad(verschil);
        onderdeelRepository.save(onderdeel);
        return onderdeel;
    }

    public Onderdeel updatePrijs(Integer artikelnummer, double nieuwePrijs){
        Onderdeel onderdeel = getOnderdeelByArtikelnummer(artikelnummer);
        onderdeel.setPrijs(nieuwePrijs);
        onderdeelRepository.save(onderdeel);
        return onderdeel;
    }

    public void deleteOnderdeel(Integer artikelnummer){
        Onderdeel onderdeel = getOnderdeelByArtikelnummer(artikelnummer);
        removeOnderdeelFromMankementen(onderdeel); //verwijder de referentie naar het onderdeel uit alle mankementen
        onderdeelRepository.delete(onderdeel);
    }

    //RemoveOnderdeelFromMankementen roept zelf de MankementRepository aan in plaats van dit uit te besteden aan de MankementService
    // om te voorkomen dat twee beans circulair naar elkaar gaan verwijzen
    private void removeOnderdeelFromMankementen(Onderdeel onderdeel_to_delete){
        List<Mankement> mankementen = onderdeel_to_delete.getMankementen();
        for(Mankement mankement: mankementen){
            List<Onderdeel> onderdelen = mankement.getOnderdelen();
            onderdelen.remove(onderdeel_to_delete);
            mankement.setOnderdelen(onderdelen);
            mankementRepository.save(mankement);
        }
    }
}

