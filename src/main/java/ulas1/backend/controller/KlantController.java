package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.dto.CreateKlantDto;
import ulas1.backend.domain.dto.UpdateNameDto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.service.KlantService;

import javax.validation.Valid;
import java.net.URI;

@RestController // Dit is de controller: de eerste spring laag
@RequestMapping("/klanten") // de path van de localHost
public class KlantController { // class

    //Autowired: dit betekent dat Spring automatisch klanten aanmaakt
    //Klantenservice: die wordt in dit geval door Spring aangemaakt
    @Autowired
    public KlantController(KlantService klantservice) {
        this.klantservice = klantservice;
    }

    //De Klantenservice die wordt aangemaakt
    private KlantService klantservice;

    //Deze methode zorgt ervoor dat er een klant wordt aangemaakt als er een POST-request wordt gestuurd
    @PostMapping
    public ResponseEntity<Klant> createKlant(@Valid @RequestBody CreateKlantDto createKlantDto){
        Klant klant = klantservice.createKlant(createKlantDto);

        final URI location = URI.create("/klanten/" + klant.getBsn());

        return ResponseEntity.created(location).body(klant);
    }

    //Met deze methode kunnen de gegevens van een klant worden opgevraagd aan de hand van een bsn-nummer
    @GetMapping("{bsn}")
    public ResponseEntity<Klant> getKlant(@PathVariable int bsn){
        Klant klant = klantservice.getKlantByBsn(bsn);
        return ResponseEntity.ok(klant);
    }

    //Met deze methode kan de naam van een klant worden gewijzigd
    @PutMapping("{bsn}/name")
    public ResponseEntity<Klant> updateName(@PathVariable int bsn, @Valid @RequestBody UpdateNameDto updateNameDto){
        Klant klant = klantservice.updateName(bsn, updateNameDto);
        return ResponseEntity.ok(klant);
    }

    //Met deze methode kan het adres van een klant worden gewijzigd
    @PutMapping("{bsn}/adres")
    public ResponseEntity<Klant> updateAdres(@PathVariable int bsn, @Valid @RequestBody String adres){
        Klant klant = klantservice.updateAdres(bsn, adres);
        return ResponseEntity.ok(klant);
    }

    //Met deze methode kan een klant worden verwijderd uit het systeem
    @DeleteMapping("{bsn}")
    public ResponseEntity<Klant> deleteKlant(@PathVariable int bsn){
        klantservice.deleteKlant(bsn);
        return ResponseEntity.noContent().build();
    }
}
