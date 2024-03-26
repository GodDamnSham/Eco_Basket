/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Sortiment.entity;


import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Vetoed
@Entity
@Table(name = "SORTIMENT")
@NamedQuery(name = "Sortiment.findALL", query = "SELECT s FROM Sortiment s ")
@NamedQuery(name = "Sortiment.getSortimentById", query = "SELECT s FROM Sortiment s WHERE s.id = :id")
@NamedQuery(name = "Sortiment.findAll", query = "SELECT s FROM Sortiment s")
public class Sortiment {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Float preis;

    //Standard Konstruktor
    public Sortiment(){}
 
    
    // Konstruktor
    public Sortiment(String name, Float preis) {
        this.name = name;
        this.preis = preis;
    }

    @JsonProperty("ID")
    public UUID getID() {
        return id;
    }

    public void setId(UUID id){
        this.id = id; 
    }

    // Setter für id
    public void setName(UUID id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }

    // Setter für Name
    public void setName(String name) {
        this.name = name;
    }


    public Float getPreis() {
        return preis;
    }

    // Setter für preis
    public void setPreis(Float preis) {
        this.preis = preis;
    }
}

