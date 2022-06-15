package ulas1.backend.component;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WachtwoordUpdateCheck {
    public boolean controleerGebruikersnaam(Authentication authentication, String gebruikersnaam){
        return authentication.getName().equals(gebruikersnaam);
    }
}
