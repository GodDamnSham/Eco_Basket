package de.hsos.swa.Warenkorb.boundary;

import java.util.Optional;
import de.hsos.swa.Warenkorb.boundary.DTO.AddPositionDTO;
import de.hsos.swa.Warenkorb.boundary.DTO.WarenkorbDTO;
import de.hsos.swa.Warenkorb.control.WarenkorbServiceInter;
import de.hsos.swa.Warenkorb.entity.Warenkorb;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


/*
 * @Author: Finn Christophersen + Malik Riaz
 */
@Tag(name = "basket Management", description = "API endpoints for basket management")
@Path("/Warenkorb")
@ApplicationScoped
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarenkorbRessource {
    @Inject
    SecurityIdentity secIdentity;

    @Inject
    JsonWebToken jwt;

    @Inject
    WarenkorbServiceInter warenkorbService;

    @GET
    @PermitAll
    @Operation(summary = "get the warenkorb for the costumer with the username")
    @Transactional(TxType.REQUIRES_NEW)
    public Response getWarenkorb(){
        String getLoggedInUserName = this.secIdentity.getPrincipal().getName();
        String kundeID = jwt.getClaim("sub").toString();
        if(getLoggedInUserName.equals("sham")){
            Response.status(Response.Status.BAD_REQUEST)
            .entity("Admin dont have Warenkorb")
            .build();
        }
        Optional<Warenkorb> warenkorb = this.warenkorbService.getWarenkorb(kundeID);

        if(!warenkorb.isPresent()){
            //Dieser Fall sollte niemals eintreten
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("Username doesn't have a Warenkorb")
            .build();
        }
        WarenkorbDTO rueckgabe = WarenkorbDTO.Converter.newWarenkorbDTO(warenkorb.get());

        return Response.ok().entity(rueckgabe.toString()).build();
    }

    @PUT
    @Path("/{kundenid}")
    @RolesAllowed("user")
    @Operation(summary = "add a position for a warenkorb with the id")
    @Transactional(TxType.REQUIRES_NEW)
    public Response addPosition(@PathParam("kundenid") String kundenid, AddPositionDTO positionDTO){
        Optional<Warenkorb> warenkorb = this.warenkorbService.fuegePositionhinzu(kundenid, positionDTO.getSortimentId(), positionDTO.getAnzahlSortiment());

        if(!warenkorb.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("Input Data is wrong or Osition already exist")
            .build();
        }

        WarenkorbDTO rueckgabe = WarenkorbDTO.Converter.newWarenkorbDTO(warenkorb.get());
        
        return Response.ok().entity(rueckgabe.toString()).build();
    }

    @POST
    @Path("/{kundenid}")
    @RolesAllowed("user")
    @Transactional(TxType.REQUIRES_NEW)
    @Operation(summary = "update a position for a warenkorb with the id")
    public Response updatePosition(@PathParam("kundenid") String kundenid, AddPositionDTO positionDTO){
        Optional<Warenkorb> warenkorb = this.warenkorbService.updatePosition(kundenid, positionDTO.getSortimentId(), positionDTO.getAnzahlSortiment());

        if(!warenkorb.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("Input Data is wrong or Position doesn't exist")
            .build();
        }

        WarenkorbDTO rueckgabe = WarenkorbDTO.Converter.newWarenkorbDTO(warenkorb.get());
        
        return Response.ok().entity(rueckgabe.toString()).build();
    }
    
}
