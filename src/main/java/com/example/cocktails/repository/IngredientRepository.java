package com.example.cocktails.repository;

import org.jdbi.v3.sqlobject.config.*;
import org.jdbi.v3.sqlobject.customizer.*;
import org.jdbi.v3.sqlobject.statement.*;

import java.util.*;

@RegisterConstructorMapper(Ingredient.class)
public interface IngredientRepository {

    @SqlQuery("SELECT id, name FROM ingredient WHERE id = :id")
    Optional<Ingredient> findById(@Bind("id") long id);

    @SqlQuery("SELECT id, name FROM ingredient ORDER BY name")
    Collection<Ingredient> findAll();

    Collection<Ingredient> findByNameContains(String query);

}
