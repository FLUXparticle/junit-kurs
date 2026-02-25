package com.example.cocktails.controller;

import com.example.cocktails.entity.*;
import com.example.cocktails.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/fridge")
public class FridgeController {

    private final FridgeService fridgeService;

    public FridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping("/ingredients")
    public List<FridgeIngredient> getFridgeIngredients() {
        return fridgeService.getFridgeIngredients();
    }

    @PatchMapping("/ingredients/{id}")
    public void updateIngredientStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        boolean inFridge = payload.getOrDefault("inFridge", false);
        fridgeService.updateIngredientStatus(id, inFridge);
    }

    @GetMapping("/possible")
    public List<Cocktail> getPossibleCocktails() {
        return fridgeService.getPossibleCocktails();
    }

}
