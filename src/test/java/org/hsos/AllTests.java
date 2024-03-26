/*
 * @Author:  Malik Shameer Riaz
 */

package org.hsos;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hsos.swa.Kunde.boundary.DTO.KundeDTO;
import de.hsos.swa.Kunde.entity.Kunde;
import de.hsos.swa.Lager.boundary.DTO.AddBestandDTO;
import de.hsos.swa.Sortiment.boundary.DTO.SortimentAddDTO;
import de.hsos.swa.Sortiment.entity.Sortiment;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

@QuarkusTest
public class AllTests {

        private String accessTokenAdmin;
        private String accessTokenUser;

        @BeforeEach
        public void setup() {
                String tokenUrl = "http://localhost:8180/realms/dev/protocol/openid-connect/token";
                String clientId = "eco-basket";
                String clientSecret = "**********";
                String username = "admin";
                String password = "admin";

                Response response = given()
                                .formParam("grant_type", "password")
                                .formParam("client_id", clientId)
                                .formParam("client_secret", clientSecret)
                                .formParam("username", username)
                                .formParam("password", password)
                                .when()
                                .post(tokenUrl);

                accessTokenAdmin = response.jsonPath().getString("access_token");
                // System.out.println(accessToken);
        }

        @BeforeEach
        public void setup1() {
                String tokenUrl = "http://localhost:8180/realms/dev/protocol/openid-connect/token";
                String clientId = "eco-basket";
                String clientSecret = "**********";
                String username = "user";
                String password = "user";

                Response response = given()
                                .formParam("grant_type", "password")
                                .formParam("client_id", clientId)
                                .formParam("client_secret", clientSecret)
                                .formParam("username", username)
                                .formParam("password", password)
                                .when()
                                .post(tokenUrl);

                accessTokenUser = response.jsonPath().getString("access_token");
                // System.out.println(accessTokenUser);
        }

        @Test
        public void TokensArePresent() {
                assertNotNull("Token not null admin", this.accessTokenAdmin);
                assertNotNull("Token not null User", this.accessTokenUser);

        }

        @Test
        public void addKundeTest() {
                KundeDTO kundeDTO = new KundeDTO("test", "test", "test", "test@gmx.com");
                given()
                                .contentType(ContentType.JSON)
                                .body(kundeDTO)
                                .when()
                                .post("/ecobasket/Kunde")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void getAllKundeTestOnlyAdmin() {
                List<Kunde> kundeList = given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .when()
                                .get("/ecobasket/Kunde")
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .jsonPath().getList(".", Kunde.class);
                assertTrue(kundeList.size() > 0, "The list of Kunde is empty.");
        }

        @Test
        public void getAllKundeTestOnlyUnAuthorized() {
                given()
                                .auth().preemptive().oauth2(accessTokenUser)
                                .when()
                                .get("/ecobasket/Kunde")
                                .then()
                                .statusCode(403);

        }

        @Test
        public void testAddAndGetKunde() {
                KundeDTO kundeDTO = new KundeDTO("test1", "test1", "test1", "test1@gmx.com");
                Response postResponse = given()
                                .contentType(ContentType.JSON)
                                .body(kundeDTO)
                                .when()
                                .post("/ecobasket/Kunde")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response();

                String addedKundeId = postResponse.jsonPath().getString("id");

                List<Kunde> kundeList = given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .when()
                                .get("/ecobasket/Kunde")
                                .then()
                                .statusCode(200)
                                .contentType(ContentType.JSON)
                                .extract()
                                .body()
                                .jsonPath().getList(".", Kunde.class);

                boolean found = false;
                for (Kunde kunde : kundeList) {
                        if (kunde.getId().equals(addedKundeId)) {
                                found = true;
                                break;
                        }
                }

                assertTrue(found, "The added Kunde was not found in the list.");
        }

        @Test
        public void addSortimentTest() {
                SortimentAddDTO sortimentAddDTO = new SortimentAddDTO("milch", 2.3f);
                given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .contentType(ContentType.JSON)
                                .body(sortimentAddDTO)
                                .when()
                                .post("/ecobasket/sortiment")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void addSortimentUserNotAllowedTest() {
                SortimentAddDTO sortimentAddDTO = new SortimentAddDTO("milch", 2.3f);
                given()
                                .auth().preemptive().oauth2(accessTokenUser)
                                .contentType(ContentType.JSON)
                                .body(sortimentAddDTO)
                                .when()
                                .post("/ecobasket/sortiment")
                                .then()
                                .statusCode(403);
        }

        @Test
        public void getAllSortimentTestOnlyAdmin() {
                List<Sortiment> sList = given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .when()
                                .get("/ecobasket/sortiment")
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .jsonPath().getList(".", Sortiment.class);
                assertTrue(sList.size() > 0, "The list of Kunde is empty.");
        }

        @Test
        public void SortimentAddGetDeleteFlowTest() {
                SortimentAddDTO sortimentAddDTO = new SortimentAddDTO("Resi", 20.3f);
                String sortimentId = null;
                given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .contentType(ContentType.JSON)
                                .body(sortimentAddDTO)
                                .when()
                                .post("/ecobasket/sortiment")
                                .then()
                                .statusCode(200)
                                .extract()
                                .jsonPath().getString("id");

                List<Sortiment> sList = given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .when()
                                .get("/ecobasket/sortiment")
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .jsonPath().getList(".", Sortiment.class);

                for (Sortiment sor : sList) {
                        sortimentId = sor.getID().toString();
                }

                System.out.println(sortimentId);
                given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .when()
                                .delete("/ecobasket/sortiment/{id}", sortimentId)
                                .then()
                                .statusCode(200);

        }

        @Test
        public void createLagerAddBestandFlowTest() {
                UUID sortimentIdForLager = null;
                SortimentAddDTO sortimentAddDTO = new SortimentAddDTO("plants", 28.3f);

                String lagerId = given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .contentType(ContentType.JSON)
                                .when()
                                .post("/ecobasket/Lager/{ort}", "Osna")
                                .then()
                                .statusCode(200)
                                .extract()
                                .jsonPath().getString("id");

                given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .contentType(ContentType.JSON)
                                .body(sortimentAddDTO)
                                .when()
                                .post("/ecobasket/sortiment")
                                .then()
                                .statusCode(200)
                                .extract()
                                .jsonPath().getString("id");

                List<Sortiment> sList = given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .when()
                                .get("/ecobasket/sortiment")
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .jsonPath().getList(".", Sortiment.class);

                for (Sortiment sor : sList) {
                        sortimentIdForLager = sor.getID();
                }

                AddBestandDTO addBestandDTO = new AddBestandDTO(sortimentIdForLager, 20);
                given()
                                .auth().preemptive().oauth2(accessTokenAdmin)
                                .contentType(ContentType.JSON)
                                .body(addBestandDTO)
                                .when()
                                .post("/ecobasket/Lager/Bestand/{lagerid}", lagerId)
                                .then()
                                .statusCode(200);

        }

}
