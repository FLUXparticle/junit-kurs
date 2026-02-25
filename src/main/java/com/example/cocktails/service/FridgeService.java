package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.annotation.*;

import java.util.*;

@Service
@SessionScope
public class FridgeService {

    private final CocktailService cocktailService;
    private final Set<Long> fridge = new LinkedHashSet<>();

    public FridgeService(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    public List<FridgeIngredient> getFridgeIngredients() {
        Set<Long> fridgeIds = new HashSet<>(fridge);
        return cocktailService.getAllIngredients().stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .map(ingredient -> new FridgeIngredient(
                        ingredient.getId(),
                        ingredient.getName(),
                        fridgeIds.contains(ingredient.getId())
                ))
                .toList();
    }

    public void updateIngredientStatus(Long ingredientId, boolean inFridge) {
        if (cocktailService.getIngredientWithID(ingredientId) == null) {
            return;
        }
        if (inFridge) {
            fridge.add(ingredientId);
        } else {
            fridge.remove(ingredientId);
        }
    }

    public List<Cocktail> getPossibleCocktails() {
        if (fridge.isEmpty()) {
            return List.of();
        }
        return cocktailService.getPossibleCocktails(new HashSet<>(fridge));
    }

}
