package ulas1.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.dto.CreateAutoDto;
import ulas1.backend.service.AutoService;

import javax.validation.Valid;
import java.net.URI;


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
        Auto auto = autoService.getAutoByKenteken(kenteken);
        return ResponseEntity.ok(auto);
    }

    @GetMapping("klant/{bsn}")
    public ResponseEntity<Auto> getAuto(@PathVariable int bsn){
        Auto auto = autoService.getAutoByKlant(bsn);
        return ResponseEntity.ok(auto);
    }

    @PutMapping("{kenteken}/model")
    public ResponseEntity<Auto> updateModel(@PathVariable String kenteken, @Valid @RequestBody String model){
        Auto auto = autoService.updateModel(kenteken, model);
        return ResponseEntity.ok(auto);
    }

    @PutMapping("{kenteken}/merk")
    public ResponseEntity<Auto> updateMerk(@PathVariable String kenteken, @Valid @RequestBody String merk){
        Auto auto = autoService.updateMerk(kenteken, merk);
        return ResponseEntity.ok(auto);
    }

    @PutMapping("{kenteken}/brandstof")
    public ResponseEntity<Auto> updateBrandstof(@PathVariable String kenteken, @Valid @RequestBody String brandstof){
        Auto auto = autoService.updateBrandstof(kenteken, brandstof);
        return ResponseEntity.ok(auto);
    }

    @DeleteMapping("{kenteken}")
    public ResponseEntity<Auto> deleteAuto(@PathVariable String kenteken){
        autoService.deleteAuto(kenteken);
        return ResponseEntity.noContent().build();
    }
}




