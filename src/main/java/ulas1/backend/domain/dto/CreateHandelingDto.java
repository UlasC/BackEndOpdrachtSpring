package ulas1.backend.domain.dto;

public class CreateHandelingDto {
    private double prijs;
    private String handeling;

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
