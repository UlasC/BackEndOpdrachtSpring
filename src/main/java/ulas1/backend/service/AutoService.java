package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.dto.CreateAutoDto;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.AutoRepository;

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

        Klant klant = klantService.getKlantByBsn(createAutoDto.getBsn());
        auto.setKlant(klant);
        autoRepository.save(auto);
        return auto;
    }
    public Optional <Auto> getAutoByKenteken(String kenteken) {
        Optional<Auto> auto = autoRepository.findById(kenteken);
        return auto;
    }

    public Optional <Auto> getAutoByKlant(int bsn){
        Klant klant= klantService.getKlantByBsn(bsn);
        Optional<Auto> auto  = autoRepository.findAutoByKlant(klant);
        return auto;
    }

}
