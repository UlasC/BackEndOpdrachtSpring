package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ulas1.backend.domain.dto.FotoUploadedDto;
import ulas1.backend.domain.entity.IdentiteitskaartFoto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.exception.KlantHeeftGeenFotosException;
import ulas1.backend.repository.IdentiteitskaartFotoRepository;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdentiteitskaartFotoService {

    private IdentiteitskaartFotoRepository identiteitskaartFotoRepository;
    private KlantService klantService;

    @Autowired
    public IdentiteitskaartFotoService(IdentiteitskaartFotoRepository identiteitskaartFotoRepository, KlantService klantService){
        this.identiteitskaartFotoRepository = identiteitskaartFotoRepository;
        this.klantService = klantService;
    }

    public FotoUploadedDto upload(MultipartFile foto, int bsn) throws IOException {
        IdentiteitskaartFoto identiteitskaartFoto = new IdentiteitskaartFoto();
        identiteitskaartFoto.setName(foto.getOriginalFilename());
        identiteitskaartFoto.setType(foto.getContentType());
        identiteitskaartFoto.setData(foto.getBytes());

        Klant klant = klantService.getKlantByBsn(bsn);
        identiteitskaartFoto.setKlant(klant);

        identiteitskaartFotoRepository.save(identiteitskaartFoto);
        return FotoUploadedDto.from(identiteitskaartFoto);
    }

    public IdentiteitskaartFoto download(int id) throws FileNotFoundException {
        Optional<IdentiteitskaartFoto> identiteitskaartFoto = identiteitskaartFotoRepository.findById(id);
        if(identiteitskaartFoto.isEmpty()){
            throw new FileNotFoundException();
        }
        return identiteitskaartFoto.get();
    }

    @Transactional
    public List<FotoUploadedDto> listAllFromKlant(int bsn){
        Klant klant= klantService.getKlantByBsn(bsn);
        Optional<List<IdentiteitskaartFoto>> identiteitskaartFotos  = identiteitskaartFotoRepository.findIdentiteitskaartFotosByKlant(klant);
        if(identiteitskaartFotos.isEmpty()){
            throw new KlantHeeftGeenFotosException(klant.getFirstName(), klant.getLastName());
        }
        return identiteitskaartFotos.get().stream().map(FotoUploadedDto::from).collect(Collectors.toList());
    }

    public void remove(int id) throws FileNotFoundException{
        IdentiteitskaartFoto identiteitskaartFoto = download(id);
        identiteitskaartFotoRepository.delete(identiteitskaartFoto);
    }

    @Transactional
    public Klant removeAllFromKlant(int bsn){
        Klant klant= klantService.getKlantByBsn(bsn);
        Optional<List<IdentiteitskaartFoto>> identiteitskaartFotos  = identiteitskaartFotoRepository.findIdentiteitskaartFotosByKlant(klant);
        if(identiteitskaartFotos.isEmpty()){
            throw new KlantHeeftGeenFotosException(klant.getFirstName(), klant.getLastName());
        }
        for(IdentiteitskaartFoto id_foto:identiteitskaartFotos.get()){
            identiteitskaartFotoRepository.delete(id_foto);
        }
        return klantService.getKlantByBsn(bsn);
    }
}
