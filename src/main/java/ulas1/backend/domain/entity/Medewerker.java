package ulas1.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Medewerker {

    public static final String BALIEMEDEWERKER = "BALIEMEDEWERKER";
    public static final String MONTEUR = "MONTEUR";
    public static final String BACKENDMEDEWERKER = "BACKENDMEDEWERKER";

    @Id
    @Column(length = 15)
    private String gebruikersnaam;
    private String wachtwoord;
    protected String role;
    private boolean enabled;

    @OneToMany(mappedBy = "medewerker")
    @JsonIgnore
    private List<Afspraak> afspraken;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Afspraak> getAfspraken() {
        return afspraken;
    }

    public void setAfspraken(List<Afspraak> afspraken) {
        this.afspraken = afspraken;
    }
}
