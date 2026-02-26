package com.example.cocktails;

import io.restassured.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.server.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CocktailServiceApplicationRestControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void testGetAllCocktails() {
        given()
                .when()
                .get("/api/cocktails")
                .then()
                .statusCode(200)
                .body("size()", equalTo(69));
    }

    @Test
    void testGetCocktailById() {
        // TODO Testfall: GET /api/cocktails/{id}
        // Abnahmekriterien:
        // 1) Bei gueltiger ID kommt HTTP 200.
        // 2) Die Antwort enthaelt mindestens die Felder "name" und "instructions".
        // 3) Bonus: Bei ungueltiger ID kommt HTTP 404.
    }

    @Test
    void testSearchCocktails() {
        // TODO Testfall: GET /api/cocktails/search?query=...
        // Abnahmekriterien:
        // 1) Bei query mit Treffer kommt HTTP 200 und mindestens ein Ergebnis.
        // 2) Bei leerer query kommt HTTP 200 und eine fachlich sinnvolle Antwort (z. B. alle Cocktails).
        // 3) Bonus: Jeder Treffer passt zum Suchbegriff (Name oder Zutat), ohne Duplikate.
    }

    @Test
    void testPostPossibleRecipes() {
        // TODO Testfall: POST /api/possible
        // Abnahmekriterien:
        // 1) Bei gueltigem JSON-Body mit ingredientIDs kommt HTTP 200.
        // 2) Die Antwort enthaelt nur Cocktails, die mit den uebergebenen Zutaten moeglich sind.
        // 3) Bonus: Bei leerer ingredientIDs-Liste ist die Antwort leer (HTTP 200).
    }

    @Test
    void testGetAllIngredients() {
        // TODO Testfall: GET /api/ingredients
        // Abnahmekriterien:
        // 1) HTTP 200.
        // 2) Antwortliste ist nicht leer.
        // 3) Bonus: Elemente enthalten mindestens id und name und sind stabil sortiert.
    }

    @Test
    void testGetIngredientById() {
        // TODO Testfall: GET /api/ingredients/{id}
        // Abnahmekriterien:
        // 1) Bei gueltiger ID kommt HTTP 200 mit passender ID.
        // 2) Bei ungueltiger ID kommt HTTP 404.
        // 3) Bonus: Die Antwort enthaelt einen nicht-leeren Namen.
    }

    @Test
    void testGetCocktailsByIngredient() {
        // TODO Testfall: GET /api/ingredients/{id}/cocktails
        // Abnahmekriterien:
        // 1) Bei gueltiger Ingredient-ID kommt HTTP 200.
        // 2) Antwort enthaelt das Feld "cocktails".
        // 3) Bonus: Bei ungueltiger Ingredient-ID kommt HTTP 404.
    }
}
