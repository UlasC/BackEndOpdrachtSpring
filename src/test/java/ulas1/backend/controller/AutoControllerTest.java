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
import ulas1.backend.domain.dto.CreateAutoDto;
import ulas1.backend.domain.entity.Auto;
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.exception.KlantHasNoCarException;;
import ulas1.backend.service.AutoService;
import ulas1.backend.service.JwtService;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;

@WebMvcTest(AutoController.class)
class AutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutoService mockAutoService;

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
        Auto auto = getTestAuto();

        String createAutoDtoString = getTestCreateAutoDtoString();

        Mockito.when(mockAutoService.createAuto(any(CreateAutoDto.class))).thenReturn(auto);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/autos").contentType(MediaType.APPLICATION_JSON).content(createAutoDtoString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("/autos/" + auto.getKenteken())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.MONTEUR})
    void getAutoReturnsAutoWhenKentekenExists() throws Exception {
        //Assign
        Auto auto = getTestAuto();

        Mockito.when(mockAutoService.getAutoByKenteken(anyString())).thenReturn(auto);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/autos/" + auto.getKenteken()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.kenteken",is(auto.getKenteken())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAutoReturnsAutoWhenKlantExistsAndHasCar() throws Exception {
        //Assign
        Auto auto = getTestAuto();

        Mockito.when(mockAutoService.getAutoByKlant(anyInt())).thenReturn(auto);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/autos/klant/" + auto.getKlant().getBsn()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.klant.bsn",is(auto.getKlant().getBsn())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getAutoThrowsKlantHasNoCarExceptionWhenNoCarsAreRegisteredForKlant() throws Exception {
        //Assign
        Auto auto = getTestAuto();

        Mockito.when(mockAutoService.getAutoByKlant(anyInt())).thenThrow(new KlantHasNoCarException(auto.getKlant().getFirstName(), auto.getKlant().getLastName()));

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/autos/klant/" + auto.getKlant().getBsn()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.valueOf(auto.getKlant().getFirstName()))))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.valueOf(auto.getKlant().getLastName()))));;
    }

    private Auto getTestAuto(){
        String kenteken = "12-34-5A";
        String brandstof = "Diesel";
        String merk = "BMW";
        String model = "5 serie";
        int bsn = 11223344;
        String firstName = "Jan";
        String lastName = "van Steen";

        Klant klant = new Klant();
        klant.setBsn(bsn);
        klant.setFirstName(firstName);
        klant.setLastName(lastName);

        Auto auto = new Auto();
        auto.setKenteken(kenteken);
        auto.setBrandstof(brandstof);
        auto.setMerk(merk);
        auto.setModel(model);
        auto.setKlant(klant);

        return auto;
    }

    private String getTestCreateAutoDtoString(){
        String kenteken = "12-34-5A";
        String brandstof = "Diesel";
        String merk = "BMW";
        String model = "5 serie";
        int bsn = 11223344;

        return String.format("{\n" +
                "\"kenteken\":\"%s\",\n" +
                "\"brandstof:\":\"%s\",\n" +
                "\"merk:\":\"%s\",\n" +
                "\"model:\":\"%s\",\n" +
                "\"bsn:\":%d\n" +
                "}", kenteken, brandstof, merk, model, bsn);
    }
}