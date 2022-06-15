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
import ulas1.backend.domain.entity.Klant;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.exception.KlantNotFoundException;
import ulas1.backend.service.JwtService;
import ulas1.backend.service.KlantService;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.hamcrest.Matchers.is;

@WebMvcTest(KlantController.class)
class KlantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    KlantService mockKlantService;

    @MockBean
    JwtService jwtService;

    @MockBean
    DataSource dataSource;

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void createKlantCreatesKlantAndReturnsLocation() throws Exception {
        //Assign
        Klant klant = getTestKlant();
        String klantString = getTestKlantString();

        Mockito.when(mockKlantService.createKlant(any(Klant.class))).thenReturn(klant);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/klanten").contentType(MediaType.APPLICATION_JSON).content(klantString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location",Matchers.containsString("/klanten/" + klant.getBsn())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.MONTEUR})
    void createKlantFailsWhenNotExecutedByBaliemedewerker() throws Exception {
        //Assign
        Klant klant = getTestKlant();
        String klantString = getTestKlantString();

        Mockito.when(mockKlantService.createKlant(any(Klant.class))).thenReturn(klant);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/klanten").contentType(MediaType.APPLICATION_JSON).content(klantString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getKlantWithValidBsnReturnsKlant() throws Exception {
        //Assign
        Klant klant = getTestKlant();

        Mockito.when(mockKlantService.getKlantByBsn(anyInt())).thenReturn(klant);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/klanten/" + klant.getBsn()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bsn",is(klant.getBsn())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BALIEMEDEWERKER})
    void getKlantWithoutValidBsnReturnsKlantNotFoundException() throws Exception {
        //Assign
        int bsn = 9;

        Mockito.when(mockKlantService.getKlantByBsn(anyInt())).thenThrow(new KlantNotFoundException(bsn));

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/klanten/" + bsn))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.valueOf(bsn))));
    }

    private Klant getTestKlant(){
        int bsn = 111222666;
        String firstName = "Zwarte";
        String lastName = "Magie";
        String adres = "Magiestraat 42Z";

        Klant klant = new Klant();
        klant.setBsn(bsn);
        klant.setFirstName(firstName);
        klant.setLastName(lastName);
        klant.setAdres(adres);

        return klant;
    }

    private String getTestKlantString(){
        int bsn = 111222666;
        String firstName = "Zwarte";
        String lastName = "Magie";
        String adres = "Magiestraat 42Z";

        return String.format("{\n" +
                "\"bsn\":%d,\n" +
                "\"firstName:\":\"%s\",\n" +
                "\"lastName:\":\"%s\",\n" +
                "\"adres:\":\"%s\"\n" +
                "}",bsn,firstName,lastName,adres);
    }
}