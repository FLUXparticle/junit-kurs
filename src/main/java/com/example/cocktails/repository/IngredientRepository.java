package com.example.cocktails.repository;

import com.example.cocktails.entity.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface IngredientRepository extends Repository<Ingredient, Long> {

    Optional<Ingredient> findById(Long id);

    Ingredient save(Ingredient ingredient);

    Collection<Ingredient> findAll();

    Collection<Ingredient> findByNameContains(String query);

}
