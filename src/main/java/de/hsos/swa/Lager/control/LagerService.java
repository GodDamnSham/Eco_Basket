package de.hsos.swa.Lager.control;

import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Lager.entity.Lager;

/*
 * @Author: Finn Christophersen
 */
public interface LagerService {
    public Optional<Lager> getLager(UUID lagerid);

    public Optional<Lager> updateBestandVonLager(UUID lagerid, UUID sortimentid, Integer anzahl);

    public Optional<Lager> updateBestandVonLagerZuLager(UUID lagerVon, UUID lagerZu, UUID sortimentid, Integer anzahl);

    public Optional<Lager> fuegeBestandhinzu(UUID idLager, UUID sortimentid, Integer anzahlSortiment);
    public Optional<Lager> addLager(String ort);


}
