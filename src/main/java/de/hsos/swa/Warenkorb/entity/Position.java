package de.hsos.swa.Warenkorb.entity;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.hsos.swa.Sortiment.entity.Sortiment;
import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
@Vetoed
@Entity
@Table(name = "POSITION")
@NamedQuery(name = "Position.getSortimentById", query = "SELECT p FROM  Position p WHERE p.sortiment = :idsortiment")
public class Position{
    @Id
    @GeneratedValue
    UUID id;

    @OneToOne(targetEntity = Sortiment.class , cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Sortiment sortiment;
    Integer anzahlsortiment;

    public Position(){}

    public Position(UUID id, Sortiment sortiment, Integer anzahlSortiment){
        this.id = id;
        this.sortiment = sortiment;
        this.anzahlsortiment = anzahlSortiment;
    }

    public Position(Sortiment sortiment, Integer anzahlSortiment){
        this.sortiment = sortiment;
        this.anzahlsortiment = anzahlSortiment;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public Sortiment getSortiment(){
        return sortiment;
    }

    public void setSortiment(Sortiment sortiment){
        this.sortiment = sortiment;
    }

    public Integer getAnzahlSortiment(){
        return anzahlsortiment;
    }

    public void setAnzahlSortiment(Integer anzahlSortiment){
        this.anzahlsortiment = anzahlSortiment;
    }

    @Override
    public boolean equals(Object pos){
        Position tmp = (Position)pos;
        return (tmp.getSortiment() == this.sortiment);
    }
}
