package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.dto.UpdateNameDto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.KlantRepository;

import java.util.Optional;

// Dit is de service klas: hierin worden de klant berekeningen gedaan.
@Service
public class KlantService {
// Klant service heeft een link naar klant repository
    KlantRepository klantRepository;
// Autowired zorgt ervoor dat spring zelf een klant repository aanmaakt.
    @Autowired
    public KlantService(KlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }
// hier wordt de klant gecreeerd
    public Klant createKlant(Klant klant){
        klantRepository.save(klant);
        return klant;
    }

    public Klant getKlantByBsn(Integer bsn) {
        Optional<Klant> klant = klantRepository.findById(bsn);
        if(klant.isEmpty()){
            throw new KlantNotFoundException();
        }
        return klant.get();
    }

    public Klant updateName(Integer bsn, UpdateNameDto updateNameDto){
        Klant klant = getKlantByBsn(bsn);
        klant.setFirstName(updateNameDto.getFirstName());
        klant.setLastName(updateNameDto.getLastName());
        klantRepository.save(klant);
        return klant;
    }

    public Klant updateAdres(Integer bsn, String adres){
        Klant klant = getKlantByBsn(bsn);
        klant.setAdres(adres);
        klantRepository.save(klant);
        return klant;
    }

    public void deleteKlant(Integer bsn){
        Klant klant = getKlantByBsn(bsn);
        klantRepository.delete(klant);
    }
}
