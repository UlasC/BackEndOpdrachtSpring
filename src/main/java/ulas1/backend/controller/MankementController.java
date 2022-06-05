package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.entity.Mankement;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.domain.dto.CreateMankementDto;
import ulas1.backend.exception.AutoHasNoMankementenException;
import ulas1.backend.service.MankementService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController // Dit is de controller: spring object
@RequestMapping("/mankementen")
public class MankementController {
    

        @Autowired
        public MankementController(MankementService mankementService) {
            this.mankementService = mankementService;

        }

        private MankementService mankementService;

        @PostMapping
        public ResponseEntity<Mankement> createMankement(@RequestBody CreateMankementDto createMankementDto) {
            Mankement mankement = mankementService.createMankement(createMankementDto);

            final URI location = URI.create("/mankementen/" + mankement.getMankementId());

            return ResponseEntity.created(location).body(mankement);
        }

        @PostMapping("{mankementId}/onderdeel")
        public ResponseEntity<Mankement> addOnderdeeltoMankement(@PathVariable int mankementId, @RequestBody int onderdeelId){
            Mankement mankement = mankementService.addOnderdeelToMankement(mankementId, onderdeelId);
            return ResponseEntity.ok(mankement);
        }

        @PostMapping("{mankementId}/handeling")
        public ResponseEntity<Mankement> addBestaandeHandelingtoMankement(@PathVariable int mankementId, @RequestBody int handelingsnummer){
            Mankement mankement = mankementService.addBestaandeHandelingToMankement(mankementId, handelingsnummer);
            return ResponseEntity.ok(mankement);
        }

        @PostMapping("{mankementId}/overigehandeling")
        public ResponseEntity<Mankement> addOnderdeeltoMankement(@PathVariable int mankementId, @RequestBody CreateHandelingDto createHandelingDto){
            Mankement mankement = mankementService.addOverigeHandelingToMankement(mankementId, createHandelingDto);
            return ResponseEntity.ok(mankement);
        }

        @DeleteMapping("{mankementId}/overigehandeling")
        public ResponseEntity<Mankement> deleteOverigeHandelingen(@PathVariable int mankementId){
            Mankement mankement = mankementService.deleteOverigeHandelingen(mankementId);
            return ResponseEntity.ok(mankement);
        }

        @DeleteMapping("{mankementId}")
        public ResponseEntity<Mankement> deleteMankement(@PathVariable int mankementId){
            mankementService.deleteMankement(mankementId);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("{mankementId}")
        public ResponseEntity<Mankement> getMankement(@PathVariable int mankementId) {
            Mankement mankement = mankementService.getMankementByMankementId(mankementId);
            return ResponseEntity.ok(mankement);
        }

        @GetMapping("auto/{kenteken}")
        public ResponseEntity<List <Mankement>> getMankementen(@PathVariable String kenteken){
            Optional<List<Mankement>> mankement = mankementService.getMankementenByAuto(kenteken);
            if (mankement.isEmpty()) {
                throw new AutoHasNoMankementenException(kenteken);
            }
            return ResponseEntity.ok(mankement.get());
        }

        @GetMapping("{mankementId}/bon")
        public ResponseEntity<String> getBon(@PathVariable int mankementId){
            String bon = mankementService.getBon(mankementId);
            return ResponseEntity.ok(bon);
        }
    }

