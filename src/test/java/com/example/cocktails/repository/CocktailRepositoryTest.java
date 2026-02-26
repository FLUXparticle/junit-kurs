package com.example.cocktails.repository;

import com.example.cocktails.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CocktailRepositoryTest {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void testFindByNameContains() {
        // Testdaten anlegen
        Ingredient ingredient = new Ingredient(1L, "Minze");
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        Instruction instruction = new Instruction(null, savedIngredient);
        Cocktail cocktail = new Cocktail("Mojito", List.of(instruction));
        cocktailRepository.save(cocktail);

        Collection<Cocktail> found = cocktailRepository.findByNameContainsIgnoreCase("mojito");
        assertThat(found).isNotEmpty();
    }

    @Test
    void testFindByIngredientId() {
        // Testdaten anlegen
        Ingredient ingredient = new Ingredient(1L, "Minze");
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        Instruction instruction = new Instruction(null, savedIngredient);
        Cocktail cocktail = new Cocktail("Mojito", List.of(instruction));
        cocktailRepository.save(cocktail);

        Collection<Cocktail> found = cocktailRepository.findDistinctByInstructionsIngredientIdIn(Set.of(1L));
        assertThat(found).isNotEmpty();
    }

}
