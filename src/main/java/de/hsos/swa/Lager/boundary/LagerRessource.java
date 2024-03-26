package de.hsos.swa.Lager.boundary;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import de.hsos.swa.Lager.boundary.DTO.AddBestandDTO;
import de.hsos.swa.Lager.boundary.DTO.LagerDTO;
import de.hsos.swa.Lager.boundary.DTO.VerschiebeBestandDTO;
import de.hsos.swa.Lager.control.LagerService;
import de.hsos.swa.Lager.entity.Lager;
import io.quarkus.security.Authenticated;
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

/*
 * @Author: Finn Christophersen
 */
@Tag(name = "lager Management", description = "API endpoints for lager management")
@Path("/Lager")
@ApplicationScoped
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LagerRessource {

    @Inject
    LagerService lagerService;
    
    @GET
    @Path("/{lagerid}")
    @RolesAllowed("admin")
    @Operation(summary = "get lager with his Id (only admin)")
    @Transactional(TxType.REQUIRES_NEW)
    public Response getLager(@PathParam("lagerid") UUID lagerid){
        Optional<Lager> lager = lagerService.getLager(lagerid);

        if(!lager.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No lager with the id found")
                    .build();
        }

        LagerDTO lagerdto = LagerDTO.Converter.newLagerDTO(lager.get());
        
        return Response.ok().entity(lagerdto).build();
    }

    @POST
    @Path("/{ort}")
    @RolesAllowed("admin")
    @Operation(summary = "create lager with the ort (only admin)")
    @Transactional(TxType.REQUIRES_NEW)
    public Response addLager(@PathParam("ort") String ort){
        
        Optional<Lager> lager = this.lagerService.addLager(ort);

        LagerDTO erg = LagerDTO.Converter.newLagerDTO(lager.get());

        return Response.ok().entity(erg).build();
    }

    @POST
    @Path("/Bestand/{lagerid}")
    @RolesAllowed("admin")
    @Operation(summary = "add a bestand with to the lager with the id (only admin)")
    @Transactional(TxType.REQUIRES_NEW)
    public Response addBestandZuLager(@PathParam("lagerid") UUID lagerid, AddBestandDTO produktDTO){
        Optional<Lager> lager = this.lagerService.fuegeBestandhinzu(lagerid, produktDTO.getSortimentid(), produktDTO.getAnzahlSortiment());

        if(!lager.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Input Data is wrong or Bestand already exist")
                .build();
        }

        LagerDTO rueckgabe = LagerDTO.Converter.newLagerDTO(lager.get());
        
        return Response.ok().entity(rueckgabe.toString()).build();
    }

    @PUT
    @Path("/{lagerid}")
    @RolesAllowed("admin")
    @Operation(summary = "update a bestand with to the lager with the id (only admin)")
    @Transactional(TxType.REQUIRES_NEW)
    public Response updateBestandImLager(@PathParam("lagerid") UUID lagerid, AddBestandDTO produktDTO){
        Optional<Lager> lager = this.lagerService.updateBestandVonLager(lagerid, produktDTO.getSortimentid(), produktDTO.getAnzahlSortiment());

        if(!lager.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Input Data is wrong or Bestand doesn't exist")
                    .build();
        }

        LagerDTO rueckgabe = LagerDTO.Converter.newLagerDTO(lager.get());
        
        return Response.ok().entity(rueckgabe.toString()).build();
    }

    @PUT
    @Path("/{lageridVon}/{lageridZu}")
    @RolesAllowed("admin")
    @Operation(summary = "shift the number of bestand from one lager to another (only admin)")
    @Transactional(TxType.REQUIRES_NEW)
    public Response verschiebeProduktVonLagerZuLager(@PathParam("lageridVon") UUID lageridVon, @PathParam("lageridZu") UUID lageridZu, VerschiebeBestandDTO produktDTO){
        Optional<Lager> lager = this.lagerService.updateBestandVonLagerZuLager(lageridVon, lageridZu, produktDTO.getSortimentid(), produktDTO.getAnzahlSortiment());
        
        if(!lager.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("Input Data is wrong or not enough Bestand in LagerVon")
            .build();
        }

        LagerDTO rueckgabe = LagerDTO.Converter.newLagerDTO(lager.get());
        
        return Response.ok().entity(rueckgabe.toString()).build();
    }
}
