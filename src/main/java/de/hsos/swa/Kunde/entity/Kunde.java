/*
 * @Author:  Malik & Finn 
 */
package de.hsos.swa.Kunde.entity;



import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Vetoed
@Entity
@Table(name="KUNDE")
@NamedQuery(name = "Kunde.findAll" , query = "select k from Kunde k")
@NamedQuery(name = "Kunde.getKundeById", query = "SELECT k FROM Kunde k WHERE k.id = :id")
public class Kunde {
    @Id
    String id;

    String vorname;
    String nachname;
    String username;
    String email;
    Boolean enabled;


    @OneToOne
    public Adresse adresse;

    public Kunde(){}

    public Kunde(String vorname, String nachname , String username ,   String email){
        this.vorname = vorname;
        this.nachname = nachname;
        this.username = username;
        this.email = email;
        this.enabled = true;

    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
