/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Kunde.control;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.Kunde.acl.KundeCreatedEvent;
import de.hsos.swa.Kunde.acl.KundeDeleteEvent;
import de.hsos.swa.Kunde.boundary.KundeRessource;
import de.hsos.swa.Kunde.entity.Adresse;
import de.hsos.swa.Kunde.entity.Kunde;
import de.hsos.swa.Kunde.gateway.KundeRepoInter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;


@ApplicationScoped
public class KundeService implements KundeServiceInter {
    final Logger LOGGER = Logger.getLogger(KundeRessource.class);
    @Inject
    KundeRepoInter kundeRepoInter;

    @Inject
    Event<KundeCreatedEvent> kundeCreatedEvent;

    @Inject
    Event<KundeDeleteEvent> kundeDeleteEvent;



    public List<Kunde> getAllKunde(){
        return this.kundeRepoInter.getAllKunde();
    }

    public Optional<Kunde> addKunde(Kunde kunde){
        Optional<Kunde> newKunde = this.kundeRepoInter.addKunde(kunde);
        if(!newKunde.isPresent()){
            return Optional.empty();
        }
        LOGGER.info("new costumer Added");
        this.kundeCreatedEvent.fireAsync(new KundeCreatedEvent(newKunde.get()));
        return newKunde;
    }

    public Optional<Kunde> getKunde(String id){
        return this.kundeRepoInter.getKunde(id);
    }
    
    public boolean kundeAddAdress(String kundenId , Adresse adresse){
        return this.kundeRepoInter.kundeAddAdress(kundenId, adresse);
    }

    
    public Optional<Adresse> updateAdresse(String id, Adresse adresse){
        return this.kundeRepoInter.updateAdresse(id, adresse);
    }

    public Optional<Adresse> getKundenAdress(String id){
        return this.kundeRepoInter.getKundenAdress(id);
    }

    public Boolean deleteKunde(String id){
        LOGGER.info("costumer Deleted");
        this.kundeDeleteEvent.fireAsync(new KundeDeleteEvent(id));
        return this.kundeRepoInter.deleteKunde(id);
    }
}
