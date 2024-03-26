/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Sortiment.boundary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import de.hsos.swa.Sortiment.boundary.DTO.SortimentAddDTO;
import de.hsos.swa.Sortiment.boundary.DTO.SortimentDTO;
import de.hsos.swa.Sortiment.control.SortimentService;
import de.hsos.swa.Sortiment.entity.Sortiment;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Tag(name = "Products Management", description = "API endpoints for the product management")
@Path("/sortiment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SortimentRessource {

    @Inject
    SortimentService sortimentService;

    @Inject
    Template products;

    @Inject
    SecurityIdentity secIdentity;

    //@GET
    @Path("/gui")
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getAllSortimentUI(){
        List<Sortiment> sortiments = this.sortimentService.getAllSortiment();

        List<SortimentDTO> erg = sortiments.stream()
                                .map(SortimentDTO.Converter::newSortiment)
                                .toList();
        
        return products.data("erg",erg);
    }




    @GET
    @Operation(summary = "get all products (no authentication needed)")
    @PermitAll
    @Transactional(TxType.REQUIRES_NEW)
    public Response getAllSortiment(){
        List<Sortiment> sortiment = this.sortimentService.getAllSortiment();

        if(sortiment.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("no products available")
                    .build();
        }

        List<SortimentDTO> erg = sortiment.stream()
                                .map(SortimentDTO.Converter::newSortiment)
                                .toList();
        
        return Response.ok().entity(erg).build();
    }

    @GET
    @Operation(summary = "get a product with ID (only admin)")
    @Path("getSortimentmitId/{id}")
    @RolesAllowed({"admin"})
    @Transactional(TxType.REQUIRES_NEW)
    public Response getSortiment(@PathParam("id") UUID sortimentid){
        Optional<Sortiment> erg = this.sortimentService.getSortiment(sortimentid);

        if(!erg.isPresent()){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("no products with given Id available")
                    .build();
        }

        SortimentDTO sortiment = SortimentDTO.Converter.newSortiment(erg.get());
        return Response.ok().entity(sortiment).build();
    }

    @POST
    @Operation(summary = "add a product (only admin)")
    @RolesAllowed({"admin"})
    @Transactional(TxType.REQUIRES_NEW)
    public Response addSortiment(SortimentAddDTO sortimentAddDTO){
        Optional<Sortiment> sortiment = this.sortimentService.addSortiment(SortimentAddDTO.createSortiment(sortimentAddDTO));

        if(!sortiment.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("cannot add the product")
                    .build();
        }

        SortimentDTO erg = SortimentDTO.Converter.newSortiment(sortiment.get());

        return Response.ok().entity(erg).build();
    }

    @DELETE
    @Operation(summary = "delete product with Id (only admin)")
    @Path("/{id}")
    @RolesAllowed({"admin"})
    @Transactional(TxType.REQUIRES_NEW)
    public Response deleteSortiment(@PathParam("id") UUID sortimentId){
        if(!this.sortimentService.deleteSortiment(sortimentId)){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("delete product cannot be processed!")
                    .build();
        }

        return Response.ok().build();
    }


}
