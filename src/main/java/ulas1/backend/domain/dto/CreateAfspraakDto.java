package ulas1.backend.domain.dto;


public class CreateAfspraakDto {

    private String tijd;
    private int dag;
    private int maand;
    private int jaar;
    private int bsn;
    private String gebruikersnaam;
    private String soortAfspraak;

    public String getGebruikersnaam(){
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam){
        this.gebruikersnaam = gebruikersnaam;
    }

    public int getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

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

}
