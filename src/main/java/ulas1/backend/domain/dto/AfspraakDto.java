package ulas1.backend.domain.dto;

import ulas1.backend.domain.entity.Afspraak;
import ulas1.backend.domain.entity.Klant;

import java.util.ArrayList;
import java.util.List;

public class AfspraakDto {

    private String tijd;
    private int dag;
    private int maand;
    private int jaar;
    private String soortAfspraak;
    private Klant klant;
    private MedewerkerCreatedDto medewerker;

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

    public String getSoortAfspraak() {
        return soortAfspraak;
    }

    public void setSoortAfspraak(String soortAfspraak) {
        this.soortAfspraak = soortAfspraak;
    }

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public MedewerkerCreatedDto getMedewerkerCreatedDto() {
        return medewerker;
    }

    public void setMedewerkerCreatedDto(MedewerkerCreatedDto medewerkerCreatedDto) {
        this.medewerker = medewerkerCreatedDto;
    }

    public static AfspraakDto from(Afspraak afspraak){
        AfspraakDto afspraakDto = new AfspraakDto();
        afspraakDto.setTijd(afspraak.getTijd());
        afspraakDto.setDag(afspraak.getDag());
        afspraakDto.setMaand(afspraak.getMaand());
        afspraakDto.setJaar(afspraak.getJaar());
        afspraakDto.setSoortAfspraak(afspraak.getSoortAfspraak());
        afspraakDto.setKlant(afspraak.getKlant());
        afspraakDto.setMedewerkerCreatedDto(MedewerkerCreatedDto.from(afspraak.getMedewerker()));

        return afspraakDto;
    }

    public static List<AfspraakDto> from(List<Afspraak> afspraken){
        List<AfspraakDto> afspraakDtos = new ArrayList<>();
        for(Afspraak afspraak : afspraken){
            afspraakDtos.add(AfspraakDto.from(afspraak));
        }
        return afspraakDtos;
    }
}
