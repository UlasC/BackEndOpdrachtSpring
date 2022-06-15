package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Afspraak createAfspraak(CreateAfspraakDto createAfspraakDto){
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
        
        if(hasAfspraak(klant,afspraak.getTijd(), afspraak.getDag(), afspraak.getMaand(), afspraak.getJaar())){
            throw new KlantHeeftAlAfspraakException(klant.getFirstName(), klant.getLastName());
        }
        if(hasAfspraak(medewerker,afspraak.getTijd(), afspraak.getDag(), afspraak.getMaand(), afspraak.getJaar())){
            throw new MedewerkerHeeftAlAfspraakException(medewerker.getGebruikersnaam());
        }
        
        afspraakRepository.save(afspraak);
        return afspraak;
    }

    public List<Afspraak> getAfspraken(int bsn){
        Klant klant= klantService.getKlantByBsn(bsn);
        Optional<List<Afspraak>> afspraken  = afspraakRepository.findAfsprakenByKlant(klant);
        if(afspraken.isEmpty()){
            return new ArrayList<>();
        }else{
            return afspraken.get();
        }
    }

    public List<Afspraak> getAfspraken(String gebruikersnaam){
        Medewerker medewerker = medewerkerService.getMedewerkerByGebruikersnaam(gebruikersnaam);
        Optional<List<Afspraak>> afspraken  = afspraakRepository.findAfsprakenByMedewerker(medewerker);
        if(afspraken.isEmpty()){
            return new ArrayList<>();
        }else{
            return afspraken.get();
        }
    }

    public boolean hasAfspraak(Klant klant, String tijd, int dag, int maand, int jaar){
        List<Afspraak> afspraken = getAfspraken(klant.getBsn());
        if(afspraken.size() > 0){
            for(Afspraak afspraak: afspraken){
                if(afspraak.getTijd().equals(tijd) && afspraak.getDag() == dag && afspraak.getMaand() == maand && afspraak.getJaar() == jaar){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAfspraak(Medewerker medewerker, String tijd, int dag, int maand, int jaar){
        List<Afspraak> afspraken = getAfspraken(medewerker.getGebruikersnaam());
        if(afspraken.size() > 0){
            for(Afspraak afspraak: afspraken){
                if(afspraak.getTijd().equals(tijd) && afspraak.getDag() == dag && afspraak.getMaand() == maand && afspraak.getJaar() == jaar){
                    return true;
                }
            }
        }
        return false;
    }
}
