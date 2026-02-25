package com.example.cocktails.repository;

import com.example.cocktails.entity.*;
import org.springframework.data.repository.*;
import org.springframework.lang.*;

import java.util.*;

public interface CocktailRepository extends Repository<Cocktail, Long> {

    Optional<Cocktail> findById(Long id);

    @NonNull
    Collection<Cocktail> findAll();

    Collection<Cocktail> findByNameContains(String query);

    Collection<Cocktail> findDistinctByInstructionsIngredientIdIn(Collection<Long> ingredientIDs);

}
