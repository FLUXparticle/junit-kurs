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

        // TODO Testen, ob der Cocktail auch gefunden wird, wenn der Name klein geschrieben wird
        // TODO Durch hinzufügen von `findByNameContainsIgnoreCase` im Repository beheben
        Collection<Cocktail> found = cocktailRepository.findByNameContains("Mojito");
        assertThat(found).isNotEmpty();
    }

    @Test
    void testFindByIngredientId() {
        // TODO Testen, ob der Cocktail auch gefunden wird, wenn man nach der ID der Zutat sucht
    }

}
