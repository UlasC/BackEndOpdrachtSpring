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
import ulas1.backend.domain.dto.AfspraakDto;
import ulas1.backend.domain.dto.CreateAfspraakDto;
import ulas1.backend.domain.dto.MedewerkerCreatedDto;
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

    //De jwtService en de Datasource moeten verplicht gemockt worden in alle ControllerTest-klasses,
    // anders geeft Spring een error.
    @MockBean
    JwtService jwtService;

    @MockBean
    DataSource dataSource;

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void createAfspraakCreatesAfspraakAndReturnsLocation() throws Exception {
        //Assign
        AfspraakDto afspraakDto = getTestAfspraakDto();

        String createAfspraakDtoString = getTestCreateAfspraakDtoString();

        Mockito.when(mockAfspraakService.createAfspraak(any(CreateAfspraakDto.class))).thenReturn(afspraakDto);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/afspraken").contentType(MediaType.APPLICATION_JSON).content(createAfspraakDtoString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("/afspraken/" + afspraakDto.getKlant().getBsn())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAfsprakenReturnsAfsprakenWhenBsnIsValid() throws Exception {
        //Assign
        AfspraakDto afspraakDto = getTestAfspraakDto();
        List<AfspraakDto> afspraakDtos = List.of(afspraakDto);

        Mockito.when(mockAfspraakService.getAfspraken(anyInt())).thenReturn(afspraakDtos);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/klant/" + afspraakDto.getKlant().getBsn()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].klant.bsn",is(afspraakDto.getKlant().getBsn())));
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
        AfspraakDto afspraakDto = getTestAfspraakDto();
        List<AfspraakDto> afspraakDtos = List.of(afspraakDto);

        Mockito.when(mockAfspraakService.getAfspraken(anyString())).thenReturn(afspraakDtos);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/medewerker/" + afspraakDto.getMedewerkerCreatedDto().getGebruikersnaam()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medewerkerCreatedDto.gebruikersnaam",is(afspraakDto.getMedewerkerCreatedDto().getGebruikersnaam())));
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
        AfspraakDto afspraakDto = getTestAfspraakDto();
        List<AfspraakDto> afspraakDtos = List.of(afspraakDto);

        Mockito.when(mockAfspraakService.getAfspraken(anyString())).thenReturn(afspraakDtos);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/afspraken/medewerker/" + afspraakDto.getMedewerkerCreatedDto().getGebruikersnaam()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private AfspraakDto getTestAfspraakDto(){
        String tijd = "10:00";
        int dag = 1;
        int maand = 2;
        int jaar = 2030;
        String soortAfspraak = "onderzoek";
        int bsn = 111222666;
        String gebruikersnaam = "Ali";

        Klant klant = new Klant();
        klant.setBsn(bsn);

        MedewerkerCreatedDto medewerkerCreatedDto = new MedewerkerCreatedDto();
        medewerkerCreatedDto.setGebruikersnaam(gebruikersnaam);

        AfspraakDto afspraakDto = new AfspraakDto();
        afspraakDto.setTijd(tijd);
        afspraakDto.setDag(dag);
        afspraakDto.setMaand(maand);
        afspraakDto.setJaar(jaar);
        afspraakDto.setSoortAfspraak(soortAfspraak);
        afspraakDto.setKlant(klant);
        afspraakDto.setMedewerkerCreatedDto(medewerkerCreatedDto);

        return afspraakDto;
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