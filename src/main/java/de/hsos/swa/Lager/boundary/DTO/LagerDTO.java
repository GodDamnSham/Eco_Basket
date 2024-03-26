package de.hsos.swa.Lager.boundary.DTO;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.hsos.swa.Lager.entity.Lager;
import de.hsos.swa.Lager.entity.Bestand;

/*
 * @Author: Finn Christophersen
 */
public class LagerDTO {

    UUID id;
    List<BestandDTO> bestand;

    public LagerDTO(UUID id, List<BestandDTO> bestand){
        this.id = id;
        this.bestand = bestand;
    }

    public LagerDTO(Lager lager){
        this.id = lager.getId();
        List<BestandDTO> tmp = new LinkedList<BestandDTO>();

        List<Bestand> list = lager.getBestand();
        for(int i=0; i<list.size(); i++){
            Bestand bestand = list.get(i);
            tmp.add(new BestandDTO(bestand.getId(), bestand.getSortiment().getID(), bestand.getAnzahlSortiment()));
        }

        this.bestand = tmp;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public List<BestandDTO> getBestand(){
        return bestand;
    }

    public void setBestand(List<BestandDTO> bestand){
        this.bestand = bestand;
    }

    @Override
    public String toString(){

        String bestandString = "";
        if(!this.bestand.isEmpty()){
            
            for(int i = 0 ; i<this.bestand.size(); i++){
                bestandString = bestandString + this.bestand.get(i).toString();
            }
        }

        return "Lager{" +
               "ID: " + this.id + "\n"+
               "Bestand: " + bestandString +"\n"+
        "};";
    }
    
    public static class Converter{
        public static LagerDTO newLagerDTO(Lager lager){
            return new LagerDTO(lager);
        }
    }
    
}
