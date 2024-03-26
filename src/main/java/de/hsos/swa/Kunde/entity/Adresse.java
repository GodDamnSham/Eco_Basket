/*
 * @Author:  Malik & Finn 
 */
package de.hsos.swa.Kunde.entity;

import java.util.UUID;

import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Vetoed
@Entity
@Table(name="ADRESSE")
public class Adresse {

    @Id
    @GeneratedValue
    private UUID id;

    private String plz;
    private String ort;
    private String strasse;
    private String hausnummer;

    

    public Adresse(){}
    // Konstruktor
    public Adresse(String plz, String ort, String straße, String hausnummer) {
        this.plz = plz;
        this.ort = ort;
        this.strasse = straße;
        this.hausnummer = hausnummer;
    }

    // Getter für PLZ
    public String getPlz() {
        return plz;
    }

    // Setter für PLZ
    public void setPlz(String plz) {
        this.plz = plz;
    }

    // Getter für Ort
    public String getOrt() {
        return ort;
    }

    // Setter für Ort
    public void setOrt(String ort) {
        this.ort = ort;
    }

    // Getter für Straße
    public String getStraße() {
        return strasse;
    }

    // Setter für Straße
    public void setStraße(String straße) {
        this.strasse = straße;
    }

    // Getter für Hausnummer
    public String getHausnummer() {
        return hausnummer;
    }

    // Setter für Hausnummer
    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
