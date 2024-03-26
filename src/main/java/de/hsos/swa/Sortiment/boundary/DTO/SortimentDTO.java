package de.hsos.swa.Sortiment.boundary.DTO;

import java.util.UUID;

import de.hsos.swa.Sortiment.entity.Sortiment;
/*
 * @Author:  Malik & Finn 
 */

public class SortimentDTO {
    private UUID id;
    private String name;
    private Float preis;

    //Standard Konstruktor
    public SortimentDTO(){}

    // Konstruktor
    public SortimentDTO(UUID id, String name, Float preis) {
        this.id = id;
        this.name = name;
        this.preis = preis;
    }

        // Getter für id
    public UUID getID() {
        return id;
    }

    // Setter für id
    public void setName(UUID id) {
        this.id = id;
    }
    // Getter für Name
    public String getName() {
        return name;
    }

    // Setter für Name
    public void setName(String name) {
        this.name = name;
    }

        // Getter für preis
    public Float getPreis() {
        return preis;
    }

    // Setter für preis
    public void setPreis(Float preis) {
        this.preis = preis;
    }

    public static class Converter{
        public static SortimentDTO newSortiment(Sortiment sortiment){
            return new SortimentDTO(sortiment.getID(), sortiment.getName(), sortiment.getPreis());
        }
    }
}
