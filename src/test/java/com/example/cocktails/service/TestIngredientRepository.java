package com.example.cocktails.service;

import com.example.cocktails.repository.*;
import com.google.inject.*;

import java.util.*;

@Singleton
public class TestIngredientRepository implements IngredientRepository {

    private final Map<Long, Ingredient> db = new HashMap<>();

    public TestIngredientRepository() {
        addIngredient(7, "Fruchtsirup Preiselbeere");
        addIngredient(30, "Buttermilch");
    }

    private void addIngredient(long id, String name) {
        db.put(id, new Ingredient(id, name));
    }

    @Override
    public Optional<Ingredient> findById(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Collection<Ingredient> findAll() {
        return null;
    }

    @Override
    public Collection<Ingredient> getIngredients(String query) {
        return null;
    }

}
