package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;
    private final IngredientRepository ingredientRepository;

    public CocktailService(CocktailRepository cocktailRepository, IngredientRepository ingredientRepository) {
        this.cocktailRepository = cocktailRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Collection<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    public Collection<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Collection<Instruction> getInstructions(long cocktailId) {
        return cocktailRepository.findById(cocktailId)
                .map(Cocktail::getInstructions)
                .orElse(Collections.emptyList());
    }

    public Collection<Cocktail> getAllCocktailsWithIngredient(Long ingredientId) {
        Collection<Cocktail> cocktails = getAllCocktailsWithIngredients(Collections.singleton(ingredientId));
        return new TreeSet<>(cocktails);
    }

    public Cocktail getCocktailWithID(Long id) {
        return cocktailRepository.findById(id).orElse(null);
    }

    public Ingredient getIngredientWithID(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Collection<Cocktail> search(String query) {
        if (query == null || query.isBlank()) {
            return getAllCocktails();
        }

        Collection<Cocktail> cocktailsWithName = cocktailRepository.findByNameContains(query);
        Collection<Ingredient> ingredientsWithName = ingredientRepository.findByNameContains(query);

        Set<Long> ingredientIds = new HashSet<>();
        for (Ingredient ingredient : ingredientsWithName) {
            ingredientIds.add(ingredient.getId());
        }

        Collection<Cocktail> cocktailsWithIngredients = getAllCocktailsWithIngredients(ingredientIds);

        SortedSet<Cocktail> result = new TreeSet<>();
        result.addAll(cocktailsWithName);
        result.addAll(cocktailsWithIngredients);

        return result;
    }

    public List<Cocktail> getPossibleCocktails(Set<Long> ingredientIds) {
        if (ingredientIds == null || ingredientIds.isEmpty()) {
            return Collections.emptyList();
        }

        Collection<Cocktail> maybePossibleCocktails = getAllCocktailsWithIngredients(ingredientIds);

        return maybePossibleCocktails.stream()
                .filter(cocktail -> cocktail.getInstructions().stream()
                        .allMatch(instruction -> {
                            Long ingredientId = instruction.getIngredient().getId();
                            return ingredientIds.contains(ingredientId);
                        })
                )
                .toList();
    }

    private Collection<Cocktail> getAllCocktailsWithIngredients(Set<Long> ingredientIds) {
        if (ingredientIds == null || ingredientIds.isEmpty()) {
            return Collections.emptyList();
        }
        return cocktailRepository.findDistinctByInstructionsIngredientIdIn(ingredientIds);
    }

}
