package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ulas1.backend.domain.dto.FotoUploadedDto;
import ulas1.backend.domain.entity.IdentiteitskaartFoto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.exception.KlantHeeftGeenFotosException;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.IdentiteitskaartFotoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdentiteitskaartFotoServiceTest {

    @Mock
    IdentiteitskaartFotoRepository identiteitskaartFotoRepository;

    @Mock
    KlantService klantService;

    @Mock
    MultipartFile foto;

    @InjectMocks
    IdentiteitskaartFotoService sut;

    @Test
    void uploadWithValidFotoAndValidBsnReturnsCorrectDTO() throws IOException {
        //Assign
        Integer bsn = 111222333;
        String type = "pdf";
        String filename = "identiteitskaart.pdf";

        Klant klant = getTestKlant(bsn);

        when(foto.getOriginalFilename()).thenReturn(filename);
        when(foto.getContentType()).thenReturn(type);
        when(foto.getBytes()).thenReturn(new byte[0]);

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);

        //Act
        FotoUploadedDto fotoUploadedDto = sut.upload(foto, bsn);

        //Assert
        assertEquals(fotoUploadedDto.getKlant().getBsn(), bsn);
        assertEquals(fotoUploadedDto.getName(), filename);
        assertEquals(fotoUploadedDto.getType(), type);
    }

    @Test
    void uploadWithInvalidBsnReturnsKlantNotFoundException() throws IOException {
        //Assign
        Integer bsn = 8;
        String type = "pdf";
        String filename = "identiteitskaart.pdf";

        when(foto.getOriginalFilename()).thenReturn(filename);
        when(foto.getContentType()).thenReturn(type);
        when(foto.getBytes()).thenReturn(new byte[0]);

        when(klantService.getKlantByBsn(bsn)).thenThrow(new KlantNotFoundException());

        //Act and assert
        assertThrows(KlantNotFoundException.class, () -> sut.upload(foto, bsn));
    }

    @Test
    void listAllFromKlantReturnsListOfFotoUploadedDTOsWithCorrectIDs(){
        //Assign
        Integer bsn = 555654555;
        Klant klant = getTestKlant(bsn);

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);
        when(identiteitskaartFotoRepository.findIdentiteitskaartFotosByKlant(klant)).thenReturn(
                Optional.of(List.of(
                        getTestIdentiteitskaarFoto(klant, "foto_1.pdf", 1),
                        getTestIdentiteitskaarFoto(klant, "foto_2.pdf", 2)
                ))
        );

        //Act
        List<FotoUploadedDto> fotoUploadedDtos = sut.listAllFromKlant(bsn);

        //Assert
        assertEquals(fotoUploadedDtos.get(0).getId(), 1);
        assertEquals(fotoUploadedDtos.get(0).getName(), "foto_1.pdf");
        assertEquals(fotoUploadedDtos.get(0).getKlant().getBsn(), bsn);
        assertEquals(fotoUploadedDtos.get(1).getId(), 2);
        assertEquals(fotoUploadedDtos.get(1).getName(), "foto_2.pdf");
        assertEquals(fotoUploadedDtos.get(1).getKlant().getBsn(), bsn);
    }

    @Test
    void listAllFromKlantReturnsExceptionWhenKlantHasNoFotos(){
        //Assign
        Integer bsn = 555654555;
        Klant klant = getTestKlant(bsn);

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);
        when(identiteitskaartFotoRepository.findIdentiteitskaartFotosByKlant(klant)).thenReturn(Optional.empty());

        //Act and assert
        KlantHeeftGeenFotosException exception = assertThrows(KlantHeeftGeenFotosException.class, () -> sut.listAllFromKlant(bsn));
        assertTrue(exception.getMessage().contains(klant.getFirstName()));
        assertTrue(exception.getMessage().contains(klant.getLastName()));
    }

    private Klant getTestKlant(int bsn){
        Klant klant = new Klant();
        klant.setBsn(bsn);
        klant.setFirstName("Kees");
        klant.setLastName("Van Oldenbarneveldt");
        return klant;
    }

    private IdentiteitskaartFoto getTestIdentiteitskaarFoto(Klant klant, String name, int id){
        IdentiteitskaartFoto identiteitskaartFoto = new IdentiteitskaartFoto();
        identiteitskaartFoto.setKlant(klant);
        identiteitskaartFoto.setName(name);
        identiteitskaartFoto.setType("pdf");
        identiteitskaartFoto.setData(new byte[0]);
        identiteitskaartFoto.setId(id);

        return identiteitskaartFoto;
    }
}