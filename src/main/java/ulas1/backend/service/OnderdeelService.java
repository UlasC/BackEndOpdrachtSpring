package ulas1.backend.service;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ulas1.backend.domain.Onderdeel;

import ulas1.backend.domain.dto.UpdateVoorraadDto;
import ulas1.backend.repository.OnderdeelRepository;

import java.util.Optional;

@Service
public class OnderdeelService {
    private OnderdeelRepository onderdeelRepository;

    @Autowired
    public OnderdeelService(OnderdeelRepository onderdeelRepository) {
        this.onderdeelRepository = onderdeelRepository;
    }

    public Onderdeel addOnderdeel(Onderdeel onderdeel){
        onderdeelRepository.save(onderdeel);
        return onderdeel;
    }

    public Optional<Onderdeel> getOnderdeelByArtikelnummer(Integer artikelnummer) {
        Optional<Onderdeel> onderdeel = onderdeelRepository.findById(artikelnummer);
        return onderdeel;
    }
    public Optional<Onderdeel> updateVoorraad(int artikelnummer, UpdateVoorraadDto updateVoorraadDto){
        Optional<Onderdeel> onderdeel = onderdeelRepository.findById(artikelnummer);
        if(onderdeel.isEmpty()) {
            return Optional.empty();
        }
        onderdeel.get().updateVoorraad(updateVoorraadDto.getVerschil());
        onderdeelRepository.save(onderdeel.get());
        return onderdeel;
    }
}

