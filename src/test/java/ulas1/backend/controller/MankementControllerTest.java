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
import ulas1.backend.domain.entity.*;
import ulas1.backend.service.AfspraakService;
import ulas1.backend.service.JwtService;
import ulas1.backend.service.MankementService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(MankementController.class)
class MankementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MankementService mockMankementService;

    @MockBean
    JwtService jwtService;

    @MockBean
    DataSource dataSource;

    @Test
    @WithMockUser(authorities={Medewerker.MONTEUR})
    void addBestaandeHandelingtoMankementReturnsMankementWithNewBestaandeHandeling() throws Exception {
        //Assign
        Mankement mankement = getTestMankement();
        BestaandeHandeling bestaandeHandeling = getTestBestaandeHandeling();

        Mankement mankement_met_nieuwe_handeling = getTestMankement();
        mankement_met_nieuwe_handeling.setBestaandeHandelingen(List.of(bestaandeHandeling));

        Mockito.when(mockMankementService.addBestaandeHandelingToMankement(anyInt(), anyInt())).thenReturn(mankement_met_nieuwe_handeling);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/mankementen/" + mankement.getMankementId() + "/handelingen").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(bestaandeHandeling.getHandelingsnummer())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bestaandeHandelingen[0].handelingsnummer",is(bestaandeHandeling.getHandelingsnummer())));
    }

    @Test
    @WithMockUser(authorities={Medewerker.MONTEUR})
    void deleteOverigeHandelingenReturnsMankementWithoutOverigeHandelingen() throws Exception{
        //Assign
        Mankement mankement_with_overige_handelingen = getTestMankement();
        Mankement mankement_without_overige_handelingen = getTestMankement();
        mankement_without_overige_handelingen.setOverigeHandelingen(new ArrayList<>());

        Mockito.when(mockMankementService.deleteOverigeHandelingen(mankement_with_overige_handelingen.getMankementId())).thenReturn(mankement_without_overige_handelingen);

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/mankementen/" + mankement_with_overige_handelingen.getMankementId() + "/overigehandelingen"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.overigeHandelingen",is(new ArrayList<>())));
    }

    private Mankement getTestMankement(){
        String betalingsstatus = "Niet betaald";
        String reparatiefase = "in reparatie";
        String kenteken = "12-34-5A";

        Auto auto = new Auto();
        auto.setKenteken(kenteken);

        double prijs = 200.00;
        String handelingsomschrijving = "bijzondere vloeistof vervangen";
        OverigeHandeling overigeHandeling = new OverigeHandeling();
        overigeHandeling.setPrijs(prijs);
        overigeHandeling.setHandeling(handelingsomschrijving);
        List<OverigeHandeling> overigeHandelingen = List.of(overigeHandeling);

        Mankement mankement = new Mankement();
        mankement.setBetalingstatus(betalingsstatus);
        mankement.setReparatieFase(reparatiefase);
        mankement.setAuto(auto);
        mankement.setOverigeHandelingen(overigeHandelingen);
        mankement.setBestaandeHandelingen(new ArrayList<>());
        mankement.setOnderdelen(new ArrayList<>());

        return mankement;
    }

    private BestaandeHandeling getTestBestaandeHandeling(){
        String handelingsomschrijving = "remblok vervangen";
        double prijs = 10.00;

        BestaandeHandeling handeling = new BestaandeHandeling();
        handeling.setPrijs(prijs);
        handeling.setHandeling(handelingsomschrijving);

        return handeling;
    }
}