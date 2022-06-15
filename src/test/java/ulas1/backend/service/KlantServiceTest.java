package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.repository.KlantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KlantServiceTest {

    @Mock
    KlantRepository klantRepository;

    @Mock
    Klant mockKlant;

    @InjectMocks
    KlantService sut;

    @Test
    void getKlantByBsnReturnsKlantIfKlantExists(){
        //Assign
        Integer bsn = 111222333;
        when(klantRepository.findById(bsn)).thenReturn(Optional.of(mockKlant));

        //Act
        Klant klant = sut.getKlantByBsn(bsn);

        //Assert
        assertSame(mockKlant, klant);
    }

    @Test
    void getKlantByBsnThrowsErrorIfKlantDoesNotExist(){
        //Assign
        Integer bsn = 0;
        when(klantRepository.findById(bsn)).thenReturn(Optional.empty());

        //Act and assert
        assertThrows(KlantNotFoundException.class, () ->
        {
            sut.getKlantByBsn(bsn);
        });
    }
}