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
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.domain.entity.Onderdeel;
import ulas1.backend.service.JwtService;
import ulas1.backend.service.OnderdeelService;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.is;

@WebMvcTest(OnderdeelController.class)
class OnderdeelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OnderdeelService mockOnderdeelService;

    @MockBean
    JwtService jwtService;

    @MockBean
    DataSource dataSource;

    @Test
    @WithMockUser(authorities={Medewerker.BACKENDMEDEWERKER})
    void updateVoorraadReturnsOnderdeelWithNewVoorraad() throws Exception{
        //Assign
        int oudevoorraad = 3;
        int verschil = 1;
        int nieuwevoorraad = oudevoorraad - verschil;
        Onderdeel onderdeel_oude_voorraad = getTestOnderdeel(oudevoorraad);
        Onderdeel onderdeel_nieuwe_voorraad = getTestOnderdeel(nieuwevoorraad);

        Mockito.when(mockOnderdeelService.updateVoorraad(onderdeel_oude_voorraad.getArtikelnummer(),verschil)).thenReturn(onderdeel_nieuwe_voorraad);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/onderdelen/" + onderdeel_oude_voorraad.getArtikelnummer() + "/voorraad").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(verschil)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.voorraad",is(nieuwevoorraad)));
    }

    private Onderdeel getTestOnderdeel(int voorraad){
        double prijs = 5.00;
        String name = "vloeistof";

        Onderdeel onderdeel = new Onderdeel();
        onderdeel.setPrijs(prijs);
        onderdeel.setName(name);
        onderdeel.setVoorraad(voorraad);

        return onderdeel;
    }
}