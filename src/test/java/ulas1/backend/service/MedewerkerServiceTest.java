package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulas1.backend.domain.dto.MedewerkerCreatedDto;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.repository.AfspraakRepository;
import ulas1.backend.repository.MedewerkerRepository;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedewerkerServiceTest {

    @Mock
    MedewerkerRepository medewerkerRepository;

    @InjectMocks
    MedewerkerService sut;

    @Test
    //testing to make sure updateWachtwoord returns null and not the password
    void updateWachtwoordReturnsNull() throws NoSuchMethodException {
        //Assign
        Class med_serv_class = sut.getClass();
        Method updateWachtwoord = med_serv_class.getDeclaredMethod("updateWachtwoord", String.class, String.class);

        //Assert
        assertTrue(updateWachtwoord.getReturnType() == Void.TYPE);
    }

    @Test
    void updateRoleReturnsDTOWithNewRole() {
        //Assign
        String role = Medewerker.MONTEUR;

        String gebruikersnaam = "Mark_rutte";
        Medewerker medewerker = new Medewerker();
        medewerker.setGebruikersnaam(gebruikersnaam);

        when(medewerkerRepository.findById(gebruikersnaam)).thenReturn(Optional.of(medewerker));

        //Act
        MedewerkerCreatedDto medewerkerCreatedDto = sut.updateRole(gebruikersnaam, role);

        //Assert
        assertEquals(medewerkerCreatedDto.getRole(), role);
    }
}