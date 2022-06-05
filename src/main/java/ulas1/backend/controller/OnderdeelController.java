package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.BestaandeHandeling;
import ulas1.backend.domain.Klant;
import ulas1.backend.domain.Onderdeel;
import ulas1.backend.domain.dto.UpdateVoorraadDto;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.exception.OnderdeelNotFoundException;
import ulas1.backend.repository.OnderdeelRepository;
import ulas1.backend.service.KlantService;
import ulas1.backend.service.OnderdeelService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController // Dit is de controller: spring object
@RequestMapping("/onderdelen") // de path van de localHost
public class OnderdeelController {
    @Autowired
    public OnderdeelController(OnderdeelService onderdeelService) {
        this.onderdeelService = onderdeelService;
    }


    private OnderdeelService onderdeelService;


    @PostMapping
    public ResponseEntity<Onderdeel> addOnderdeel(@RequestBody Onderdeel onderdeel){
        onderdeelService.addOnderdeel(onderdeel);

        final URI location = URI.create("/onderdelen/" + onderdeel.getArtikelnummer());

        return ResponseEntity.created(location).body(onderdeel);
    }

    @GetMapping("{artikelnummer}")
    public ResponseEntity<Onderdeel> getOnderdeel(@PathVariable int artikelnummer){
        Onderdeel onderdeel = onderdeelService.getOnderdeelByArtikelnummer(artikelnummer);
        return ResponseEntity.ok(onderdeel);
    }

    @PutMapping("{artikelnummer}/voorraad")
    public ResponseEntity<Onderdeel> updateVoorraad(@PathVariable int artikelnummer, @Valid @RequestBody UpdateVoorraadDto updateVoorraadDto){
        Onderdeel onderdeel = onderdeelService.updateVoorraad(artikelnummer, updateVoorraadDto);
        return ResponseEntity.ok(onderdeel);
    }

    @PutMapping("{artikelnummer}/prijs")
    public ResponseEntity<Onderdeel> updatePrijs(@PathVariable int artikelnummer, @Valid @RequestBody double nieuweprijs){
        Onderdeel onderdeel = onderdeelService.updatePrijs(artikelnummer, nieuweprijs);
        return ResponseEntity.ok(onderdeel);
    }

    @DeleteMapping("{artikelnummer}")
    public ResponseEntity<Onderdeel> deleteOnderdeel(@PathVariable int artikelnummer){
        onderdeelService.deleteOnderdeel(artikelnummer);
        return ResponseEntity.noContent().build();
    }
}


