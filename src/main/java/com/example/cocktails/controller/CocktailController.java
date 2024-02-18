package com.example.cocktails.controller;

import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import com.google.inject.*;
import com.sun.net.httpserver.*;

import java.util.*;

import static java.util.stream.Collectors.*;

public class CocktailController extends AbstractController {

    private final CocktailService cocktailService;

    @Inject
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @Override
    protected Object handleGetRequest(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath().substring(4);
        if (path.equals("/cocktails")) {
            return cocktails();
        } else if (path.startsWith("/cocktails/")) {
            long id = Long.parseLong(path.substring(11));
            return cocktail(id);
        } else if (path.equals("/ingredients")) {
            return ingredients();
        } else if (path.startsWith("/ingredients/")) {
            long id = Long.parseLong(path.substring(13));
            return ingredient(id);
        } else if (path.equals("/search")) {
            String query = exchange.getRequestURI().getQuery()
                    .substring(6)
                    .replace('+', ' ');
            return search(query);
        } else {
            return null;
        }
    }

//    @PostMapping("/search")
    public List<Map<String, Object>> search(String query) {
        return cocktailService.search(query).stream()
                .map(cocktail -> Map.<String, Object>of(
                        "id", cocktail.getId(),
                        "name", cocktail.getName()
                ))
                .collect(toList());
    }

//    @GetMapping("/cocktails")
    public List<Map<String, Object>> cocktails() {
        return cocktailService.getAllCocktails().stream()
                .map(cocktail -> Map.<String, Object>of(
                        "id", cocktail.getId(),
                        "name", cocktail.getName()
                ))
                .collect(toList());
    }

//    @GetMapping("/cocktails/{id}")
    public Map<String, Object> cocktail(Long id) {
        Cocktail cocktail = cocktailService.getCocktailWithID(id);
        return Map.of(
                "name", cocktail.getName(),
                "instructions", cocktail.getInstructions().stream()
                        .map(instruction -> Map.of(
                                "amount", Optional.ofNullable(instruction.getAmountCL()).map(x -> x + " cl").orElse(""),
                                "ingredient_id", instruction.getIngredient().getId(),
                                "ingredient_name", instruction.getIngredient().getName()
                        ))
                        .collect(toList())
        );
    }

//    @GetMapping("/ingredients")
    public List<Map<String, Object>> ingredients() {
        return cocktailService.getAllIngredients().stream()
                .map(ingredient -> Map.<String, Object>of(
                        "id", ingredient.getId(),
                        "name", ingredient.getName()
                ))
                .collect(toList());
    }

//    @GetMapping("/ingredients/{id}")
    public Map<String, Object> ingredient(Long id) {
        Ingredient ingredient = cocktailService.getIngredientWithID(id);
        Collection<Cocktail> cocktails = cocktailService.getAllCocktailsWithIngredient(id);

        return Map.of(
                "name", ingredient.getName(),
                "cocktails", cocktails.stream()
                        .map(cocktail -> Map.of(
                                "id", cocktail.getId(),
                                "name", cocktail.getName()
                        ))
                        .collect(toList())
        );
    }

}
