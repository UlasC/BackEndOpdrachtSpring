package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.Handeling;
import ulas1.backend.domain.dto.UpdateVoorraadDto;
import ulas1.backend.exception.HandelingNotFoundException;
import ulas1.backend.service.HandelingService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController // Dit is de controller: spring object
@RequestMapping("/handelingen") // de path van de localHost
public class HandelingController {
    
        @Autowired
        public HandelingController(HandelingService handelingService) {
            this.handelingService = handelingService;
        }


        private HandelingService handelingService;


        @PostMapping
        public ResponseEntity<Handeling> addHandeling(@RequestBody Handeling handeling){
            handelingService.addHandeling(handeling);

            final URI location = URI.create("/handelingen/" + handeling.getHandelingsnummer());

            return ResponseEntity.created(location).body(handeling);
        }

        @GetMapping("{handelingsnummer}")
        public ResponseEntity<Handeling> getHandeling(@PathVariable int handelingsnummer){
            Optional<Handeling> handeling = handelingService.getHandelingByHandelingsnummer(handelingsnummer);
            if(handeling.isEmpty()){
                throw new HandelingNotFoundException(handelingsnummer);
            }
            return ResponseEntity.ok(handeling.get());

        }
}
