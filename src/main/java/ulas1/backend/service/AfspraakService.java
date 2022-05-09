package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.Afspraak;
import ulas1.backend.domain.Klant;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.AfspraakRepository;
import ulas1.backend.repository.KlantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AfspraakService {

    private AfspraakRepository afspraakRepository;
    private KlantService klantService;

    @Autowired
    public AfspraakService(AfspraakRepository afspraakRepository, KlantService klantService) {
        this.afspraakRepository = afspraakRepository;
        this.klantService = klantService;
    }

    public Afspraak createAfspraak(CreateAfspraakDto createAfspraakDto){
        Afspraak afspraak = new Afspraak();
        afspraak.setDag(createAfspraakDto.getDag());
        afspraak.setMaand(createAfspraakDto.getMaand());
        afspraak.setJaar(createAfspraakDto.getJaar());
        afspraak.setTijd(createAfspraakDto.getTijd());
        afspraak.setSoortAfspraak(createAfspraakDto.getSoortAfspraak());

        Optional <Klant> klant = klantService.getKlantByBsn(createAfspraakDto.getBsn());
        if(klant.isEmpty()){
            throw new KlantNotFoundException(createAfspraakDto.getBsn());
        }
        Klant persoon = klant.get();
        afspraak.setKlant(persoon);
        afspraakRepository.save(afspraak);
        return afspraak;
    }

    public Optional <List<Afspraak>> getAfspraken(int bsn){
        Optional <Klant> klant= klantService.getKlantByBsn(bsn);
        if(klant.isEmpty()){
            throw new KlantNotFoundException(bsn);
        }
        Optional<List<Afspraak>> afspraken  = afspraakRepository.findAfsprakenByKlant(klant.get());
        return afspraken;
    }
}
