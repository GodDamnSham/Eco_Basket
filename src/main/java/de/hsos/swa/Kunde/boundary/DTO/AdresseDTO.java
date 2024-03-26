/*
 * @Author:  Malik & Finn 
 */
package de.hsos.swa.Kunde.boundary.DTO;

import de.hsos.swa.Kunde.entity.Adresse;

public class AdresseDTO {
    String plz;
    String ort;
    String straße;
    String hausnummer;

    public AdresseDTO(){}

    // Konstruktor
    public AdresseDTO(String plz, String ort, String straße, String hausnummer) {
        this.plz = plz;
        this.ort = ort;
        this.straße = straße;
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
        return straße;
    }

    // Setter für Straße
    public void setStraße(String straße) {
        this.straße = straße;
    }

    // Getter für Hausnummer
    public String getHausnummer() {
        return hausnummer;
    }

    // Setter für Hausnummer
    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public static Adresse DTOToAdresse( AdresseDTO adresseDTO){
        return new Adresse(adresseDTO.plz, adresseDTO.ort, adresseDTO.straße, adresseDTO.hausnummer);
    }

    public static AdresseDTO adresseInDTO(Adresse adresse){
        return new AdresseDTO(adresse.getPlz(), adresse.getOrt(), adresse.getStraße(), adresse.getHausnummer());

    }
}
