package de.hsos.swa.Warenkorb.gateway;

import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Warenkorb.entity.Warenkorb;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
public interface WarenKorbRepoI {
    public Optional<Warenkorb> getWarenkorb(String kundenid);
    public Optional<Warenkorb> addWarenkorb(String kundenid);
    public Optional<Warenkorb> fuegePositionhinzu(String kundenid, UUID idpizza, Integer anzahlpizza);
    public Optional<Warenkorb> updatePosition(String kundenid, UUID idpizza, Integer anzahlpizza);
    public boolean deleteWarenkorb(String kundenid);

}
