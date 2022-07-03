package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.entity.Mankement;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.service.MankementService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController // Dit is de controller: de eerste spring laag
@RequestMapping("/mankementen")
public class MankementController {

    @Autowired
    public MankementController(MankementService mankementService) {
        this.mankementService = mankementService;
    }

    private MankementService mankementService;

    @PostMapping
    public ResponseEntity<Mankement> createMankement(@Valid @RequestBody String kenteken) {
        Mankement mankement = mankementService.createMankement(kenteken);

        final URI location = URI.create("/mankementen/" + mankement.getMankementId());

        return ResponseEntity.created(location).body(mankement);
    }

    @PostMapping("{mankementId}/onderdelen")
    public ResponseEntity<Mankement> addOnderdeeltoMankement(@PathVariable int mankementId, @Valid @RequestBody int onderdeelId){
        Mankement mankement = mankementService.addOnderdeelToMankement(mankementId, onderdeelId);
        return ResponseEntity.ok(mankement);
    }

    @PostMapping("{mankementId}/handelingen")
    public ResponseEntity<Mankement> addBestaandeHandelingtoMankement(@PathVariable int mankementId, @Valid @RequestBody int handelingsnummer){
        Mankement mankement = mankementService.addBestaandeHandelingToMankement(mankementId, handelingsnummer);
        return ResponseEntity.ok(mankement);
    }

    @PostMapping("{mankementId}/overigehandelingen")
    public ResponseEntity<Mankement> addOnderdeeltoMankement(@PathVariable int mankementId, @Valid @RequestBody CreateHandelingDto createHandelingDto){
        Mankement mankement = mankementService.addOverigeHandelingToMankement(mankementId, createHandelingDto);
        return ResponseEntity.ok(mankement);
    }

    @GetMapping("{mankementId}")
    public ResponseEntity<Mankement> getMankement(@PathVariable int mankementId) {
        Mankement mankement = mankementService.getMankementByMankementId(mankementId);
        return ResponseEntity.ok(mankement);
    }

    @GetMapping("auto/{kenteken}")
    public ResponseEntity<List <Mankement>> getMankementen(@PathVariable String kenteken){
        List<Mankement> mankement = mankementService.getMankementenByAuto(kenteken);
        return ResponseEntity.ok(mankement);
    }

    @GetMapping("{mankementId}/bon")
    public ResponseEntity<String> getBon(@PathVariable int mankementId){
        String bon = mankementService.getBon(mankementId);
        return ResponseEntity.ok(bon);
    }

    @PutMapping("{mankementId}/betalingsstatus")
    public ResponseEntity<Mankement> updateBetalingstatus(@PathVariable int mankementId, @Valid @RequestBody String betalingstatus){
        Mankement mankement = mankementService.updateBetalingstatus(mankementId, betalingstatus);
        return ResponseEntity.ok(mankement);
    }

    @PutMapping("{mankementId}/reparatiefase")
    public ResponseEntity<Mankement> updateReparatiefase(@PathVariable int mankementId, @Valid @RequestBody String reparatiefase){
        Mankement mankement = mankementService.updateReparatiefase(mankementId, reparatiefase);
        return ResponseEntity.ok(mankement);
    }

    @DeleteMapping("{mankementId}/overigehandelingen")
    public ResponseEntity<Mankement> deleteOverigeHandelingen(@PathVariable int mankementId){
        Mankement mankement = mankementService.deleteOverigeHandelingen(mankementId);
        return ResponseEntity.ok(mankement);
    }

    @DeleteMapping("{mankementId}")
    public ResponseEntity<Mankement> deleteMankement(@PathVariable int mankementId){
        mankementService.deleteMankement(mankementId);
        return ResponseEntity.noContent().build();
    }
}

