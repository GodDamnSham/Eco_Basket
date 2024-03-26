package de.hsos.swa.Warenkorb.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.hsos.swa.Kunde.entity.Kunde;
import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
@Vetoed
@Entity
@Table(name = "WARENKORB")
@NamedQuery(name = "Warenkorb.getWarenkorbByKundenid", query = "SELECT w FROM Warenkorb w WHERE w.kunden = :kundenId")
public class Warenkorb{
    @Id
    @GeneratedValue
    UUID id;

    @OneToOne(targetEntity = Kunde.class , cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Kunde kunden;

    @OneToMany(targetEntity = Position.class, fetch = FetchType.EAGER)
    List<Position> position;

    public Warenkorb(){}

    public Warenkorb(UUID id, Kunde kundenid, List<Position> position){
        this.id = id;
        this.kunden = kundenid;
        this.position = position;
    }

    public Warenkorb(Kunde kundenid, List<Position> position){
        this.kunden = kundenid;
        this.position = position;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public Kunde getKundenId(){
        return kunden;
    }

    public void setKundenId(Kunde kundenid){
        this.kunden = kundenid;
    }

    public List<Position> getPosition(){
        return position;
    }

    public void setPosition(List<Position> position){
        this.position = position;
    }
}
