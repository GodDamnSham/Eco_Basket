/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Sortiment.control;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Sortiment.entity.Sortiment;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface SortimentService {

    public List<Sortiment> getAllSortiment();
    public Optional<Sortiment> getSortiment(UUID id);
    public Optional<Sortiment> addSortiment(Sortiment sortiment);
    public boolean deleteSortiment(UUID id);


    
    
}