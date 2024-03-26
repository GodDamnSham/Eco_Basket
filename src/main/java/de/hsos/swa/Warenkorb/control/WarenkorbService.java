package de.hsos.swa.Warenkorb.control;

import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Kunde.acl.KundeCreatedEvent;
import de.hsos.swa.Kunde.acl.KundeDeleteEvent;
import de.hsos.swa.Kunde.boundary.KundeRessource;
import de.hsos.swa.Warenkorb.entity.Warenkorb;
import de.hsos.swa.Warenkorb.gateway.WarenKorbRepoI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
@ApplicationScoped
public class WarenkorbService implements WarenkorbServiceInter{
    final Logger LOGGER = Logger.getLogger(KundeRessource.class);

    @Inject
    WarenKorbRepoI warenKorbRepoI;

    @Override
    public Optional<Warenkorb> getWarenkorb(String kundenid){
        return this.warenKorbRepoI.getWarenkorb(kundenid);
    }

    @Override
    public Optional<Warenkorb> fuegePositionhinzu(String kundenid, UUID idpizza, Integer anzahlpizza){
        return this.warenKorbRepoI.fuegePositionhinzu(kundenid, idpizza, anzahlpizza);
    }
  
    public Optional<Warenkorb> createAWarenKorbWhenKundeAdded(@ObservesAsync KundeCreatedEvent kundeCreated) {
        if (kundeCreated.getKundeID() == null) {
            throw new IllegalArgumentException(" ID cannot be null");
        }
        Optional<Warenkorb> warenKorb = this.warenKorbRepoI.addWarenkorb(kundeCreated.getKundeID());
        LOGGER.info("warenKorb created by Event with ID : " + warenKorb.get().getId() );
        return warenKorb;
    }

    public boolean deleteWarenKorbWhenKundeRemoved(@ObservesAsync KundeDeleteEvent kundeDeleteEvent) {
        if (kundeDeleteEvent.getKundeID() == null) {
            throw new IllegalArgumentException(" ID cannot be null");
        }
        boolean warenKorb = this.warenKorbRepoI.deleteWarenkorb(kundeDeleteEvent.getKundeID());
        LOGGER.info("warenKorb deleted " );
        return warenKorb;
    }

   
    public Optional<Warenkorb> updatePosition(String kundenid, UUID idpizza, Integer anzahlpizza){
        return null;
    }
    



}
