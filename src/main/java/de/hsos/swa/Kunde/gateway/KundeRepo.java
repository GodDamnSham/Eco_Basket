/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Kunde.gateway;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.Kunde.entity.Adresse;
import de.hsos.swa.Kunde.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class KundeRepo implements KundeRepoInter {

    @Inject
    EntityManager eManager;

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Kunde> addKunde(Kunde kunde) {
        if (kunde == null) {
            return Optional.empty();
        }

        List<Kunde> result = this.eManager
                .createNamedQuery("Kunde.getKundeById", Kunde.class)
                .setParameter("id", kunde.getId())
                .getResultList();

        if (!result.isEmpty()) {
            return Optional.empty();
        }

        eManager.persist(kunde);
        return Optional.of(kunde);
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Kunde> getKunde(String id) {
        List<Kunde> result = this.eManager
                .createNamedQuery("Kunde.getKundeById", Kunde.class)
                .setParameter("id", id)
                .getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Adresse> updateAdresse(String id, Adresse adresse) {
        if (id == null) {
            return Optional.empty();
        }
        List<Kunde> result = this.eManager
                .createNamedQuery("Kunde.getKundeById", Kunde.class)
                .setParameter("id", id)
                .getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        Kunde kunde = result.get(0);

        Adresse oldAdress = kunde.adresse;
        if (kunde.adresse != null) {
            eManager.persist(adresse);
            kunde.adresse = adresse;
            if (oldAdress != null) {
                eManager.remove(oldAdress);
            }
            return Optional.of(kunde.adresse);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public List<Kunde> getAllKunde() {
        TypedQuery<Kunde> query = this.eManager
                .createNamedQuery("Kunde.findAll", Kunde.class);
        List<Kunde> result = query.getResultList();
        return result;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Adresse> getKundenAdress(String id) {
        if (id == null) {
            return Optional.empty();
        }

        List<Kunde> result = this.eManager
                .createNamedQuery("Kunde.getKundeById", Kunde.class)
                .setParameter("id", id)
                .getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        Kunde kunde = result.get(0);
        if (kunde == null || kunde.adresse == null) {
            return Optional.empty();
        }
        Adresse a = kunde.adresse;
        return Optional.of(a);
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public boolean kundeAddAdress(String id, Adresse adresse) {
        if (id == null || adresse == null) {
            return false;
        }

        List<Kunde> result = this.eManager
                .createNamedQuery("Kunde.getKundeById", Kunde.class)
                .setParameter("id", id)
                .getResultList();
        if (result.isEmpty()) {
            return false;
        }
        Kunde kunde = result.get(0);
        eManager.persist(adresse);
        if (result != null && kunde.adresse == null) {
            kunde.adresse = adresse;
            eManager.persist(kunde);
            return true;
        }

        return false;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Boolean deleteKunde(String id) {
        if (id == null) {
            return false;
        }
        List<Kunde> result = this.eManager
                .createNamedQuery("Kunde.getKundeById", Kunde.class)
                .setParameter("id", id)
                .getResultList();
        if (result.isEmpty()) {
            return false;
        }
        Kunde kunde = result.get(0);
        eManager.remove(kunde);
        return true;
    }

}
