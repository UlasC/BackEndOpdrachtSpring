package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ulas1.backend.domain.entity.Onderdeel;

import ulas1.backend.exception.OnderdeelNotFoundException;
import ulas1.backend.repository.OnderdeelRepository;

import java.util.Optional;

@Service
public class OnderdeelService {
    private final OnderdeelRepository onderdeelRepository;

    @Autowired
    public OnderdeelService(OnderdeelRepository onderdeelRepository) {
        this.onderdeelRepository = onderdeelRepository;
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
        onderdeelRepository.delete(onderdeel);
    }
}

