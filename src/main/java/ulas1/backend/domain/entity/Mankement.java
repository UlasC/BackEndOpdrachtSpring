package ulas1.backend.domain.entity;

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
    private List<BestaandeHandeling> handelingen;
    @ManyToMany
    private List<OverigeHandeling> overigeHandelingen;
    @ManyToOne
    private Auto auto;

    public Mankement(){

    }

    public Mankement(String betalingstatus, String reparatieFase, List<Onderdeel> onderdelen, List<BestaandeHandeling> handelingen, List<OverigeHandeling> overigeHandelingen, Auto auto, int mankementId) {
        this.betalingstatus = betalingstatus;
        this.reparatieFase = reparatieFase;
        this.onderdelen = onderdelen;
        this.handelingen = handelingen;
        this.overigeHandelingen = overigeHandelingen;
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

    public List<BestaandeHandeling> getBestaandeHandelingen() {
        return handelingen;
    }

    public void setBestaandeHandelingen(List<BestaandeHandeling> handelingen) {
        this.handelingen = handelingen;
    }

    public List<OverigeHandeling> getOverigeHandelingen() {
        return overigeHandelingen;
    }

    public void setOverigeHandelingen(List<OverigeHandeling> overigeHandelingen) {
        this.overigeHandelingen = overigeHandelingen;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
}