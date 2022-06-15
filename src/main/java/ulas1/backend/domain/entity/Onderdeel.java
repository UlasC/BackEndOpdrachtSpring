package ulas1.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Onderdeel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artikelnummer;
    private double prijs;
    private String name;
    private int voorraad;

    @ManyToMany(mappedBy = "onderdelen")
    @JsonIgnore
    private List<Mankement> mankementen;

    public List<Mankement> getMankementen() {
        return mankementen;
    }

    public void setMankementen(List<Mankement> mankementen) {
        this.mankementen = mankementen;
    }

    public int getArtikelnummer() {
        return artikelnummer;
    }

    public void setArtikelnummer(int artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }

    public int updateVoorraad(int verschil){
        this.voorraad = Math.max(0, this.voorraad + verschil);
        return this.voorraad;
    }
}

