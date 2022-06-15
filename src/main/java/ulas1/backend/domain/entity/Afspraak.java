package ulas1.backend.domain.entity;

import ulas1.backend.domain.idClass.AfspraakId;

import javax.persistence.*;

@Entity
@IdClass(AfspraakId.class)
public class Afspraak {

    @Id
    @Column(length = 5)
    private String tijd;
    @Id
    private int dag;
    @Id
    private int maand;
    @Id
    private int jaar;

    private String soortAfspraak;

    @Id
    @ManyToOne // een op veel relatie
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
