package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.Klant;
import ulas1.backend.repository.KlantRepository;

import java.util.Optional;

// Dit is de service klas: hierin worden de klant berekeningen gedaan.
@Service
public class KlantService {
// Klant service heeft een link naar klant repository
    private KlantRepository klantRepository;
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

    public Optional <Klant> getKlantByBsn(Integer bsn) {
        Optional<Klant> klant = klantRepository.findById(bsn);
        return klant;
    }
}
