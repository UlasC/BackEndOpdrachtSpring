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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ulas1.backend.service.*;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

@WebMvcTest(LogInController.class)
class LogInControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    AuthenticationManager authManager;

    //De jwtService en de Datasource moeten verplicht gemockt worden in alle ControllerTest-klasses,
    // anders geeft Spring een error.
    @MockBean
    DataSource dataSource;

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
        Mockito.when(jwtService.generateToken(any(UserDetails.class), anyLong())).thenReturn(token);

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