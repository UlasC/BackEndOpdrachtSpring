package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.dto.MedewerkerCreatedDto;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.service.MedewerkerService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/medewerkers")
public class MedewerkerController {

    private MedewerkerService medewerkerService;

    @Autowired
    public MedewerkerController(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }

    @PostMapping
    public ResponseEntity<MedewerkerCreatedDto> addMedewerker(@Valid @RequestBody Medewerker medewerker){
        MedewerkerCreatedDto medewerkerCreatedDto = medewerkerService.addMedewerker(medewerker);

        final URI location = URI.create("/gebruikers/" + medewerkerCreatedDto.getGebruikersnaam());

        return ResponseEntity.created(location).body(medewerkerCreatedDto);
    }

    @GetMapping("{gebruikersnaam}")
    public ResponseEntity<MedewerkerCreatedDto> getGebruiker(@PathVariable String gebruikersnaam){
        Medewerker medewerker = medewerkerService.getMedewerkerByGebruikersnaam(gebruikersnaam);
        MedewerkerCreatedDto medewerkerCreatedDto = MedewerkerCreatedDto.from(medewerker);
        return ResponseEntity.ok(medewerkerCreatedDto);
    }

    @PutMapping("{gebruikersnaam}/wachtwoord")
    public ResponseEntity<MedewerkerCreatedDto> updateWachtwoord(@PathVariable String gebruikersnaam, @Valid @RequestBody String nieuwwachtwoord){
        medewerkerService.updateWachtwoord(gebruikersnaam, nieuwwachtwoord);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{gebruikersnaam}/rol")
    public ResponseEntity<MedewerkerCreatedDto> updateRole(@PathVariable String gebruikersnaam, @Valid @RequestBody String rol){
        MedewerkerCreatedDto medewerkerCreatedDto = medewerkerService.updateRole(gebruikersnaam, rol);
        return ResponseEntity.ok(medewerkerCreatedDto);
    }

    @DeleteMapping("{gebruikersnaam}")
    public ResponseEntity<MedewerkerCreatedDto> deleteMedewerker(@PathVariable String gebruikersnaam){
        medewerkerService.deleteMedewerker(gebruikersnaam);
        return ResponseEntity.noContent().build();
    }
}
