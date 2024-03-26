package de.hsos.swa.Warenkorb.boundary.DTO;

import java.util.UUID;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
public class UpdatePositionDTO {
    UUID id;
    UUID idsortiment;
    Integer anzahlSortiment;

    public UpdatePositionDTO(){}

    public UpdatePositionDTO(UUID id, UUID idsortiment, Integer anzahlSortiment){
        this.id = id;
        this.idsortiment = idsortiment;
        this.anzahlSortiment = anzahlSortiment;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
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
