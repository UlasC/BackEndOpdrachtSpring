package ulas1.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Onderdeel {

    @Id
    private int artikelnummer;
    private double prijs;
    private String name;
    private int voorraad;


    public Onderdeel(){
    }

    public Onderdeel(int artikelnummer, double prijs, String name, int voorraad) {
        this.artikelnummer = artikelnummer;
        this.prijs = prijs;
        this.name = name;
        this.voorraad = voorraad;
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

