package ulas1.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ulas1.backend.domain.dto.LogInDto;
import ulas1.backend.service.JwtService;

import javax.validation.Valid;

@RestController
public class LogInController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@Valid @RequestBody LogInDto logInDto){
        UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(logInDto.getGebruikersnaam(), logInDto.getWachtwoord());
        Authentication auth = authManager.authenticate(up);

        UserDetails ud = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(ud, System.currentTimeMillis());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(token); //Als het inloggen geslaagd is, geef dan een token terug om toekomstige requests mee te valideren
    }
}
