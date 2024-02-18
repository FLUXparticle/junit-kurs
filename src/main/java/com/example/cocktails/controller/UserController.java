package com.example.cocktails.controller;

import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import com.google.inject.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.util.*;

public class UserController implements HttpHandler {

    private final CocktailService cocktailService;

    private final FridgeService fridgeService;

    @Inject
    public UserController(CocktailService cocktailService, FridgeService fridgeService) {
        this.cocktailService = cocktailService;
        this.fridgeService = fridgeService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
    }

//    @GetMapping("/fridge")
    public String fridge() {
        Collection<Ingredient> allIngredients = cocktailService.getAllIngredients();
        Set<Ingredient> fridgeIngredients = fridgeService.getFridgeIngredients();

        List<FridgeModel> fridgeModels = new ArrayList<>();
        for (Ingredient ingredient : allIngredients) {
            fridgeModels.add(new FridgeModel(ingredient, fridgeIngredients.contains(ingredient)));
        }

//        model.addAttribute("fridgeModels", fridgeModels);
        return "fridge";
    }

//    @PostMapping("/fridge")
    public String fridge(Long id, String action) {
        Ingredient ingredient = cocktailService.getIngredientWithID(id);

        switch (action) {
            case "add":
                fridgeService.addIngredient(ingredient);
                break;
            case "remove":
                fridgeService.removeIngredient(ingredient);
                break;
        }

        return "redirect:fridge";
    }

//    @GetMapping("/possible")
    public String possible() {
        Collection<Cocktail> cocktails = fridgeService.getPossibleCocktails();
//        model.addAttribute("cocktails", cocktails);
        return "possible";
    }

//    @GetMapping("/shopping")
    public String shopping() {
        Set<Ingredient> shoppingList = fridgeService.getShoppingList();
//        model.addAttribute("shoppingList", shoppingList);
        return "shopping";
    }

}
