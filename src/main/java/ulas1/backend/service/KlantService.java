package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.dto.CreateKlantDto;
import ulas1.backend.domain.dto.UpdateNameDto;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.entity.IdentiteitskaartFoto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.AfspraakRepository;
import ulas1.backend.repository.AutoRepository;
import ulas1.backend.repository.IdentiteitskaartFotoRepository;
import ulas1.backend.repository.KlantRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Dit is de service: de tweede spring laag, waarin de klant berekeningen worden gedaan.
@Service
public class KlantService {
    //De Klant service heeft een link naar klant repository
    private final KlantRepository klantRepository;

    //Klant service heeft ook een link naar andere repositories.
    //Als er dan een klant verwijderd wordt, kunnen ook alle bijbehorende auto's, afspraken en ID-foto's verwijderd worden.
    //Dit kan niet via de service omdat je dan een circulaire referentie tussen twee beans krijgt.
    private final AutoRepository autoRepository;
    private final AfspraakRepository afspraakRepository;
    private final IdentiteitskaartFotoRepository identiteitskaartFotoRepository;

    //Autowired zorgt ervoor dat spring zelf de vier repositories aanmaakt.
    @Autowired
    public KlantService(KlantRepository klantRepository, AutoRepository autoRepository, AfspraakRepository afspraakRepository, IdentiteitskaartFotoRepository identiteitskaartFotoRepository) {
        this.klantRepository = klantRepository;
        this.autoRepository = autoRepository;
        this.afspraakRepository = afspraakRepository;
        this.identiteitskaartFotoRepository = identiteitskaartFotoRepository;
    }

    //In deze methode wordt een nieuwe klant aangemaakt en via de repository naar de database gestuurd
    public Klant createKlant(CreateKlantDto createKlantDto){
        Klant klant = new Klant();
        klant.setAdres(createKlantDto.getAdres());
        klant.setFirstName(createKlantDto.getFirstName());
        klant.setLastName(createKlantDto.getLastName());
        klant.setBsn(createKlantDto.getBsn());
        klant.setAfspraken(new ArrayList<>());
        klant.setAuto(null);
        klant.setIdentiteitskaartFotos(new ArrayList<>());

        klantRepository.save(klant);
        return klant;
    }

    //Via deze methode kunnen de gegevens van een klant opgevraagd worden
    public Klant getKlantByBsn(Integer bsn) {
        Optional<Klant> klant = klantRepository.findById(bsn);
        if(klant.isEmpty()){
            throw new KlantNotFoundException();
        }
        return klant.get();
    }

    //Via deze methode kan de naam van een klant veranderd worden
    public Klant updateName(Integer bsn, UpdateNameDto updateNameDto){
        Klant klant = getKlantByBsn(bsn);
        klant.setFirstName(updateNameDto.getFirstName());
        klant.setLastName(updateNameDto.getLastName());
        klantRepository.save(klant);
        return klant;
    }

    //Via deze methode kan het adres van een klant veranderd worden
    public Klant updateAdres(Integer bsn, String adres){
        Klant klant = getKlantByBsn(bsn);
        klant.setAdres(adres);
        klantRepository.save(klant);
        return klant;
    }

    //Via deze methode kan een klant worden verwijderd
    public void deleteKlant(Integer bsn){
        Klant klant = getKlantByBsn(bsn);
        removeAutoByKlant(klant); //verwijder ook zijn auto
        removeAfsprakenByKlant(klant); //verwijder ook zijn afspraken
        removeFotosByKlant(klant); //verwijder ook de foto's van zijn ID-kaart
        klantRepository.delete(klant);
    }

    private void removeAutoByKlant(Klant klant){
        Auto auto = klant.getAuto();
        if(auto != null){
            autoRepository.delete(auto); //Verwijder de auto, als de klant er een had
        }
    }

    private void removeAfsprakenByKlant(Klant klant){
        List<Afspraak> afspraken = klant.getAfspraken();
        for(Afspraak afspraak: afspraken){
            afspraakRepository.delete(afspraak);
        }
    }

    @Transactional
    private void removeFotosByKlant(Klant klant){
        List<IdentiteitskaartFoto> fotos = klant.getIdentiteitskaartFotos();
        for(IdentiteitskaartFoto foto: fotos){
            identiteitskaartFotoRepository.delete(foto);
        }
    }
}
