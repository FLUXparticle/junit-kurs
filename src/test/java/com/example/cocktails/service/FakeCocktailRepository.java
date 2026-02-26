package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;

import java.util.*;

final class FakeCocktailRepository implements CocktailRepository {

    private final List<Cocktail> cocktails;

    FakeCocktailRepository(List<Cocktail> cocktails) {
        this.cocktails = new ArrayList<>(cocktails);
    }

    @Override
    public Optional<Cocktail> findById(Long id) {
        return cocktails.stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
    }

    @Override
    public Cocktail save(Cocktail cocktail) {
        cocktails.add(cocktail);
        return cocktail;
    }

    @Override
    public Collection<Cocktail> findAll() {
        return List.copyOf(cocktails);
    }

    @Override
    public Collection<Cocktail> findByNameContains(String query) {
        return cocktails.stream()
                .filter(c -> c.getName() != null && c.getName().contains(query))
                .toList();
    }

    @Override
    public Collection<Cocktail> findByNameContainsIgnoreCase(String query) {
        String lower = query.toLowerCase(Locale.ROOT);
        return cocktails.stream()
                .filter(c -> c.getName() != null && c.getName().toLowerCase(Locale.ROOT).contains(lower))
                .toList();
    }

    @Override
    public Collection<Cocktail> findDistinctByInstructionsIngredientIdIn(Collection<Long> ingredientIDs) {
        Set<Long> ingredientIds = new HashSet<>(ingredientIDs);
        return cocktails.stream()
                .filter(cocktail -> cocktail.getInstructions().stream()
                        .map(instruction -> instruction.getIngredient())
                        .filter(Objects::nonNull)
                        .map(Ingredient::getId)
                        .anyMatch(ingredientIds::contains))
                .toList();
    }
}
