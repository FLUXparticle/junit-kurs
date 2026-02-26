package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class CocktailServiceWithFakesTest {

    @Test
    void searchFindsCocktailViaIngredientName_usingFakeRepositories() {
        Ingredient mint = new Ingredient(1L, "Minze");
        Cocktail mojito = new Cocktail("Mojito", List.of(new Instruction(null, mint)));

        CocktailRepository cocktailRepository = new FakeCocktailRepository(List.of(mojito));
        IngredientRepository ingredientRepository = new FakeIngredientRepository(List.of(mint));

        CocktailService cocktailService = new CocktailService(cocktailRepository, ingredientRepository);
        Collection<Cocktail> result = cocktailService.search("Minze");

        assertThat(result).containsExactly(mojito);
    }

    @Test
    void searchWithBlankQueryReturnsAllCocktails_usingFakeRepositories() {
        Cocktail mojito = new Cocktail(1L, "Mojito");
        Cocktail pinkPower = new Cocktail(2L, "Pink Power");

        CocktailRepository cocktailRepository = new FakeCocktailRepository(List.of(mojito, pinkPower));
        IngredientRepository ingredientRepository = new FakeIngredientRepository(List.of());

        CocktailService cocktailService = new CocktailService(cocktailRepository, ingredientRepository);
        Collection<Cocktail> result = cocktailService.search(" ");

        assertThat(result).containsExactlyInAnyOrder(mojito, pinkPower);
    }

}
