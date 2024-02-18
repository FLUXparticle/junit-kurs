package com.example.cocktails.controller;

import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import com.google.inject.*;
import com.sun.net.httpserver.*;
import org.json.simple.*;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.*;

public class UserController extends AbstractController {

    private final CocktailService cocktailService;

    private final FridgeService fridgeService;

    @Inject
    public UserController(CocktailService cocktailService, FridgeService fridgeService) {
        this.cocktailService = cocktailService;
        this.fridgeService = fridgeService;
    }

    @Override
    protected Object handleGetRequest(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath().substring(9);
        if (path.equals("/fridge")) {
            return fridge();
        } else if (path.equals("/possible")) {
            return possible();
        } else if (path.equals("/shopping")) {
            return shopping();
        } else {
            return null;
        }
    }

    @Override
    protected void handlePatchRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath().substring(9);
        if (path.startsWith("/fridge/")) {
            try {
                long id = Long.parseLong(path.substring(8));
                JsonObject body = (JsonObject) Jsoner.deserialize(new InputStreamReader(exchange.getRequestBody()));
                fridge(id, body);
            } catch (DeserializationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //    @GetMapping("/fridge")
    public List<Map<String, Object>> fridge() {
        Collection<Ingredient> allIngredients = cocktailService.getAllIngredients();
        Set<Ingredient> fridgeIngredients = fridgeService.getFridgeIngredients();

        return allIngredients.stream()
                .map(ingredient -> Map.<String, Object>of(
                        "id", ingredient.getId(),
                        "name", ingredient.getName(),
                        "inFridge", fridgeIngredients.contains(ingredient)
                ))
                .collect(toList());
    }

//    @PostMapping("/fridge")
    public void fridge(long id, JsonObject body) {
        Ingredient ingredient = cocktailService.getIngredientWithID(id);

        Boolean inFridge = body.getBoolean("inFridge");

        if (inFridge) {
            fridgeService.addIngredient(ingredient);
        } else {
            fridgeService.removeIngredient(ingredient);
        }
    }

//    @GetMapping("/possible")
    public List<Map<String, Object>> possible() {
        return fridgeService.getPossibleCocktails().stream()
                .map(cocktail -> Map.<String, Object>of(
                        "id", cocktail.getId(),
                        "name", cocktail.getName()
                ))
                .collect(toList());
    }

//    @GetMapping("/shopping")
    public List<Map<String, Object>> shopping() {
        return fridgeService.getShoppingList().stream()
                .map(ingredient -> Map.<String, Object>of(
                        "id", ingredient.getId(),
                        "name", ingredient.getName()
                ))
                .collect(toList());
    }

}
