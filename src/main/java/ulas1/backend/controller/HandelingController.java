package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.entity.BestaandeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.service.HandelingService;

import javax.validation.Valid;
import java.net.URI;

@RestController // Dit is de controller: de eerste spring laag
@RequestMapping("/handelingen") // de path van de localHost
public class HandelingController {

    @Autowired
    public HandelingController(HandelingService handelingService) {
        this.handelingService = handelingService;
    }

    private HandelingService handelingService;

    @PostMapping
    public ResponseEntity<BestaandeHandeling> addHandeling(@Valid @RequestBody CreateHandelingDto createHandelingDto){
        BestaandeHandeling handeling = handelingService.addHandeling(createHandelingDto);

        final URI location = URI.create("/handelingen/" + handeling.getHandelingsnummer());

        return ResponseEntity.created(location).body(handeling);
    }

    @GetMapping("{handelingsnummer}")
    public ResponseEntity<BestaandeHandeling> getHandeling(@PathVariable int handelingsnummer){
        BestaandeHandeling handeling = handelingService.getHandelingByHandelingsnummer(handelingsnummer);
        return ResponseEntity.ok(handeling);
    }

    @PutMapping("{handelingsnummer}/prijs")
    public ResponseEntity<BestaandeHandeling> updatePrijs(@PathVariable int handelingsnummer, @Valid @RequestBody double nieuweprijs){
        BestaandeHandeling handeling = handelingService.updatePrijs(handelingsnummer, nieuweprijs);
        return ResponseEntity.ok(handeling);
    }

    @DeleteMapping("{handelingsnummer}")
    public ResponseEntity<BestaandeHandeling> deleteHandeling(@PathVariable int handelingsnummer){
        handelingService.deleteHandeling(handelingsnummer);
        return ResponseEntity.noContent().build();
    }
}
