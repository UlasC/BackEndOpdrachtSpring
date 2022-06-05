package ulas1.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.dto.CreateAutoDto;
import ulas1.backend.exception.AutoNotFoundException;
import ulas1.backend.exception.KlantHasNoCarException;
import ulas1.backend.service.AutoService;
import java.net.URI;
import java.util.Optional;


@RestController // Dit is de controller: spring object
@RequestMapping("/autos")
public class AutoController {

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;

    }


    private AutoService autoService;


    @PostMapping
    public ResponseEntity<Auto> createAuto(@RequestBody CreateAutoDto createAutoDto) {
        Auto auto = autoService.createAuto(createAutoDto);

        final URI location = URI.create("/autos/" + auto.getKenteken());

        return ResponseEntity.created(location).body(auto);
    }

    @GetMapping("{kenteken}")
    public ResponseEntity<Auto> getAuto(@PathVariable String kenteken) {
        Optional<Auto> auto = autoService.getAutoByKenteken(kenteken);
        if (auto.isEmpty()) {
            throw new AutoNotFoundException(kenteken);
        }
        return ResponseEntity.ok(auto.get());
    }

    @GetMapping("klant/{bsn}")
    public ResponseEntity<Auto> getAuto(@PathVariable int bsn){
        Optional<Auto> auto = autoService.getAutoByKlant(bsn);
        if (auto.isEmpty()) {
            throw new KlantHasNoCarException(bsn);
        }
        return ResponseEntity.ok(auto.get());
    }
}




