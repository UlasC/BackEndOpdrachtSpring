package ulas1.backend.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.exception.MedewerkerNotFoundException;
import ulas1.backend.service.AfspraakService;
import ulas1.backend.service.JwtService;

import javax.sql.DataSource;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;

@WebMvcTest(AfspraakController.class)
class AfspraakControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AfspraakService mockAfspraakService;

    @MockBean
    JwtService jwtService;

    @MockBean
    DataSource dataSource;

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void createAfspraakCreatesAfspraakAndReturnsLocation() throws Exception {
        //Assign
        Afspraak afspraak = getTestAfspraak();

        String createAfspraakDtoString = getTestCreateAfspraakDtoString();

        Mockito.when(mockAfspraakService.createAfspraak(any(CreateAfspraakDto.class))).thenReturn(afspraak);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/afspraken").contentType(MediaType.APPLICATION_JSON).content(createAfspraakDtoString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("/afspraken/" + afspraak.getKlant().getBsn())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAfsprakenReturnsAfsprakenWhenBsnIsValid() throws Exception {
        //Assign
        Afspraak afspraak = getTestAfspraak();
        List<Afspraak> afspraken = List.of(afspraak);

        Mockito.when(mockAfspraakService.getAfspraken(anyInt())).thenReturn(afspraken);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/klant/" + afspraak.getKlant().getBsn()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].klant.bsn",is(afspraak.getKlant().getBsn())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAfsprakenReturnsKlantNotFoundExceptionWhenBsnIsNotValid() throws Exception {
        //Assign
        int bsn = 9;

        Mockito.when(mockAfspraakService.getAfspraken(anyInt())).thenThrow(new KlantNotFoundException());

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/klant/" + bsn))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAfsprakenReturnsAfsprakenWhenGebruikersnaamIsValid() throws Exception {
        //Assign
        Afspraak afspraak = getTestAfspraak();
        List<Afspraak> afspraken = List.of(afspraak);

        Mockito.when(mockAfspraakService.getAfspraken(anyString())).thenReturn(afspraken);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/medewerker/" + afspraak.getMedewerker().getGebruikersnaam()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medewerker.gebruikersnaam",is(afspraak.getMedewerker().getGebruikersnaam())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAfsprakenReturnsMedewerkerNotFoundExceptionWhenGebruikersnaamIsNotValid() throws Exception {
        //Assign
        String gebruikersnaam = "Klaas";

        Mockito.when(mockAfspraakService.getAfspraken(anyString())).thenThrow(new MedewerkerNotFoundException(gebruikersnaam));

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/medewerker/" + gebruikersnaam))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(gebruikersnaam)));
    }

    @Test
    void getAfsprakenReturnsErrorWhenUnauthorized() throws Exception {
        //Assign
        Afspraak afspraak = getTestAfspraak();
        List<Afspraak> afspraken = List.of(afspraak);

        Mockito.when(mockAfspraakService.getAfspraken(anyString())).thenReturn(afspraken);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/medewerker/" + afspraak.getMedewerker().getGebruikersnaam()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private Afspraak getTestAfspraak(){
        String tijd = "10:00";
        int dag = 1;
        int maand = 2;
        int jaar = 2030;
        String soortAfspraak = "onderzoek";
        int bsn = 111222666;
        String gebruikersnaam = "Ali";

        Klant klant = new Klant();
        klant.setBsn(bsn);

        Medewerker medewerker = new Medewerker();
        medewerker.setGebruikersnaam(gebruikersnaam);

        Afspraak afspraak = new Afspraak();
        afspraak.setTijd(tijd);
        afspraak.setDag(dag);
        afspraak.setMaand(maand);
        afspraak.setJaar(jaar);
        afspraak.setSoortAfspraak(soortAfspraak);
        afspraak.setKlant(klant);
        afspraak.setMedewerker(medewerker);

        return afspraak;
    }

    private String getTestCreateAfspraakDtoString(){
        String tijd = "10:00";
        int dag = 1;
        int maand = 2;
        int jaar = 2030;
        String soortAfspraak = "onderzoek";
        int bsn = 111222666;
        String gebruikersnaam = "Ali";

        return String.format("{\n" +
                "\"tijd\":\"%s\",\n" +
                "\"dag:\":%d,\n" +
                "\"maand:\":%d,\n" +
                "\"jaar:\":%d,\n" +
                "\"soortAfspraak:\":\"%s\",\n" +
                "\"bsn:\":%d,\n" +
                "\"gebruikersnaam:\":\"%s\"\n" +
                "}", tijd, dag, maand, jaar, soortAfspraak, bsn, gebruikersnaam);
    }
}