package ulas1.backend.domain.dto;

import ulas1.backend.domain.entity.Medewerker;

public class MedewerkerCreatedDto {
    private String gebruikersnaam;
    private String role;
    private boolean enabled;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
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

    public static MedewerkerCreatedDto from(Medewerker medewerker){
        MedewerkerCreatedDto medewerkerCreatedDto = new MedewerkerCreatedDto();
        medewerkerCreatedDto.setGebruikersnaam(medewerker.getGebruikersnaam());
        medewerkerCreatedDto.setRole(medewerker.getRole());
        medewerkerCreatedDto.setEnabled(medewerker.isEnabled());

        return medewerkerCreatedDto;
    }
}
