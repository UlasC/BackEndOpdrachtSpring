package ulas1.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Auto {
    @Id
    @Column(length = 10)
    private String kenteken;
    private String model;
    private String merk;
    private String brandstof;

    @OneToOne
    private Klant klant;

    @OneToMany(mappedBy = "auto")
    @JsonIgnore
    private List<Mankement> mankementen;

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

    public List<Mankement> getMankementen() {
        return mankementen;
    }

    public void setMankementen(List<Mankement> mankementen) {
        this.mankementen = mankementen;
    }
}
