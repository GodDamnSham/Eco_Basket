package de.hsos.swa.Lager.gateway;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Lager.control.LagerService;
import de.hsos.swa.Lager.entity.Lager;
import de.hsos.swa.Sortiment.entity.Sortiment;
import de.hsos.swa.Lager.entity.Bestand;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/*
 * @Author: Finn Christophersen
 */
@Dependent
public class LagerRepo implements LagerService{

    @Inject
    EntityManager em;

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Lager> getLager(UUID lagerId) {
        List<Lager> lager = em.createNamedQuery("Lager.getLagerByLagerId", Lager.class)
                            .setParameter("lagerId", lagerId)
                            .getResultList();


        if(lager.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(lager.get(0));
    }

    

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Lager> updateBestandVonLager(UUID lagerid, UUID idSortiment, Integer anzahlSortiment){
        List<Sortiment> sortiment = em.createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
        .setParameter("id", idSortiment)
        .getResultList();


        if(sortiment.isEmpty()){
            return Optional.empty();
        }


        List<Lager> lager = em.createNamedQuery("Lager.getLagerByLagerId", Lager.class)
                                .setParameter("lagerId", lagerid)
                                .getResultList();

        if(lager.isEmpty() /*!= null*/){
            return Optional.empty();
        }

        Bestand bestand = new Bestand(sortiment.get(0), anzahlSortiment);

        List<Bestand> list = lager.get(0).getBestand();



        int positionInListe = isBestandInList(list, bestand);
        if(positionInListe < 0){
            return Optional.empty();
        }else{
            
            lager.get(0).getBestand().get(positionInListe).setAnzahlSortiment(anzahlSortiment);

            return Optional.of(lager.get(0));
        }
    }
 
    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Lager> updateBestandVonLagerZuLager(UUID lagerVon, UUID lagerZu, UUID idSortiment, Integer anzahlSortiment){
        List<Sortiment> sortiment = em.createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
        .setParameter("id", idSortiment)
        .getResultList();


        if(sortiment.isEmpty()){
            return Optional.empty();
        }


        List<Lager> lagerV = em.createNamedQuery("Lager.getLagerByLagerId", Lager.class)
                                .setParameter("lagerId", lagerVon)
                                .getResultList();

        if(lagerV.isEmpty() /*!= null*/){
            return Optional.empty();
        }

        
        List<Lager> lagerZ = em.createNamedQuery("Lager.getLagerByLagerId", Lager.class)
                                .setParameter("lagerId", lagerZu)
                                .getResultList();

        if(lagerZ.isEmpty() /*!= null*/){
            return Optional.empty();
        }

        Bestand bestand = new Bestand(sortiment.get(0), anzahlSortiment);

        int positionVonLager = isBestandInList(lagerV.get(0).getBestand(), bestand);
        if(positionVonLager<0){
            return Optional.empty();
        }


        if(!istGenugImLagerVorhanden(lagerV.get(0).getBestand().get(positionVonLager).getAnzahlSortiment(), anzahlSortiment)){
            return Optional.empty();
        }

        Lager LagerNew = lagerZ.get(0);
        List<Bestand> list = LagerNew.getBestand();

        

        int positionInListe = isBestandInList(list, bestand);
        if(positionInListe >= 0){
            lagerZ.get(0).getBestand().get(positionInListe).setAnzahlSortiment(anzahlSortiment+lagerZ.get(0).getBestand().get(positionInListe).getAnzahlSortiment());

            lagerV.get(0).getBestand().get(positionVonLager).setAnzahlSortiment(lagerV.get(0).getBestand().get(positionVonLager).getAnzahlSortiment()-anzahlSortiment);
            
            return Optional.of(lagerZ.get(0));
        }else{
            list.add(bestand);
            em.persist(bestand);
            lagerZ.get(0).setBestand(list);

            lagerV.get(0).getBestand().get(positionVonLager).setAnzahlSortiment(anzahlSortiment-lagerV.get(0).getBestand().get(positionVonLager).getAnzahlSortiment());
            return Optional.of(lagerZ.get(0));
        }
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Lager> fuegeBestandhinzu(UUID idLager, UUID idSortiment, Integer anzahlSortiment) {
        List<Sortiment> sortiment = em.createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
                            .setParameter("id", idSortiment)
                            .getResultList();
 
        if(sortiment.isEmpty()){
            return Optional.empty();
        }

        
        List<Lager> lager = em.createNamedQuery("Lager.getLagerByLagerId", Lager.class)
                                .setParameter("lagerId", idLager)
                                .getResultList();
        
        if(lager.isEmpty() ){
            return Optional.empty();
        }

        
        Bestand bestand = new Bestand(sortiment.get(0), anzahlSortiment);

        List<Bestand> list = lager.get(0).getBestand();

        

        int positionInListe = isBestandInList(list, bestand);
        if(positionInListe >= 0){
            return Optional.empty();
        }else{
            
            em.persist(bestand);
            //list.add(bestand);
            
            lager.get(0).getBestand().add(bestand);

            return Optional.of(lager.get(0));
        }

    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Lager> addLager(String ort) {
        Lager l = new Lager(ort, new LinkedList<Bestand>());
        em.persist(l);
        return Optional.of(l);
    }

    private int isBestandInList(List<Bestand> list, Bestand bestand){
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(bestand)){
                return i;
            }
        }
        return -1;
    }

    private boolean istGenugImLagerVorhanden(Integer imLager, Integer zuverschieben){
        return zuverschieben<imLager;
    }


    
}
