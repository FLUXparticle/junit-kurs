package com.example.cocktails.service;

import com.example.cocktails.repository.*;
import com.google.inject.*;

import java.util.*;

import static java.util.stream.Collectors.*;

public class FridgeService {

    private final CocktailService cocktailService;

    private final SortedSet<Ingredient> fridge;

    @Inject
    public FridgeService(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
        fridge = new TreeSet<>();
    }

    public void addIngredient(Ingredient ingredient) {
        fridge.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        fridge.remove(ingredient);
    }

    public Set<Ingredient> getFridgeIngredients() {
        return fridge;
    }

    public Collection<Cocktail> getPossibleCocktails() {
        Set<Long> fridgeIngredientIDs = getFridgeIngredients().stream()
                .map(Ingredient::getId)
                .collect(toSet());

        return cocktailService.getAllCocktailsWithIngredients(fridgeIngredientIDs).stream()
                .filter(cocktail -> cocktail.getInstructions().stream()
                        .map(Instruction::getIngredient)
                        .map(Ingredient::getId)
                        .allMatch(fridgeIngredientIDs::contains)
                )
                .collect(toList());
    }

    public Set<Ingredient> getShoppingList() {
        Collection<Ingredient> allIngredients = cocktailService.getAllIngredients();
        Collection<Ingredient> fridgeIngredients = getFridgeIngredients();

        Set<Ingredient> missing = new TreeSet<>(allIngredients);
        missing.removeAll(fridgeIngredients);

        return missing;
    }

}
