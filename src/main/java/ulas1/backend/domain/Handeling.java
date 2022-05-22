package ulas1.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Handeling {

    // -	Handelingen vaste handelingen: handelingnummer, omschrijving, prijs
    @Id
    private int handelingsnummer;
    private double prijs;
    private String handeling;


    public Handeling(){
    }

    public Handeling(int handelingsnummer, double prijs, String handeling) {
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
}
