package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.dto.MedewerkerCreatedDto;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.exception.MedewerkerNotFoundException;
import ulas1.backend.repository.AfspraakRepository;
import ulas1.backend.repository.MedewerkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedewerkerService {
    private final MedewerkerRepository medewerkerRepository;
    private final AfspraakRepository afspraakRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public MedewerkerService(MedewerkerRepository medewerkerRepository, AfspraakRepository afspraakRepository){
        this.medewerkerRepository = medewerkerRepository;
        this.afspraakRepository = afspraakRepository;
    }

    public MedewerkerCreatedDto addMedewerker(Medewerker medewerker){
        //Encodeer het wachtwoord
        medewerker.setWachtwoord(encoder.encode(medewerker.getWachtwoord()));

        medewerkerRepository.save(medewerker);
        return MedewerkerCreatedDto.from(medewerker);
    }

    //Deze methode is de enige methode hier die geen MedewerkerCreatedDto maar een Medewerker teruggeeft.
    //Dat komt omdat de andere methodes worden aangeroepen door de controller,
    // maar deze methode ook aangeroepen wordt door andere methodes binnen deze service,
    // die wel het volledige medewerker-object nodig hebben.
    public Medewerker getMedewerkerByGebruikersnaam(String gebruikersnaam){
        Optional<Medewerker> medewerker = medewerkerRepository.findById(gebruikersnaam);
        if(medewerker.isEmpty()){
            throw new MedewerkerNotFoundException(gebruikersnaam);
        }
        return medewerker.get();
    }

    public void updateWachtwoord(String gebruikersnaam, String wachtwoord){
        Medewerker medewerker = getMedewerkerByGebruikersnaam(gebruikersnaam);
        String encoded_password = encoder.encode(wachtwoord); //Encodeer het wachtwoord
        medewerker.setWachtwoord(encoded_password);
        medewerkerRepository.save(medewerker);
    }

    public MedewerkerCreatedDto updateRole(String gebruikersnaam, String role){
        Medewerker medewerker = getMedewerkerByGebruikersnaam(gebruikersnaam);
        medewerker.setRole(role);
        medewerkerRepository.save(medewerker);
        return MedewerkerCreatedDto.from(medewerker);
    }

    public void deleteMedewerker(String gebruikersnaam){
        Medewerker medewerker = getMedewerkerByGebruikersnaam(gebruikersnaam);
        removeAfsprakenByMedewerker(medewerker); //Haal ook meteen de afspraken van de medewerker uit het systeem
        medewerkerRepository.delete(medewerker);
    }

    //Deze methode communiceert direct met de AfspraakRepository omdat het niet mogelijk was
    // om dit uit te besteden aan de Afspraakservice zonder een circulaire referentie tussen
    // twee beans te creëren
    private void removeAfsprakenByMedewerker(Medewerker medewerker){
        List<Afspraak> afspraken = medewerker.getAfspraken();
        for(Afspraak afspraak: afspraken){
            afspraakRepository.delete(afspraak);
        }
    }
}
