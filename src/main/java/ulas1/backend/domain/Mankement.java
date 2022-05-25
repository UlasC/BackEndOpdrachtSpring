package ulas1.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Mankement {

    private String betalingstatus;
    private String reparatieFase;// akkoord of geen akkoord
    @Id
    private int mankementId;

    

    @ManyToMany
    private List<Onderdeel> onderdelen;
    @ManyToMany
    private List<Handeling> handelingen;
    @ManyToOne
    private Auto auto;

    public Mankement(){

    }

    public Mankement(String betalingstatus, String reparatieFase, List<Onderdeel> onderdelen, List<Handeling> handelingen, Auto auto, int mankementId) {
        this.betalingstatus = betalingstatus;
        this.reparatieFase = reparatieFase;
        this.onderdelen = onderdelen;
        this.handelingen = handelingen;
        this.auto = auto;
        this.mankementId = mankementId;
    }

    public int getMankementId() {
        return mankementId;
    }

    public void setMankementId(int mankementId) {
        this.mankementId = mankementId;
    }

    public String getBetalingstatus() {
        return betalingstatus;
    }

    public void setBetalingstatus(String betalingstatus) {
        this.betalingstatus = betalingstatus;
    }

    public String getReparatieFase() {
        return reparatieFase;
    }

    public void setReparatieFase(String reparatieFase) {
        this.reparatieFase = reparatieFase;
    }

    public List<Onderdeel> getOnderdelen() {
        return onderdelen;
    }

    public void setOnderdelen(List<Onderdeel> onderdelen) {
        this.onderdelen = onderdelen;
    }

    public List<Handeling> getHandelingen() {
        return handelingen;
    }

    public void setHandelingen(List<Handeling> handelingen) {
        this.handelingen = handelingen;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
}