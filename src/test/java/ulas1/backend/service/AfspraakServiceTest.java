package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.exception.MedewerkerHeeftAlAfspraakException;
import ulas1.backend.repository.AfspraakRepository;
import ulas1.backend.repository.KlantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AfspraakServiceTest {

    @Mock
    AfspraakRepository afspraakRepository;

    @Mock
    KlantService klantService;

    @Mock
    MedewerkerService medewerkerService;

    @InjectMocks
    AfspraakService sut;

    @Test
    void createAfspraakReturnsKlantWithCorrectBsn() {
        //Assign
        int bsn = 111222333;
        Klant klant = new Klant();
        klant.setBsn(bsn);

        String gebruikersnaam = "Jan_molenaar";
        Medewerker medewerker = new Medewerker();
        medewerker.setGebruikersnaam(gebruikersnaam);

        CreateAfspraakDto createAfspraakDto = getTestCreateAfspraakDto(bsn, gebruikersnaam, 1, 2, 2030, "10:00");

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);
        when(medewerkerService.getMedewerkerByGebruikersnaam(gebruikersnaam)).thenReturn(medewerker);
        when(afspraakRepository.findAfsprakenByKlant(klant)).thenReturn(Optional.empty());
        when(afspraakRepository.findAfsprakenByMedewerker(medewerker)).thenReturn(Optional.empty());

        //Act
        Afspraak afspraak = sut.createAfspraak(createAfspraakDto);

        //Assert
        assertEquals(afspraak.getKlant().getBsn(), bsn);
    }

    @Test
    void createAfspraakReturnsMedewerkerWithCorrectGebruikersnaam() {
        //Assign
        int bsn = 111222333;
        Klant klant = new Klant();
        klant.setBsn(bsn);

        String gebruikersnaam = "Jan_molenaar";
        Medewerker medewerker = new Medewerker();
        medewerker.setGebruikersnaam(gebruikersnaam);

        CreateAfspraakDto createAfspraakDto = getTestCreateAfspraakDto(bsn, gebruikersnaam, 1, 2, 2030, "10:00");

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);
        when(medewerkerService.getMedewerkerByGebruikersnaam(gebruikersnaam)).thenReturn(medewerker);
        when(afspraakRepository.findAfsprakenByKlant(klant)).thenReturn(Optional.empty());
        when(afspraakRepository.findAfsprakenByMedewerker(medewerker)).thenReturn(Optional.empty());

        //Act
        Afspraak afspraak = sut.createAfspraak(createAfspraakDto);

        //Assert
        assertSame(afspraak.getMedewerker().getGebruikersnaam(), gebruikersnaam);
    }

    @Test
    void createAfspraakThrowsErrorIfMedewerkerAlreadyHasAfspraak(){
        int dag = 25;
        int maand = 5;
        int jaar = 2025;
        String tijd = "17:00";

        int bsn = 111222333;
        Klant klant = new Klant();
        klant.setBsn(bsn);

        String gebruikersnaam = "Jan_molenaar";
        Medewerker medewerker = new Medewerker();
        medewerker.setGebruikersnaam(gebruikersnaam);

        Afspraak afspraak_al_in_systeem = new Afspraak();
        afspraak_al_in_systeem.setDag(dag);
        afspraak_al_in_systeem.setMaand(maand);
        afspraak_al_in_systeem.setJaar(jaar);
        afspraak_al_in_systeem.setTijd(tijd);
        afspraak_al_in_systeem.setSoortAfspraak("onderzoek");
        afspraak_al_in_systeem.setKlant(klant);
        afspraak_al_in_systeem.setMedewerker(medewerker);

        CreateAfspraakDto createAfspraakDto = getTestCreateAfspraakDto(bsn, gebruikersnaam, dag, maand, jaar, tijd);

        when(klantService.getKlantByBsn(bsn)).thenReturn(klant);
        when(medewerkerService.getMedewerkerByGebruikersnaam(gebruikersnaam)).thenReturn(medewerker);
        when(afspraakRepository.findAfsprakenByKlant(klant)).thenReturn(Optional.empty());
        when(afspraakRepository.findAfsprakenByMedewerker(medewerker)).thenReturn(Optional.of(List.of(afspraak_al_in_systeem)));

        //Act and assert
        assertThrows(MedewerkerHeeftAlAfspraakException.class, () -> {sut.createAfspraak(createAfspraakDto);});
    }

    private CreateAfspraakDto getTestCreateAfspraakDto(int bsn, String gebruikersnaam, int dag, int maand, int jaar, String tijd) {
        CreateAfspraakDto createAfspraakDto = new CreateAfspraakDto();
        createAfspraakDto.setDag(dag);
        createAfspraakDto.setMaand(maand);
        createAfspraakDto.setJaar(jaar);
        createAfspraakDto.setTijd(tijd);
        createAfspraakDto.setSoortAfspraak("reparatie");
        createAfspraakDto.setBsn(bsn);
        createAfspraakDto.setGebruikersnaam(gebruikersnaam);

        return createAfspraakDto;
    }
}