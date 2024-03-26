package de.hsos.swa.Lager.boundary.DTO;

import java.util.UUID;

/*
 * Diese Klasse wurde mit AI generiert
 *@Author: Finn Christophersen 
 */
public class AddBestandDTO {
    private UUID sortimentid;
    private Integer anzahlSortiment;

    // Default-Konstruktor
    public AddBestandDTO() {
    }

    // Konstruktor mit den gegebenen Variablen
    public AddBestandDTO(UUID sortimentid, Integer anzahlSortiment) {
        this.sortimentid = sortimentid;
        this.anzahlSortiment = anzahlSortiment;
    }

    // Getter- und Setter-Methoden
    public UUID getSortimentid() {
        return sortimentid;
    }

    public void setSortimentid(UUID sortimentid) {
        this.sortimentid = sortimentid;
    }

    public Integer getAnzahlSortiment() {
        return anzahlSortiment;
    }

    public void setAnzahlSortiment(Integer anzahlSortiment) {
        this.anzahlSortiment = anzahlSortiment;
    }
}
