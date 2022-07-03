package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.dto.AfspraakDto;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.exception.KlantHeeftAlAfspraakException;
import ulas1.backend.exception.MedewerkerHeeftAlAfspraakException;
import ulas1.backend.repository.AfspraakRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AfspraakService {

    private final AfspraakRepository afspraakRepository;
    private final KlantService klantService;
    private final MedewerkerService medewerkerService;

    @Autowired
    public AfspraakService(AfspraakRepository afspraakRepository, KlantService klantService, MedewerkerService medewerkerService) {
        this.afspraakRepository = afspraakRepository;
        this.klantService = klantService;
        this.medewerkerService = medewerkerService;
    }

    public AfspraakDto createAfspraak(CreateAfspraakDto createAfspraakDto){
        Afspraak afspraak = new Afspraak();
        afspraak.setDag(createAfspraakDto.getDag());
        afspraak.setMaand(createAfspraakDto.getMaand());
        afspraak.setJaar(createAfspraakDto.getJaar());
        afspraak.setTijd(createAfspraakDto.getTijd());
        afspraak.setSoortAfspraak(createAfspraakDto.getSoortAfspraak());

        Klant klant = klantService.getKlantByBsn(createAfspraakDto.getBsn());
        afspraak.setKlant(klant);

        Medewerker medewerker = medewerkerService.getMedewerkerByGebruikersnaam(createAfspraakDto.getGebruikersnaam());
        afspraak.setMedewerker(medewerker);

        //Als de klant of de medewerker al een afspraak heeft staan, gooi een exception
        if(hasAfspraak(klant,afspraak.getTijd(), afspraak.getDag(), afspraak.getMaand(), afspraak.getJaar())){
            throw new KlantHeeftAlAfspraakException(klant.getFirstName(), klant.getLastName());
        }
        if(hasAfspraak(medewerker,afspraak.getTijd(), afspraak.getDag(), afspraak.getMaand(), afspraak.getJaar())){
            throw new MedewerkerHeeftAlAfspraakException(medewerker.getGebruikersnaam());
        }

        afspraakRepository.save(afspraak);
        return AfspraakDto.from(afspraak);
    }

    public List<AfspraakDto> getAfspraken(int bsn){
        Klant klant= klantService.getKlantByBsn(bsn);
        Optional<List<Afspraak>> afspraken  = afspraakRepository.findAfsprakenByKlant(klant);
        if(afspraken.isEmpty()){
            return new ArrayList<>();
        }else{
            return AfspraakDto.from(afspraken.get());
        }
    }

    public List<AfspraakDto> getAfspraken(String gebruikersnaam){
        Medewerker medewerker = medewerkerService.getMedewerkerByGebruikersnaam(gebruikersnaam);
        Optional<List<Afspraak>> afspraken  = afspraakRepository.findAfsprakenByMedewerker(medewerker);
        if(afspraken.isEmpty()){
            return new ArrayList<>();
        }else{
            return AfspraakDto.from(afspraken.get());
        }
    }

    public boolean hasAfspraak(Klant klant, String tijd, int dag, int maand, int jaar){
        List<AfspraakDto> afspraakDtos = getAfspraken(klant.getBsn());
        if(afspraakDtos.size() > 0){
            for(AfspraakDto afspraakDto: afspraakDtos){
                if(afspraakDto.getTijd().equals(tijd) && afspraakDto.getDag() == dag && afspraakDto.getMaand() == maand && afspraakDto.getJaar() == jaar){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAfspraak(Medewerker medewerker, String tijd, int dag, int maand, int jaar){
        List<AfspraakDto> afspraakDtos = getAfspraken(medewerker.getGebruikersnaam());
        if(afspraakDtos.size() > 0){
            for(AfspraakDto afspraakDto: afspraakDtos){
                if(afspraakDto.getTijd().equals(tijd) && afspraakDto.getDag() == dag && afspraakDto.getMaand() == maand && afspraakDto.getJaar() == jaar){
                    return true;
                }
            }
        }
        return false;
    }
}
