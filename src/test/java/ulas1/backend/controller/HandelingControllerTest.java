package ulas1.backend.controller;

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
import ulas1.backend.domain.entity.BestaandeHandeling;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.service.HandelingService;
import ulas1.backend.service.JwtService;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.is;

@WebMvcTest(HandelingController.class)
class HandelingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    HandelingService mockHandelingService;

    @MockBean
    JwtService jwtService;

    @MockBean
    DataSource dataSource;

    @Test
    @WithMockUser(authorities={Medewerker.BACKENDMEDEWERKER})
    void updatePrijsReturnsHandelingWithNewPrijs() throws Exception {
        //Assign
        double beginprijs = 10.00;
        double nieuweprijs = 20.00;
        BestaandeHandeling handeling_oude_prijs = getTestHandeling(beginprijs);
        BestaandeHandeling handeling_nieuwe_prijs = getTestHandeling(nieuweprijs);

        Mockito.when(mockHandelingService.updatePrijs(handeling_oude_prijs.getHandelingsnummer(),nieuweprijs)).thenReturn(handeling_nieuwe_prijs);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/handelingen/" + handeling_oude_prijs.getHandelingsnummer() + "/prijs").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(nieuweprijs)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.prijs",is(nieuweprijs)));
    }

    @Test
    @WithMockUser(authorities={Medewerker.BACKENDMEDEWERKER})
    void deleteHandelingReturnsNoContent() throws Exception {
        BestaandeHandeling handeling = getTestHandeling(10.00);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/handelingen/" + handeling.getHandelingsnummer()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private BestaandeHandeling getTestHandeling(double prijs){
        String handelingsomschrijving = "remblok vervangen";

        BestaandeHandeling handeling = new BestaandeHandeling();
        handeling.setPrijs(prijs);
        handeling.setHandeling(handelingsomschrijving);

        return handeling;
    }
}