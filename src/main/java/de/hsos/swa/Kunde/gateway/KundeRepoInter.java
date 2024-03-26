/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Kunde.gateway;

import java.util.List;
import java.util.Optional;

import de.hsos.swa.Kunde.entity.Adresse;
import de.hsos.swa.Kunde.entity.Kunde;



public interface KundeRepoInter {
    public List<Kunde> getAllKunde();
    public boolean kundeAddAdress(String kundenId , Adresse adresse);
    public Optional<Kunde> addKunde(Kunde kunde);
    public Optional<Kunde> getKunde(String id);
    public Optional<Adresse> updateAdresse(String id, Adresse adresse);
    public Optional<Adresse> getKundenAdress(String id);
    public Boolean deleteKunde(String id);

}
