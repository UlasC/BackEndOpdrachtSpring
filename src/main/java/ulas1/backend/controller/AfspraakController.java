package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.service.AfspraakService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController // Dit is de controller: spring object
@RequestMapping("/afspraken")
public class AfspraakController {

    @Autowired
    public AfspraakController(AfspraakService afspraakService) {
        this.afspraakService = afspraakService;
    }


    private AfspraakService afspraakService;


    @PostMapping
    public ResponseEntity<Afspraak> createAfspraak(@Valid @RequestBody CreateAfspraakDto createAfspraakDto){
        Afspraak afspraak = afspraakService.createAfspraak(createAfspraakDto);

        final URI location = URI.create("/afspraken/" + afspraak.getKlant().getBsn());

        return ResponseEntity.created(location).body(afspraak);
    }

    @GetMapping("klant/{bsn}")
    public ResponseEntity<List<Afspraak>> getAfspraken(@PathVariable Integer bsn){
        List<Afspraak> afspraken = afspraakService.getAfspraken(bsn);
        if(afspraken.size() > 0) {
            return ResponseEntity.ok(afspraken);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("medewerker/{gebruikersnaam}")
    public ResponseEntity<List<Afspraak>> getAfspraken(@PathVariable String gebruikersnaam){
        List<Afspraak> afspraken = afspraakService.getAfspraken(gebruikersnaam);
        if(afspraken.size() > 0) {
            return ResponseEntity.ok(afspraken);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
