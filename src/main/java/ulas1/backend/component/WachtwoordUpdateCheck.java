package ulas1.backend.component;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WachtwoordUpdateCheck {

    //Deze methode wordt aangeroepen in SecSecurityConfig bij het wijzigen van een wachtwoord
    //De methode checkt of de de request om het wachtwoord te wijzigen wordt aangeroepen
    // door dezelfde gebruiker als de gebruiker van wie het wachtwoord wordt gewijzigd
    public boolean controleerGebruikersnaam(Authentication authentication, String gebruikersnaam){
        return authentication.getName().equals(gebruikersnaam);
    }
}
