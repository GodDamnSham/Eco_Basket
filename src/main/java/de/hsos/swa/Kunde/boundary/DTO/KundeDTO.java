package de.hsos.swa.Kunde.boundary.DTO;

import de.hsos.swa.Kunde.entity.Kunde;
import io.smallrye.common.constraint.NotNull;

public class KundeDTO {

    String vorname;
    String nachname;
    String username;
    String email;
    Boolean enabled;



    //Default Konstruktor
    public KundeDTO(){}

    // Konstruktor
    public KundeDTO(@NotNull String vorname, @NotNull String nachname , @NotNull String username ,  @NotNull String email ) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.username = username;
        this.email = email;
        this.enabled = true;
    
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    // Getter für Vorname
    public String getVorname() {
        return vorname;
    }

    // Setter für Vorname
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    // Getter für Nachname
    public String getNachname() {
        return nachname;
    }

    
    // Setter für Nachname
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public static Kunde DTOToKunde( KundeDTO kundeDTO){
        return new Kunde(kundeDTO.vorname, kundeDTO.nachname , kundeDTO.username, kundeDTO.email);
    }
}
