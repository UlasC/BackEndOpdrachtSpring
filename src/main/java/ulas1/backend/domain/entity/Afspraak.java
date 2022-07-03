package ulas1.backend.domain.entity;

import ulas1.backend.domain.idClass.AfspraakId;

import javax.persistence.*;

@Entity
@IdClass(AfspraakId.class) //Deze tabel gebruikt een samengestelde primaire sleutel op basis van de velden in AfspraakId
public class Afspraak {

    @Id
    @Column(length = 5) //Tijd is een String in de vorm "00:00" dus deze mag niet langer zijn dan 5 tekens
    private String tijd;
    @Id
    private int dag;
    @Id
    private int maand;
    @Id
    private int jaar;

    private String soortAfspraak;

    @Id
    @ManyToOne // Een een op veel relatie met Klant
    private Klant klant;

    @ManyToOne
    private Medewerker medewerker;

    public String getSoortAfspraak() {
        return soortAfspraak;
    }

    public void setSoortAfspraak(String soortAfspraak) {
        this.soortAfspraak = soortAfspraak;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public int getDag() {
        return dag;
    }

    public void setDag(int dag) {
        this.dag = dag;
    }

    public int getMaand() {
        return maand;
    }

    public void setMaand(int maand) {
        this.maand = maand;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public Medewerker getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
    }
}
