package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulas1.backend.domain.entity.Mankement;
import ulas1.backend.repository.MankementRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MankementServiceTest {

    @Mock
    MankementRepository mankementRepository;

    @InjectMocks
    MankementService sut;

    @Test
    void updateBetalingstatusReturnsMankementWithNewBetalingstatus() {
        //Assign
        String betalingstatus = "Niet betaald";

        Mankement mankement = new Mankement();
        mankement.setMankementId(10);
        mankement.setBetalingstatus("Betaald");

        when(mankementRepository.findById(10)).thenReturn(Optional.of(mankement));

        //Act
        Mankement updated_mankement = sut.updateBetalingstatus(10, betalingstatus);

        //Assert
        assertEquals(updated_mankement.getBetalingstatus(), betalingstatus);

    }
}