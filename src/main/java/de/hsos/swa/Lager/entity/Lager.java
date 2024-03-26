package de.hsos.swa.Lager.entity;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * @Author: Finn Christophersen
 */
@Vetoed
@Entity
@Table(name = "LAGER")
@NamedQuery(name = "Lager.getLagerByLagerId", query = "SELECT l FROM Lager l WHERE l.id = :lagerId")
public class Lager{
    @Id
    @GeneratedValue
    UUID id;

    String ort;

    @OneToMany(targetEntity = Bestand.class, fetch = FetchType.EAGER)
    List<Bestand> bestand;

    public Lager(){}

    public Lager(String ort, List<Bestand> bestand){
        this.ort = ort;
        this.bestand = bestand;
    }


    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public List<Bestand> getBestand(){
        return bestand;
    }

    public void setBestand(List<Bestand> bestand){
        this.bestand = bestand;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
