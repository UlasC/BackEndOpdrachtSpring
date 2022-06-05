package ulas1.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ulas1.backend.domain.entity.Mankement;

import javax.persistence.*;
import java.util.List;

@Entity
public class OverigeHandeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int handelingsnummer;
    private double prijs;
    private String handeling;

    @ManyToMany(mappedBy = "overigeHandelingen")
    @JsonIgnore
    private List<Mankement> mankementen;

    public OverigeHandeling(){
    }

    public OverigeHandeling(int handelingsnummer, double prijs, String handeling) {
        this.handelingsnummer = handelingsnummer;
        this.prijs = prijs;
        this.handeling = handeling;
    }

    public int getHandelingsnummer() {
        return handelingsnummer;
    }

    public void setHandelingsnummer(int handelingsnummer) {
        this.handelingsnummer = handelingsnummer;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public String getHandeling() {
        return handeling;
    }

    public void setHandeling(String handeling) {
        this.handeling = handeling;
    }

    public List<Mankement> getMankementen() {
        return mankementen;
    }

    public void setMankementen(List<Mankement> mankementen) {
        this.mankementen = mankementen;
    }
}
