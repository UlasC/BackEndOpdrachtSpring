package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.Mankement;
import ulas1.backend.domain.dto.CreateMankementDto;
import ulas1.backend.exception.AutoHasNoMankementenException;
import ulas1.backend.exception.MankementNotFoundException;
import ulas1.backend.exception.KlantHasNoCarException;
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

        @GetMapping("bon/{mankementId}")
        public ResponseEntity<String> getBon(@PathVariable int mankementId){
            String bon = mankementService.getBon(mankementId);
            return ResponseEntity.ok(bon);
        }
    }

