package ulas1.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.Afspraak;
import ulas1.backend.domain.Klant;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.service.AfspraakService;
import ulas1.backend.service.KlantService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController // Dit is de controller: spring object
@RequestMapping("/afspraken")
public class AfspraakController {

    @Autowired
    public AfspraakController(AfspraakService afspraakService, KlantService klantService) {
        this.afspraakService = afspraakService;
    }


    private AfspraakService afspraakService;


    @PostMapping
    public ResponseEntity<Afspraak> createAfspraak(@RequestBody CreateAfspraakDto createAfspraakDto){
        Afspraak afspraak = afspraakService.createAfspraak(createAfspraakDto);

        final URI location = URI.create("/afspraken/" + afspraak.getKlant().getBsn());

        return ResponseEntity.created(location).body(afspraak);
    }

    @GetMapping("{bsn}")
    public ResponseEntity<Optional<List<Afspraak>>> getAfspraken(@PathVariable Integer bsn){
        Optional<List<Afspraak>> afspraken = afspraakService.getAfspraken(bsn);
        if(afspraken.isPresent()) {
            return ResponseEntity.ok(afspraken);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
