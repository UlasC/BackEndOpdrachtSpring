package ulas1.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Klant {
    @Column(length = 15)
    private String firstName;
    @Column(length = 25)
    private String lastName;
    private String adres;

    @Id
    private int bsn;

    @OneToMany(mappedBy = "klant")
    @JsonIgnore
    private List<Afspraak> afspraken;

    @OneToOne(mappedBy = "klant")
    @JsonIgnore
    private Auto auto;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

    public List<Afspraak> getAfspraken() {
        return afspraken;
    }

    public void setAfspraken(List<Afspraak> afspraken) {
        this.afspraken = afspraken;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
}
