package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;

import java.util.*;

final class FakeIngredientRepository implements IngredientRepository {

    private final List<Ingredient> ingredients;

    FakeIngredientRepository(List<Ingredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return ingredients.stream().filter(i -> Objects.equals(i.getId(), id)).findFirst();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        ingredients.add(ingredient);
        return ingredient;
    }

    @Override
    public Collection<Ingredient> findAll() {
        return List.copyOf(ingredients);
    }

    @Override
    public Collection<Ingredient> findByNameContains(String query) {
        return ingredients.stream()
                .filter(i -> i.getName() != null && i.getName().contains(query))
                .toList();
    }
}
