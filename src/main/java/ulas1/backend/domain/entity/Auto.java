package ulas1.backend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Auto {
    @Id
    private String kenteken;
    private String model;
    private String merk;
    private String brandstof;
    @OneToOne
    private Klant klant;


    public Auto(){

    }

    public Auto(String kenteken, String model, String merk, String brandstof, Klant klant) {
        this.kenteken = kenteken;
        this.model = model;
        this.merk = merk;
        this.brandstof = brandstof;
        this.klant = klant;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getBrandstof() {
        return brandstof;
    }

    public void setBrandstof(String brandstof) {
        this.brandstof = brandstof;
    }

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }
}
