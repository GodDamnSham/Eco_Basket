/*
 * @Author:  Malik  
 */

package de.hsos.swa.Kunde.control.Keycloak;

import java.util.Collections;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import de.hsos.swa.Kunde.boundary.DTO.KundeDTO;

import org.jboss.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class KeycloakService {

    final Logger LOGGER = Logger.getLogger(KeycloakService.class);

    @Inject
    Keycloak keycloak;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.server-url")
    String keycloakUrl;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.realm")
    String realm;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.client-id")
    String clientID;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.grant-type")
    String grantType;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.username")
    String username;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.password")
    String passKey;

    @Inject
    @ConfigProperty(name = "quarkus.keycloak.admin-client.client-secret")
    String secret;

    @PostConstruct
    public void initKeycloak() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm)
                .clientId(clientID)
                .clientSecret(secret)
                .username(username)
                .password(passKey)
                .grantType(grantType)
                .build();
    }

    @PreDestroy
    public void closeKeycloak() {
        keycloak.close();
    }

    public UserRepresentation createUserRepresentation(KundeDTO kundeDTO, String newUsername, String newEmail,
            Boolean enabled,
            Boolean v) {

        UserRepresentation user = new UserRepresentation();
        user.setUsername(newUsername);
        user.setFirstName(kundeDTO.getVorname());
        user.setLastName(kundeDTO.getNachname());
        user.setEmail(newEmail);
        user.setEnabled(enabled);
        user.setEmailVerified(v);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newUsername);
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));

        return user;
    }

    public Response createUserInKeycloak(UserRepresentation user) {
        UsersResource usersResource = keycloak.realm(realm).users();
        return usersResource.create(user);
    }

    public UserRepresentation getCreatedUser(String newUsername) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation users = usersResource.search(newUsername).get(0);

        if (users==null) {
            LOGGER.info("User not found");
            return null;
        }

        return users;
    }

    public void assignUserRole(UserRepresentation user) {
        
        keycloak.realm(realm).users().get(user.getId()).roles().realmLevel().
        add(Collections.singletonList(keycloak.realm(realm).roles().
        get("user").toRepresentation()));
        LOGGER.info("Role 'user' assigned to user: " + user.getUsername() + " " + user.getId());
    }

    public boolean deleteUser(String userId) {
        if(userId == null){
            return false;
        }
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.delete(userId);

        LOGGER.info("User deleted successfully: " + userId);
        return true;
    }
}



