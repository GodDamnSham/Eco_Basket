package de.hsos.swa.Warenkorb.gateway;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import de.hsos.swa.Kunde.entity.Kunde;
import de.hsos.swa.Sortiment.entity.Sortiment;
import de.hsos.swa.Warenkorb.entity.Position;
import de.hsos.swa.Warenkorb.entity.Warenkorb;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
@Dependent
@Transactional
public class WarenkorbRepo implements WarenKorbRepoI{

    @Inject
    EntityManager em;

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Warenkorb> getWarenkorb(String kundenid) {
        List<Kunde> kunden = em.createNamedQuery("Kunde.getKundeById", Kunde.class)
                            .setParameter("id", kundenid)
                            .getResultList();


        if(kunden.isEmpty()){
            return Optional.empty();
        }


        List<Warenkorb> warenkorb = em.createNamedQuery("Warenkorb.getWarenkorbByKundenid", Warenkorb.class)
                                    .setParameter("kundenId", kunden.get(0))
                                    .getResultList();

        if(warenkorb.isEmpty() /*!= null*/){
            return Optional.empty();
        }

        return Optional.of(warenkorb.get(0));
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Warenkorb> addWarenkorb(String kundenid) {
        
        List<Kunde> kunden = em.createNamedQuery("Kunde.getKundeById", Kunde.class)
                            .setParameter("id", kundenid)
                            .getResultList();


        if(kunden.isEmpty()){
            return Optional.empty();
        }


        List<Warenkorb> warenkorb = em.createNamedQuery("Warenkorb.getWarenkorbByKundenid", Warenkorb.class)
                                    .setParameter("kundenId", kunden.get(0))
                                    .getResultList();
      
        if(!warenkorb.isEmpty() /*!= null*/){
            return Optional.of(warenkorb.get(0));
        }

        
        
        

        List<Position> list = new LinkedList<Position>();
        Kunde kunde = kunden.get(0);
        kunde.setId(kundenid);
        Warenkorb warenkorbKunde = new Warenkorb(kunde, list);

        em.persist(warenkorbKunde);
        return Optional.of(warenkorbKunde);
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Warenkorb> fuegePositionhinzu(String kundenid, UUID idsortiment, Integer anzahlSortiment) {
        List<Kunde> kunden = em.createNamedQuery("Kunde.getKundeById", Kunde.class)
                            .setParameter("id", kundenid)
                            .getResultList();


        if(kunden.isEmpty()){
            return Optional.empty();
        }


        List<Warenkorb> warenkorb = em.createNamedQuery("Warenkorb.getWarenkorbByKundenid", Warenkorb.class)
                                    .setParameter("kundenId", kunden.get(0))
                                    .getResultList();
    

        if(warenkorb.isEmpty() /*!= null*/){
            return Optional.empty();
        }

        List<Sortiment> sortiment = em.createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
                                    .setParameter("id", idsortiment)
                                    .getResultList();

      
        if(sortiment.isEmpty() /*!= null*/){
            return Optional.of(warenkorb.get(0));
        }


        Warenkorb warenkorbKunde = warenkorb.get(0);
        List<Position> list = warenkorbKunde.getPosition();

        Position pos = new Position(sortiment.get(0), anzahlSortiment);
        int positionInListe = isPositionInList(list, pos);
        if(positionInListe >= 0){
            return Optional.of(warenkorbKunde);
        }else{
            em.persist(pos);
            list.add(pos);

            warenkorbKunde.setPosition(list);

            return Optional.of(warenkorbKunde);
        }

    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Warenkorb> updatePosition(String kundenid, UUID idsortiment, Integer anzahlSortiment) {
        List<Kunde> kunden = em.createNamedQuery("Kunde.getKundeById", Kunde.class)
                            .setParameter("id", kundenid)
                            .getResultList();


        if(kunden.isEmpty()){
            return Optional.empty();
        }


        List<Warenkorb> warenkorb = em.createNamedQuery("Warenkorb.getWarenkorbByKundenid", Warenkorb.class)
                                    .setParameter("kundenId", kunden.get(0))
                                    .getResultList();
    

        if(warenkorb.isEmpty() /*!= null*/){
            return Optional.empty();
        }

        List<Sortiment> sortiment = em.createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
                                    .setParameter("id", idsortiment)
                                    .getResultList();

      
        if(sortiment.isEmpty() /*!= null*/){
            return Optional.of(warenkorb.get(0));
        }


        Warenkorb warenkorbKunde = warenkorb.get(0);
        List<Position> list = warenkorbKunde.getPosition();

        Position pos = new Position(sortiment.get(0), anzahlSortiment);

        int positionInListe = isPositionInList(list, pos);
        if(positionInListe<0){
            return Optional.empty();
        }else{
            
            warenkorb.get(0).getPosition().get(positionInListe).setAnzahlSortiment(anzahlSortiment);

            return Optional.of(warenkorb.get(0));
        }

    }



    private int isPositionInList(List<Position> list, Position position){
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(position)){
                return i;
            }
        }
        return -1;
    }


    @Override
    @Transactional(TxType.REQUIRED)
    public boolean deleteWarenkorb(String kundenid) {
        
        List<Kunde> kunden = em.createNamedQuery("Kunde.getKundeById", Kunde.class)
                            .setParameter("id", kundenid)
                            .getResultList();


        if(kunden.isEmpty()){
            return false;
        }


        List<Warenkorb> warenkorb = em.createNamedQuery("Warenkorb.getWarenkorbByKundenid", Warenkorb.class)
                                    .setParameter("kundenId", kunden.get(0))
                                    .getResultList();
      
        if(warenkorb.isEmpty() /*!= null*/){
            return false;
        }
        warenkorb.get(0).getPosition().clear();
        warenkorb.get(0).getKundenId().setId(null);

        
        em.remove(warenkorb);
        return true;
    }
    
}
