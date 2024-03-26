package de.hsos.swa.Warenkorb.control;

import java.util.Optional;
import java.util.UUID;

import de.hsos.swa.Warenkorb.entity.Warenkorb;

/*
 * @Author: Finn Christophersen + Malik Riaz
 */
public interface WarenkorbServiceInter {
    public Optional<Warenkorb> getWarenkorb(String kundenid);
    public Optional<Warenkorb> fuegePositionhinzu(String kundenid, UUID idpizza, Integer anzahlpizza);
    public Optional<Warenkorb> updatePosition(String kundenid, UUID idpizza, Integer anzahlpizza);
}
