package de.hsos.swa.Kunde.acl;

import java.util.UUID;

import de.hsos.swa.Kunde.entity.Kunde;

public class KundeCreatedEvent {
    String KundeID;

    public KundeCreatedEvent(Kunde kunde){
      this.KundeID = kunde.getId();
    }

    public String getKundeID() {
        return KundeID;
    }

    public void setKundeID(String kundeID) {
        KundeID = kundeID;
    }
}
