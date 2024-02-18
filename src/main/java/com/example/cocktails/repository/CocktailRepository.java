package com.example.cocktails.repository;

import org.jdbi.v3.core.mapper.*;
import org.jdbi.v3.sqlobject.config.*;
import org.jdbi.v3.sqlobject.customizer.*;
import org.jdbi.v3.sqlobject.statement.*;

import java.util.*;

public interface CocktailRepository {

    @RegisterConstructorMapper(Cocktail.class)
    @SqlQuery("SELECT id, name FROM cocktail WHERE cocktail.id = :id")
    Optional<Cocktail> getCocktail(@Bind("id") long id);

    @RegisterConstructorMapper(Cocktail.class)
    @SqlQuery("SELECT id, name FROM cocktail WHERE cocktail.name LIKE :query ORDER BY name")
    List<Cocktail> getCocktails(@Bind("query") String query);

    @RegisterConstructorMapper(Cocktail.class)
    @SqlQuery("SELECT DISTINCT cocktail.id, name FROM cocktail JOIN instruction ON cocktail.id = instruction.instructions_id WHERE instruction.ingredient_id IN (<ingredientIDs>) ORDER BY name")
    List<Cocktail> findCocktailsByIngredients(@BindList("ingredientIDs") Set<Long> ingredientIDs);

    @RegisterJoinRowMapper({Instruction.class, Ingredient.class})
    @RegisterConstructorMapper(value = Instruction.class, prefix = "inst")
    @RegisterConstructorMapper(value = Ingredient.class, prefix = "ing")
    @SqlQuery("SELECT instruction.id inst_id, instruction.amountcl inst_amountcl, ingredient.id ing_id, ingredient.name ing_name FROM instruction JOIN ingredient ON instruction.ingredient_id = ingredient.id WHERE instruction.instructions_id = :id")
    List<JoinRow> getCocktailDetails(@Bind("id") long id);

    default Optional<Cocktail> findByID(long id) {
        return getCocktail(id).map(this::fetchDetails);
    }

    default Cocktail fetchDetails(Cocktail cocktail) {
        List<JoinRow> joinRows = getCocktailDetails(cocktail.getId());

        for (JoinRow joinRow : joinRows) {
            Instruction instruction = joinRow.get(Instruction.class);
            cocktail.getInstructions().add(instruction);

            Ingredient ingredient = joinRow.get(Ingredient.class);
            instruction.setIngredient(ingredient);
        }

        return cocktail;
    }

    default Collection<Cocktail> findAll() {
        return getCocktails("%");
    }

    default Collection<Cocktail> findByNameContains(String query) {
        return getCocktails("%" + query + "%");
    }

}
