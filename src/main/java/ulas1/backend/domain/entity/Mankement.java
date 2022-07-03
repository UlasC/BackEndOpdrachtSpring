package ulas1.backend.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Mankement {

    private String betalingstatus; //"Betaald" of "Niet betaald"
    private String reparatieFase;// "Inspectie lopende", "Inspectie klaar", "Offerte reparatie niet akkoord", "Offerte reparatie akkoord", "In reparatie" of "Gerepareerd"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mankementId;

    @ManyToMany
    private List<Onderdeel> onderdelen;
    @ManyToMany
    private List<BestaandeHandeling> handelingen;
    @ManyToMany
    private List<OverigeHandeling> overigeHandelingen;
    @ManyToOne
    private Auto auto;

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
