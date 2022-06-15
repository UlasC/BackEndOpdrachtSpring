package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.exception.KlantHasNoCarException;
import ulas1.backend.repository.AutoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {

    @Mock
    AutoRepository autoRepository;

    @Mock
    KlantService klantService;

    @InjectMocks
    AutoService sut;

    @Test
    void getAutoByKlantThrowsErrorIfKlantHasNoCar() {
        int bsn = 123123456;
        Klant klant = new Klant();
        klant.setBsn(bsn);

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);
        when(autoRepository.findAutoByKlant(klant)).thenReturn(Optional.empty());

        //Act and assert
        assertThrows(KlantHasNoCarException.class, () -> {sut.getAutoByKlant(bsn);});
    }
}