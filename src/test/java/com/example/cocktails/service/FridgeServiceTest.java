package com.example.cocktails.service;

import com.example.cocktails.*;
import com.example.cocktails.repository.*;
import com.google.inject.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class FridgeServiceTest {

    public static Injector getReleaseInjector() {
        return CocktailApp.getReleaseInjector();
    }

    public static Injector getTestInjector() {
        return Guice.createInjector(new TestModule());
    }

    @Test
    void getPossibleCocktails1() throws IOException {
        Collection<Cocktail> expectedList = List.of(new Cocktail(27L, "Pink Power"));

        Injector injector = getTestInjector();

        CocktailService cocktailService = injector.getInstance(CocktailService.class);
        FridgeService fridgeService = injector.getInstance(FridgeService.class);

        Ingredient ingredient1 = cocktailService.getIngredientWithID(7L);
        Ingredient ingredient2 = cocktailService.getIngredientWithID(30L);

        fridgeService.addIngredient(ingredient1);
        fridgeService.addIngredient(ingredient2);

        Collection<Cocktail> actualList = fridgeService.getPossibleCocktails();

        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void getPossibleCocktails2() throws IOException {
        Collection<Cocktail> expectedList = List.of(new Cocktail(3L, "Summerfeeling"), new Cocktail(27L, "Pink Power"));

        Injector injector = getReleaseInjector();

        CocktailService cocktailService = injector.getInstance(CocktailService.class);
        FridgeService fridgeService = injector.getInstance(FridgeService.class);

        LongStream.of(9, 30, 7, 8)
                .mapToObj(cocktailService::getIngredientWithID)
                .forEach(fridgeService::addIngredient);

        Collection<Cocktail> actualList = fridgeService.getPossibleCocktails();

        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }

}
