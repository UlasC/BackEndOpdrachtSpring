package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ulas1.backend.domain.dto.FotoUploadedDto;
import ulas1.backend.domain.entity.IdentiteitskaartFoto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.service.IdentiteitskaartFotoService;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fotos")
public class IdentiteitskaartFotoController {
    private IdentiteitskaartFotoService identiteitskaartFotoService;

    @Autowired
    public IdentiteitskaartFotoController(IdentiteitskaartFotoService identiteitskaartFotoService){
        this.identiteitskaartFotoService = identiteitskaartFotoService;
    }

    @PostMapping
    public ResponseEntity<FotoUploadedDto> upload(@Valid @RequestParam("foto") MultipartFile foto, @RequestParam("bsn") int bsn) throws IOException {
        FotoUploadedDto fotoUploadedDto = identiteitskaartFotoService.upload(foto, bsn);

        final URI location = URI.create("/fotos/" + fotoUploadedDto.getId());

        return ResponseEntity.created(location).body(fotoUploadedDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> download(@PathVariable int id) throws FileNotFoundException {
        IdentiteitskaartFoto identiteitskaartFoto = identiteitskaartFotoService.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + identiteitskaartFoto.getName() + "\"")
                .body(identiteitskaartFoto.getData());
    }

    @GetMapping("klant/{bsn}")
    public ResponseEntity<List<FotoUploadedDto>> listAllFromKlant(@PathVariable int bsn){
        List<FotoUploadedDto> fotoUploadedDtos = identiteitskaartFotoService.listAllFromKlant(bsn);
        return ResponseEntity.ok(fotoUploadedDtos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<IdentiteitskaartFoto> remove(@PathVariable int id) throws FileNotFoundException{
        identiteitskaartFotoService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("klant/{bsn}")
    public ResponseEntity<Klant> removeAllFromKlant(@PathVariable int bsn){
        Klant klant = identiteitskaartFotoService.removeAllFromKlant(bsn);
        return ResponseEntity.ok(klant);
    }
}
