package de.hsos.swa.Warenkorb.boundary.DTO;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.hsos.swa.Warenkorb.entity.Position;
import de.hsos.swa.Warenkorb.entity.Warenkorb;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
public class WarenkorbDTO {

    UUID id;
    String kundenid;
    List<PositionDTO> position;

    public WarenkorbDTO(UUID id, String kundenid, List<PositionDTO> position){
        this.id = id;
        this.kundenid = kundenid;
        this.position = position;
    }

    public WarenkorbDTO(Warenkorb warenkorb){
        this.id = warenkorb.getId();
        this.kundenid = warenkorb.getKundenId().getId();
        List<PositionDTO> tmp = new LinkedList<PositionDTO>();

        List<Position> list = warenkorb.getPosition();
        for(int i=0; i<list.size(); i++){
            Position pos = list.get(i);
            tmp.add(new PositionDTO(pos.getId(), pos.getSortiment().getID(), pos.getAnzahlSortiment()));
        }

        this.position = tmp;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getKundenId(){
        return kundenid;
    }

    public void setKundenId(String kundenid){
        this.kundenid = kundenid;
    }

    public List<PositionDTO> getPosition(){
        return position;
    }

    public void setPosition(List<PositionDTO> position){
        this.position = position;
    }

    @Override
    public String toString(){

        String positionString = "";
        if(!this.position.isEmpty()){
            
            for(int i = 0 ; i<this.position.size(); i++){
                positionString = positionString + this.position.get(i).toString();
            }
        }

        return "Warenkorb{" +
               "ID: " + this.id +
               "Kundenid: " + this.kundenid +
               "Position: " + positionString +
        "};";
    }
    
    public static class Converter{
        public static WarenkorbDTO newWarenkorbDTO(Warenkorb warenkorb){
            return new WarenkorbDTO(warenkorb);
        }
    }
    
}
