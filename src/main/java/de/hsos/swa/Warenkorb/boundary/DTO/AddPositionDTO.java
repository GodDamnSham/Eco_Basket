package de.hsos.swa.Warenkorb.boundary.DTO;

import java.util.UUID;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
public class AddPositionDTO {
    
    UUID idsortiment;
    Integer anzahlSortiment;

    public AddPositionDTO(){}

    public AddPositionDTO(UUID idpizza, Integer anzahlSortiment){
        this.idsortiment = idpizza;
        this.anzahlSortiment = anzahlSortiment;
    }

    public UUID getSortimentId(){
        return idsortiment;
    }

    public void setSortimentId(UUID idsortiment){
        this.idsortiment = idsortiment;
    }

    public Integer getAnzahlSortiment(){
        return anzahlSortiment;
    }

    public void setAnzahlSortiment(Integer anzahlSortiment){
        this.anzahlSortiment = anzahlSortiment;
    }
}
