/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Sortiment.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Sortiment.control.SortimentService;
import de.hsos.swa.Sortiment.entity.Sortiment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class SortimentRepo implements SortimentService {

    @Inject
    EntityManager eManager;

    @Override
    @Transactional(TxType.REQUIRED)
    public List<Sortiment> getAllSortiment() {
        TypedQuery<Sortiment> query = this.eManager
                .createNamedQuery("Sortiment.findALL", Sortiment.class);
        List<Sortiment> result = query.getResultList();
        if (result == null) {
            List<Sortiment> emptyListToAvoidNullPointer = new ArrayList<Sortiment>();
            return emptyListToAvoidNullPointer;
        }
        return result;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Sortiment> getSortiment(UUID id) {
        List<Sortiment> query = this.eManager
                .createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
                .setParameter("id", id)
                .getResultList();
        if (query.isEmpty()) {
            return Optional.empty();
        }
        Sortiment result = query.get(0);
        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(result);
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Optional<Sortiment> addSortiment(Sortiment sortiment) {
        if (sortiment == null) {
            return Optional.empty();
        }

        eManager.persist(sortiment);
        return Optional.of(sortiment);
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public boolean deleteSortiment(UUID id) {
        if (id == null) {
            return false;
        }
        List<Sortiment> query = this.eManager
                .createNamedQuery("Sortiment.getSortimentById", Sortiment.class)
                .setParameter("id", id)
                .getResultList();
        if (query.isEmpty()) {
            return false;
        }
        Sortiment result = query.get(0);
        if (result == null) {
            return false;
        }
        eManager.remove(result);
        return true;
    }

}
