package de.hsos.swa.Lager.entity;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.hsos.swa.Sortiment.entity.Sortiment;
import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/*
 * @Author: Finn Christophersen
 */
@Vetoed
@Entity
@Table(name = "BESTAND")
public class Bestand{
    @Id
    @GeneratedValue
    UUID id;

    @OneToOne(targetEntity = Sortiment.class, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Sortiment sortiment;
    Integer anzahlsortiment;

    public Bestand(){}

    public Bestand(UUID id, Sortiment sortiment, Integer anzahlSortiment){
        this.id = id;
        this.sortiment = sortiment;
        this.anzahlsortiment = anzahlSortiment;
    }

    public Bestand(Sortiment sortiment, Integer anzahlSortiment){
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
        Bestand tmp = (Bestand)pos;
        return (tmp.getSortiment() == this.sortiment);
    }
}
