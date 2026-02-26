package com.example.cocktails.service;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private CocktailService cocktailService;

    // TODO Eine weitere Test-Klasse schreiben, die Fake-Klassen für die Repositories verwendet
    @Test
    void testSearch() {
        String query = "Minze";

        Ingredient ingredient = new Ingredient(1L, "Minze");
        Cocktail cocktail = new Cocktail("Mojito", List.of(new Instruction(null, ingredient)));

        when(cocktailRepository.findByNameContains(query)).thenReturn(List.of());
        when(ingredientRepository.findByNameContains(query)).thenReturn(List.of(ingredient));
        when(cocktailRepository.findDistinctByInstructionsIngredientIdIn(Set.of(1L))).thenReturn(List.of(cocktail));

        Collection<Cocktail> result = cocktailService.search(query);
        assertThat(result).isNotEmpty();
    }

}
