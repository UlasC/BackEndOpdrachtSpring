package ulas1.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Klant {
    private String firstName;
    private String lastName;
    private String adres;

    @Id
    private int bsn;

    @OneToMany
    private List<Afspraak> afspraken;

    public Klant(){

    }

    public Klant(String firstName, String lastName, String adres, int bsn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adres = adres;
        this.bsn = bsn;
    }

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
}
