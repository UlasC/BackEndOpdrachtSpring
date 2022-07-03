package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulas1.backend.domain.dto.AfspraakDto;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.service.AfspraakService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController // Dit is de controller: de eerste spring laag
@RequestMapping("/afspraken")
public class AfspraakController {

    @Autowired
    public AfspraakController(AfspraakService afspraakService) {
        this.afspraakService = afspraakService;
    }


    private AfspraakService afspraakService;


    @PostMapping
    public ResponseEntity<AfspraakDto> createAfspraak(@Valid @RequestBody CreateAfspraakDto createAfspraakDto){
        AfspraakDto afspraakDto = afspraakService.createAfspraak(createAfspraakDto);

        final URI location = URI.create("/afspraken/" + afspraakDto.getKlant().getBsn());

        return ResponseEntity.created(location).body(afspraakDto);
    }

    @GetMapping("klant/{bsn}")
    public ResponseEntity<List<AfspraakDto>> getAfspraken(@PathVariable Integer bsn){
        List<AfspraakDto> afspraakDtos = afspraakService.getAfspraken(bsn);
        if(afspraakDtos.size() > 0) {
            return ResponseEntity.ok(afspraakDtos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("medewerker/{gebruikersnaam}")
    public ResponseEntity<List<AfspraakDto>> getAfspraken(@PathVariable String gebruikersnaam){
        List<AfspraakDto> afspraakDtos = afspraakService.getAfspraken(gebruikersnaam);
        if(afspraakDtos.size() > 0) {
            return ResponseEntity.ok(afspraakDtos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
