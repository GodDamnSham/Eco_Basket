/*
 * @Author:  Malik & Finn 
 */
package de.hsos.swa.Sortiment.boundary.DTO;

import de.hsos.swa.Sortiment.entity.Sortiment;



public class SortimentAddDTO {
    private String name;
    private Float preis;

    //Standard Konstruktor
    public SortimentAddDTO(){}

    // Konstruktor
    public SortimentAddDTO( String name, Float preis) {
        this.name = name;
        this.preis = preis;
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

    public static Sortiment createSortiment(SortimentAddDTO dto){
        return new Sortiment(dto.getName(), dto.getPreis());
    }
}
