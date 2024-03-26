package de.hsos.swa.Liquibase;

import org.eclipse.microprofile.openapi.annotations.Operation;

import io.quarkus.liquibase.LiquibaseFactory;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import liquibase.Liquibase;

/*
 * @Author: Finn Christophersen
 */
@ApplicationScoped
@Path("/liquibase")
public class MigrationService {
    @Inject
    LiquibaseFactory liquibaseFactory; 

    
    @GET
    @Operation(summary = "get request for making a change in the database. Only admin allowed.")
    @RolesAllowed("admin")
    @Transactional(TxType.REQUIRES_NEW)
    public Response updateMigration() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.update();
            return Response.ok().build();
        }catch (Exception e) {
            return Response.noContent().build();
        }
    }
}