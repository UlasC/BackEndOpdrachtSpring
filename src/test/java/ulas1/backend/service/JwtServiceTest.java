package ulas1.backend.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import ulas1.backend.repository.AfspraakRepository;

import javax.validation.constraints.AssertTrue;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    JwtService sut;

    @Mock
    UserDetails mockUserDetails;

    @Test
    void generateTokenGeneratesValidToken() {
        //Assign
        when(mockUserDetails.getUsername()).thenReturn("Pietje123");

        //Act
        String token = sut.generateToken(mockUserDetails, System.currentTimeMillis());

        //Assert
        assertTrue(sut.validateToken(token, mockUserDetails));
    }

    @Test
    void validateTokenFailsAfterOneDay() {
        //Assign
        String username = "Pietje123";
        when(mockUserDetails.getUsername()).thenReturn("Pietje123");

        //Create a token from 24 hours and 1 second ago
        long period = 1000 * 60 * 60 * 24 + 1;
        String token = sut.generateToken(mockUserDetails, System.currentTimeMillis() - period);

        //Act and assert
        assertThrows(ExpiredJwtException.class, () -> {sut.validateToken(token, mockUserDetails);});
    }
}