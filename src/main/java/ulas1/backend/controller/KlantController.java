package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.dto.UpdateNameDto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.service.KlantService;

import javax.validation.Valid;
import java.net.URI;

@RestController // Dit is de controller: spring object
@RequestMapping("/klanten") // de path van de localHost
public class KlantController { // classe

    //Autowired: spring maak automatisch klanten aan: klantenservice: wordt door spring aangemaakt
    @Autowired
    public KlantController(KlantService klantservice) {
        this.klantservice = klantservice;
    }

    // de klantenservice die wordt aangemaakt.
    private KlantService klantservice;

    // deze methode zorgt ervoor: als er post request wordt gestuurd: word de klant aangemaakt.
    @PostMapping
    public ResponseEntity<Klant> createKlant(@Valid @RequestBody Klant klant){
        klantservice.createKlant(klant);

        final URI location = URI.create("/klanten/" + klant.getBsn());

        return ResponseEntity.created(location).body(klant);
    }

    @GetMapping("{bsn}")
    public ResponseEntity<Klant> getKlant(@PathVariable int bsn){
        Klant klant = klantservice.getKlantByBsn(bsn);
        return ResponseEntity.ok(klant);
    }

    @PutMapping("{bsn}/name")
    public ResponseEntity<Klant> updateName(@PathVariable int bsn, @Valid @RequestBody UpdateNameDto updateNameDto){
        Klant klant = klantservice.updateName(bsn, updateNameDto);
        return ResponseEntity.ok(klant);
    }

    @PutMapping("{bsn}/adres")
    public ResponseEntity<Klant> updateAdres(@PathVariable int bsn, @Valid @RequestBody String adres){
        Klant klant = klantservice.updateAdres(bsn, adres);
        return ResponseEntity.ok(klant);
    }

    @DeleteMapping("{bsn}")
    public ResponseEntity<Klant> deleteKlant(@PathVariable int bsn){
        klantservice.deleteKlant(bsn);
        return ResponseEntity.noContent().build();
    }
}
