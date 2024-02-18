package com.example.cocktails.service;

import com.example.cocktails.repository.*;
import com.google.inject.*;
import com.google.inject.name.*;

import java.util.*;

public class CocktailService {

    private final CocktailRepository cocktailRepository;

    private final IngredientRepository ingredientRepository;

    @Inject
    public CocktailService(@Named("data") CocktailRepository cocktailRepository, @Named("data") IngredientRepository ingredientRepository) {
        this.cocktailRepository = cocktailRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Collection<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    public Collection<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Collection<Cocktail> getAllCocktailsWithIngredient(Long ingredientsId) {
        return getAllCocktailsWithIngredients(Collections.singleton(ingredientsId));
    }

    public Cocktail getCocktailWithID(Long id) {
        return cocktailRepository.findByID(id).orElse(null);
    }

    public Ingredient getIngredientWithID(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Collection<Cocktail> search(String query) {
        Collection<Cocktail> cocktailsWithName = cocktailRepository.findByNameContains(query);
        Collection<Ingredient> ingredientsWithName = ingredientRepository.findByNameContains(query);

        Set<Long> ingredientIDs = new HashSet<>();
        for (Ingredient ingredient : ingredientsWithName) {
            ingredientIDs.add(ingredient.getId());
        }

        SortedSet<Cocktail> result = new TreeSet<>(); // getAllCocktailsWithIngredients(ingredientIDs);
//        result.addAll(cocktailsWithName);

        return result;
    }

    private List<Cocktail> getAllCocktailsWithIngredients(Set<Long> ingredientIDs) {
        return cocktailRepository.findCocktailsByIngredients(ingredientIDs);
    }

}
