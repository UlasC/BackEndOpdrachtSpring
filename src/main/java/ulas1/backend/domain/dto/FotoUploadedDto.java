package ulas1.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ulas1.backend.domain.entity.IdentiteitskaartFoto;
import ulas1.backend.domain.entity.Klant;

public class FotoUploadedDto {
    @JsonIgnore
    private Klant klant;
    private int id;
    private String name;
    private String type;
    private long size;

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public static FotoUploadedDto from(IdentiteitskaartFoto identiteitskaartFoto){
        FotoUploadedDto fotoUploadedDto = new FotoUploadedDto();
        fotoUploadedDto.setId(identiteitskaartFoto.getId());
        fotoUploadedDto.setType(identiteitskaartFoto.getType());
        fotoUploadedDto.setName(identiteitskaartFoto.getName());
        fotoUploadedDto.setKlant(identiteitskaartFoto.getKlant());
        fotoUploadedDto.setSize(identiteitskaartFoto.getData().length);

        return fotoUploadedDto;
    }
}
