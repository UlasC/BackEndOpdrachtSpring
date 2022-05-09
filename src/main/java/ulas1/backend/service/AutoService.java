package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.Afspraak;
import ulas1.backend.domain.Auto;
import ulas1.backend.domain.Klant;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.domain.dto.CreateAutoDto;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.AfspraakRepository;
import ulas1.backend.repository.AutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AutoService {

    private AutoRepository autoRepository;
    private KlantService klantService;

    @Autowired
    public AutoService(AutoRepository autoRepository, KlantService klantService) {
        this.autoRepository = autoRepository;
        this.klantService = klantService;
    }

    public Auto createAuto(CreateAutoDto createAutoDto){
        Auto auto = new Auto();
        auto.setModel(createAutoDto.getModel());
        auto.setMerk(createAutoDto.getMerk());
        auto.setBrandstof(createAutoDto.getBrandstof());
        auto.setKenteken(createAutoDto.getKenteken());

        Optional<Klant> klant = klantService.getKlantByBsn(createAutoDto.getBsn());
        if(klant.isEmpty()){
            throw new KlantNotFoundException(createAutoDto.getBsn());
        }
        Klant persoon = klant.get();
        auto.setKlant(persoon);
        autoRepository.save(auto);
        return auto;
    }
    public Optional <Auto> getAutoByKenteken(String kenteken) {
        Optional<Auto> auto = autoRepository.findById(kenteken);
        return auto;
    }

    public Optional <Auto> getAutoByKlant(int bsn){
        Optional <Klant> klant= klantService.getKlantByBsn(bsn);
        if(klant.isEmpty()){
            throw new KlantNotFoundException(bsn);
        }
        Optional<Auto> auto  = autoRepository.findAutoByKlant(klant.get());
        return auto;
    }

}
