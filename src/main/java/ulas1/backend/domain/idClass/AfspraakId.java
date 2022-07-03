package ulas1.backend.domain.idClass;

import java.io.Serializable;

//Deze klasse beschrijft welke velden samen de primaire sleutel
// van de afspraken-tabel omvatten
public class AfspraakId implements Serializable {

    private int jaar;

    private int dag;

    private int maand;

    //De naam van de variabele moet hetzelfde zijn als de naam van de variabele in de afspraken-klasse.
    //Het type van de variabele daarentegen moet hetzelfde zijn als het type dat uiteindelijk in de kolom
    // in de afspraken-tabel gebruikt wordt.
    //Zowel "private Klant klant" als "private int bsn" werken hier dus niet.
    private int klant;

    private String tijd;

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
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

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public int getKlant() {
        return klant;
    }

    public void setKlant(int klant) {
        this.klant = klant;
    }
}
