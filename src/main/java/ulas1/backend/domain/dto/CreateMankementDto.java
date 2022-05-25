package ulas1.backend.domain.dto;

import javax.persistence.Id;
import java.util.List;

public class CreateMankementDto {

    private String betalingstatus;
    private String reparatieFase;
    private int mankementId;

    private List <Integer> handelingsnummers;
    private List<Integer> onderdeelnummers;
    private String kenteken;

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

    public int getMankementId() {
        return mankementId;
    }

    public void setMankementId(int mankementId) {
        this.mankementId = mankementId;
    }

    public List<Integer> getHandelingsnummers() {
        return handelingsnummers;
    }

    public void setHandelingsnummers(List<Integer> handelingsnummers) {
        this.handelingsnummers = handelingsnummers;
    }

    public List<Integer> getOnderdeelnummers() {
        return onderdeelnummers;
    }

    public void setOnderdeelnummers(List<Integer> onderdeelnummers) {
        this.onderdeelnummers = onderdeelnummers;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }
}
