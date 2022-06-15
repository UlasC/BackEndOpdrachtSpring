package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.dto.CreateAutoDto;
import ulas1.backend.exception.AutoNotFoundException;
import ulas1.backend.exception.KlantHasNoCarException;
import ulas1.backend.repository.AutoRepository;

import java.util.Optional;

@Service
public class AutoService {

    private final AutoRepository autoRepository;
    private final KlantService klantService;

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
    public Auto getAutoByKenteken(String kenteken) {
        Optional<Auto> auto = autoRepository.findById(kenteken);
        if(auto.isEmpty()){
            throw new AutoNotFoundException(kenteken);
        }
        return auto.get();
    }

    public Auto getAutoByKlant(int bsn){
        Klant klant= klantService.getKlantByBsn(bsn);
        Optional<Auto> auto  = autoRepository.findAutoByKlant(klant);
        if(auto.isEmpty()){
            throw new KlantHasNoCarException(bsn);
        }
        return auto.get();
    }

    public Auto updateModel(String kenteken, String model){
        Auto auto = getAutoByKenteken(kenteken);
        auto.setModel(model);
        autoRepository.save(auto);
        return auto;
    }

    public Auto updateMerk(String kenteken, String merk){
        Auto auto = getAutoByKenteken(kenteken);
        auto.setMerk(merk);
        autoRepository.save(auto);
        return auto;
    }

    public Auto updateBrandstof(String kenteken, String brandstof){
        Auto auto = getAutoByKenteken(kenteken);
        auto.setBrandstof(brandstof);
        autoRepository.save(auto);
        return auto;
    }

    public void deleteAuto(String kenteken){
        Auto auto = getAutoByKenteken(kenteken);
        autoRepository.delete(auto);
    }
}
