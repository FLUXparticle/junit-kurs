package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FridgeServiceTest {

    @Mock
    private CocktailService cocktailService;

    @InjectMocks
    private FridgeService fridgeService;

    @Test
    void testGetFridgeIngredients() {
        Ingredient ingredient1 = new Ingredient(1L, "Rum");
        Ingredient ingredient2 = new Ingredient(2L, "Minze");

        when(cocktailService.getAllIngredients()).thenReturn(List.of(ingredient1, ingredient2));

        List<FridgeIngredient> ingredients = fridgeService.getFridgeIngredients();
        assertEquals(2, ingredients.size());
        assertEquals("Minze", ingredients.get(0).getName());
        assertFalse(ingredients.get(0).isInFridge());
        assertEquals("Rum", ingredients.get(1).getName());
        assertFalse(ingredients.get(1).isInFridge());
    }

    @Test
    void testGetPossibleCocktails() {
        Ingredient ingredient = new Ingredient(1L, "Rum");
        when(cocktailService.getIngredientWithID(1L)).thenReturn(ingredient);

        Cocktail cocktail = new Cocktail(1L, "Mojito");
        when(cocktailService.getPossibleCocktails(anySet())).thenReturn(List.of(cocktail));

        fridgeService.updateIngredientStatus(1L, true);

        List<Cocktail> cocktails = fridgeService.getPossibleCocktails();
        assertEquals(1, cocktails.size());
        assertEquals(cocktail, cocktails.get(0));
        verify(cocktailService).getPossibleCocktails(Set.of(1L));
    }

}
