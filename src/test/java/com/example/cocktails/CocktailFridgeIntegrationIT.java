package com.example.cocktails;

import io.restassured.*;
import io.restassured.filter.session.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.server.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CocktailFridgeIntegrationIT {

    @LocalServerPort
    private int port;

    private SessionFilter session;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        session = new SessionFilter();
    }

    @Test
    void testGetFridgeIngredients() {
        given()
                .filter(session)
                .when()
                .get("/api/fridge/ingredients")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void testPossibleContainsPinkPowerForIngredients7And30() {
        // TODO Die Zutaten mit den IDs 7, 30 zum Kühlschrank hinzufügen und testen, ob der "Pink Power" damit möglich ist
    }

}
