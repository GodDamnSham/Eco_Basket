package de.hsos.swa.Kunde.acl;

import de.hsos.swa.Kunde.entity.Kunde;

public class KundeDeleteEvent {
    String KundeID;

    public KundeDeleteEvent(String id){
      this.KundeID = id;
    }

    public String getKundeID() {
        return KundeID;
    }

    public void setKundeID(String kundeID) {
        KundeID = kundeID;
    }
}

