package com.example.cocktails.service;

import com.example.cocktails.repository.*;
import com.google.inject.*;
import com.google.inject.name.*;

import java.util.*;

import static java.util.Collections.*;
import static java.util.stream.Collectors.*;

@Singleton
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
        return getAllCocktailsWithIngredients(Collections.singleton(ingredientsId), false);
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

        Set<Long> ingredientIDs = ingredientsWithName.stream()
                .map(Ingredient::getId)
                .collect(toSet());

        List<Cocktail> cocktailsWithIngredients = getAllCocktailsWithIngredients(ingredientIDs, false);

        Set<Cocktail> result = new TreeSet<>(cocktailsWithIngredients);
        result.addAll(cocktailsWithName);

        return result;
    }

    public List<Cocktail> getAllCocktailsWithIngredients(Set<Long> ingredientIDs, boolean withInstructions) {
        if (ingredientIDs.isEmpty()) {
            return emptyList();
        } else {
            List<Cocktail> cocktails = cocktailRepository.findCocktailsByIngredients(ingredientIDs);
            if (withInstructions) {
                cocktails.forEach(cocktailRepository::fetchDetails);
            }
            return cocktails;
        }
    }

}
