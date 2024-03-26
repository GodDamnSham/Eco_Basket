/*
 * @Author:  Malik & Finn 
 */

package de.hsos.swa.Kunde.boundary;

import java.util.List;
import java.util.Optional;
import de.hsos.swa.Kunde.boundary.DTO.AdresseDTO;
import de.hsos.swa.Kunde.boundary.DTO.KundeDTO;
import de.hsos.swa.Kunde.control.KundeServiceInter;
import de.hsos.swa.Kunde.control.Keycloak.KeycloakService;
import de.hsos.swa.Kunde.entity.Adresse;
import de.hsos.swa.Kunde.entity.Kunde;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.commons.validator.routines.EmailValidator;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.NoCache;
import org.keycloak.representations.idm.UserRepresentation;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Tag(name = "Customer Management", description = "API endpoints for customer management")
@Path("/Kunde")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeRessource {
    final Logger LOGGER = Logger.getLogger(KundeRessource.class);

    @Inject
    SecurityIdentity secIdentity;

    @Inject
    JsonWebToken jwt;

    @Inject
    KundeServiceInter kundeService;

    @Inject
    KeycloakService keycloakService;



    @PostConstruct
    void init() {
        KundeDTO kundeDTO = new KundeDTO("firstname", "lastname", "testuser", "testuser@gmx.com");
        this.addAccount(kundeDTO);
    }

    @POST
    @Operation(summary = "add an account (no authentication needed)")
    @PermitAll
    @NoCache
    public Response addAccount(KundeDTO kundeDTO) {
        Kunde k = KundeDTO.DTOToKunde(kundeDTO);
        String newUsername = kundeDTO.getUsername();
        String newEmail = kundeDTO.getEmail();
        if (!EmailValidator.getInstance().isValid(newEmail)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid email format")
                    .build();
        }
        Boolean enabled = kundeDTO.getEnabled();
        Boolean verified = true;
        UserRepresentation user = keycloakService.createUserRepresentation(kundeDTO, newUsername, newEmail, enabled,
                verified);
        keycloakService.createUserInKeycloak(user);
        UserRepresentation createdUser = keycloakService.getCreatedUser(newUsername);
        if (createdUser == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("user not created")
                    .build();
        }
        keycloakService.assignUserRole(createdUser);

        k.setId(createdUser.getId());
        Optional<Kunde> result = this.kundeService.addKunde(k);

        if (!result.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("user creation failed")
                    .build();
        }

        return Response.ok().entity(k).build();

    }



    @GET
    @Operation(summary = "get all active costumer (only admin) ")
    @RolesAllowed("admin")
    @NoCache
    public List<Kunde> getAllKunde() {
        List<Kunde> k = this.kundeService.getAllKunde();
        if (k == null) {
            return null;
        }
        return this.kundeService.getAllKunde();
    }


    @PUT
    @Operation(summary = "add account adress for the costumer (only user)")
    @Path("/addAccountAddress")
    @RolesAllowed("user")
    @NoCache
    public Response addAccountAdress(AdresseDTO adresseDTO) {
        String kundeID = jwt.getClaim("sub").toString();
        Adresse a = AdresseDTO.DTOToAdresse(adresseDTO);
        boolean updated = this.kundeService.kundeAddAdress(kundeID, a);

        if (updated == false) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("cannot create account address")
                    .build();
        }
        return Response.ok().entity(updated).build();
    }


    @PUT
    @Operation(summary = "update account adress for the costumer (only admin)")
    @RolesAllowed("user")
    @Path("/updateAccountAddress")
    @NoCache
    public Response updateAdresse( AdresseDTO adresseDTO) {
        String kundeID = jwt.getClaim("sub").toString();
        Adresse a = AdresseDTO.DTOToAdresse(adresseDTO);
        Optional<Adresse> updated = this.kundeService.updateAdresse(kundeID, a);

        if (!updated.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("cannot update account address")
                    .build();
        }
        return Response.ok().entity(updated).build();
    }

    @GET
    @Operation(summary = "get account address of the costumer with his Id (admin and user both)")
    @Path("/getAdress/{kundenId}")
    @RolesAllowed({ "admin", "user" })
    @NoCache
    public Response getKundenAdress(@PathParam("kundenId") String id) {
        Optional<Adresse> a = this.kundeService.getKundenAdress(id);
        if (!a.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("no address with the given user found")
                    .build();
        }
        return Response.ok().entity(a).build();
    }


    @GET
    @Operation(summary = "get costumer with his Id (only admin)")
    @Path("/getEineKunde/{kundenId}")
    @RolesAllowed("admin")
    @NoCache
    public Response getKunde(@PathParam("kundenId") String id) {
        Optional<Kunde> k = this.kundeService.getKunde(id);
        if (!k.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND)
            .entity("no costumer found with given ID")
            .build();
        }
        return Response.ok().entity(k).build();
    }

    @DELETE
    @Operation(summary = "delete the costumer his Id (only admin)")
    @Path("/{kundenId}")
    @RolesAllowed("admin")
    @NoCache
    public Response deleteKunde(@PathParam("kundenId") String id) {
        boolean keycloakRemoveKunde = this.keycloakService.deleteUser(id);
        if(!keycloakRemoveKunde){
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("delete costumer did not worked")
            .build();
        }
        boolean k = this.kundeService.deleteKunde(id);
        if (k == false) {
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("delete costumer did not worked")
            .build();
        }
        return Response.ok().entity(k).build();
    }

}
