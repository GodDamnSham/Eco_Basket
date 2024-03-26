package de.hsos.swa.Lager.boundary.DTO;

import java.util.UUID;

/*
 * @Author: Finn Christophersen
 */
public class BestandDTO {
    
    UUID id;
    UUID idSortiment;

    Integer anzahlSortiment;

    public BestandDTO(){}

    public BestandDTO(UUID id, UUID idSortiment, Integer anzahlSortiment){
        
        this.id = id;
        this.idSortiment = idSortiment;
        this.anzahlSortiment = anzahlSortiment;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getIdSortiment() {
        return idSortiment;
    }

    public void setIdSortiment(UUID idSortiment) {
        this.idSortiment = idSortiment;
    }

    public Integer getAnzahlSortiment(){
        return anzahlSortiment;
    }

    public void setAnzahlSortiment(Integer anzahlSortimet){
        this.anzahlSortiment = anzahlSortimet;
    }

    @Override
    public String toString(){

        return "Bestand{" +
               "ID: " + this.id + "\n"+
               "SortimentId: " + this.idSortiment + "\n"+
               "Anzahl: " + this.anzahlSortiment + "\n"+
        "};";
    }
}
