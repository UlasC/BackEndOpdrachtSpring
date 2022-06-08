package ulas1.backend.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ulas1.backend.domain.dto.LogInDto;
import ulas1.backend.service.*;

import javax.sql.DataSource;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@WebMvcTest
class LogInControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    AuthenticationManager authManager;

    @MockBean
    DataSource dataSource;

    @MockBean
    AfspraakService afspraakService;

    @MockBean
    AutoService autoService;

    @MockBean
    HandelingService handelingService;

    @MockBean
    KlantService klantService;

    @MockBean
    MankementService mankementService;

    @MockBean
    MedewerkerService medewerkerService;

    @MockBean
    OnderdeelService onderdeelService;

    @MockBean
    OverigeHandelingService overigeHandelingService;

    @Test
    void correctLogInShouldReturnStatusOKandToken() throws Exception{
        //Assign
        String gebruikersnaam = "Johnny";
        String wachtwoord = "welkom";
        String token = "test token";

        Authentication auth = mock(Authentication.class);
        UserDetails ud = mock(UserDetails.class);

        Mockito.when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn(ud);
        Mockito.when(jwtService.generateToken(ud)).thenReturn(token);

        String LogInBody = "{\n" +
                "\"gebruikersnaam\":\"Johnny\",\n" +
                "\"wachtwoord:\":\"welkom\"\n" +
                "}";

        //Act and assert
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON).content(LogInBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(token)));
    }

}