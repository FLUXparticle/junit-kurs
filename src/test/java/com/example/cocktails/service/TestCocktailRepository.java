package com.example.cocktails.service;

import com.example.cocktails.repository.*;
import com.google.inject.*;
import org.jdbi.v3.core.mapper.*;

import java.util.*;

import static java.util.stream.Collectors.*;

@Singleton
public class TestCocktailRepository implements CocktailRepository {

    private final TestIngredientRepository ingredientRepository;

    private final Map<Long, Cocktail> db = new HashMap<>();

    @Inject
    public TestCocktailRepository(TestIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
        addCocktail(27, "Pink Power", 7, 30);
    }

    private void addCocktail(long id, String name, long... ingredients) {
        Cocktail cocktail = new Cocktail(id, name);
        Collection<Instruction> instructions = cocktail.getInstructions();
        for (Long ingredientID : ingredients) {
            Instruction instruction = new Instruction(null, null);
            instruction.setIngredient(new Ingredient(ingredientID, null));
            instructions.add(instruction);
        }
        db.put(id, cocktail);
    }

    @Override
    public Optional<Cocktail> getCocktail(long id) {
        return Optional.empty();
    }

    @Override
    public List<Cocktail> getCocktails(String query) {
        return null;
    }

    @Override
    public List<Cocktail> findCocktailsByIngredients(Set<Long> ingredientIDs) {
        return db.values().stream()
                .filter(cocktail -> cocktail.getInstructions().stream()
                        .map(Instruction::getIngredient)
                        .map(Ingredient::getId)
                        .anyMatch(ingredientIDs::contains)
                )
                .collect(toList());
    }

    @Override
    public List<JoinRow> getCocktailDetails(long id) {
        return null;
    }

    @Override
    public Cocktail fetchDetails(Cocktail cocktail) {
        for (Instruction instruction : cocktail.getInstructions()) {
            Optional<Ingredient> optIngredient = ingredientRepository.findById(instruction.getIngredient().getId());
            optIngredient.ifPresent(instruction::setIngredient);
        }
        return cocktail;
    }

}
