package com.example.cocktails.service;

import com.example.cocktails.*;
import com.example.cocktails.repository.*;
import com.google.inject.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FridgeServiceTest {

    public static Injector getReleaseInjector() {
        return CocktailApp.getReleaseInjector();
    }

    public static Injector getTestInjector() {
        return Guice.createInjector(new TestModule());
    }

    @Test
    void getPossibleCocktails() throws IOException {
        Collection<Cocktail> expected = List.of(new Cocktail(27L, "Pink Power"));

        Injector injector = getTestInjector();

        CocktailService cocktailService = injector.getInstance(CocktailService.class);
        FridgeService fridgeService = injector.getInstance(FridgeService.class);

        Ingredient ingredient1 = cocktailService.getIngredientWithID(7L);
        Ingredient ingredient2 = cocktailService.getIngredientWithID(30L);

        fridgeService.addIngredient(ingredient1);
        fridgeService.addIngredient(ingredient2);

        Collection<Cocktail> actual = fridgeService.getPossibleCocktails();

        assertIterableEquals(expected, actual);
    }

}