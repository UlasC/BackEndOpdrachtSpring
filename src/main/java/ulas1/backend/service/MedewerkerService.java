package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.dto.MedewerkerCreatedDto;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.exception.MedewerkerNotFoundException;
import ulas1.backend.repository.MedewerkerRepository;

import java.util.Optional;

@Service
public class MedewerkerService {
    private final MedewerkerRepository medewerkerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public MedewerkerService(MedewerkerRepository medewerkerRepository){
        this.medewerkerRepository = medewerkerRepository;
    }

    public MedewerkerCreatedDto addMedewerker(Medewerker medewerker){
        //Encode raw password
        medewerker.setWachtwoord(encoder.encode(medewerker.getWachtwoord()));

        medewerkerRepository.save(medewerker);
        return getDTOfromMedewerker(medewerker);
    }

    public Medewerker getMedewerkerByGebruikersnaam(String gebruikersnaam){
        Optional<Medewerker> medewerker = medewerkerRepository.findById(gebruikersnaam);
        if(medewerker.isEmpty()){
            throw new MedewerkerNotFoundException(gebruikersnaam);
        }
        return medewerker.get();
    }

    public void updateWachtwoord(String gebruikersnaam, String wachtwoord){
        Medewerker medewerker = getMedewerkerByGebruikersnaam(gebruikersnaam);
        String encoded_password = encoder.encode(wachtwoord);
        medewerker.setWachtwoord(encoded_password);
        medewerkerRepository.save(medewerker);
    }

    public MedewerkerCreatedDto updateRole(String gebruikersnaam, String role){
        Medewerker medewerker = getMedewerkerByGebruikersnaam(gebruikersnaam);
        medewerker.setRole(role);
        medewerkerRepository.save(medewerker);
        return getDTOfromMedewerker(medewerker);
    }

    public void deleteMedewerker(String gebruikersnaam){
        Medewerker medewerker = getMedewerkerByGebruikersnaam(gebruikersnaam);
        medewerkerRepository.delete(medewerker);
    }

    public MedewerkerCreatedDto getDTOfromMedewerker(Medewerker medewerker){
        MedewerkerCreatedDto medewerkerCreatedDto = new MedewerkerCreatedDto();
        medewerkerCreatedDto.setGebruikersnaam(medewerker.getGebruikersnaam());
        medewerkerCreatedDto.setRole(medewerker.getRole());
        medewerkerCreatedDto.setEnabled(medewerker.isEnabled());
        return medewerkerCreatedDto;
    }
}
