package com.example.cocktails.controller;

import com.example.cocktails.dto.*;
import com.example.cocktails.entity.*;
import com.example.cocktails.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CocktailRestController {

    private final CocktailService cocktailService;
    private final CartService cartService;
    private final FavoriteService favoriteService;

    public CocktailRestController(CocktailService cocktailService, CartService cartService, FavoriteService favoriteService) {
        this.cocktailService = cocktailService;
        this.cartService = cartService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/cocktails")
    public Collection<Cocktail> getAllCocktails() {
        return cocktailService.getAllCocktails().stream()
                .sorted(Comparator.comparing(Cocktail::getName))
                .toList();
    }

    @GetMapping("/cocktails/{id}")
    public Map<String, Object> cocktail(@PathVariable Long id) {
        Cocktail cocktail = cocktailService.getCocktailWithID(id);
        if (cocktail == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail not found");
        }

        return Map.of(
                "name", cocktail.getName(),
                "instructions", cocktail.getInstructions()
        );
    }

    @GetMapping("/cocktails/search")
    public Collection<Cocktail> searchCocktails(@RequestParam String query) {
        return cocktailService.search(query);
    }

    @PostMapping("/possible")
    public Collection<Cocktail> getPossibleRecipes(@RequestBody Map<String, List<Long>> payload) {
        List<Long> ingredientIds = payload.getOrDefault("ingredientIDs", List.of());
        return cocktailService.getPossibleCocktails(new HashSet<>(ingredientIds));
    }

    @GetMapping("/ingredients")
    public Collection<Ingredient> getAllIngredients() {
        return cocktailService.getAllIngredients().stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .toList();
    }

    @GetMapping("/ingredients/{id}")
    public Ingredient getIngredient(@PathVariable Long id) {
        Ingredient ingredient = cocktailService.getIngredientWithID(id);
        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found");
        }
        return ingredient;
    }

    @GetMapping("/ingredients/{id}/cocktails")
    public Map<String, Object> ingredient(@PathVariable Long id) {
        Ingredient ingredient = cocktailService.getIngredientWithID(id);
        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found");
        }

        Collection<Cocktail> cocktails = cocktailService.getAllCocktailsWithIngredient(id);
        return Map.of(
                "name", ingredient.getName(),
                "cocktails", cocktails
        );
    }

    @GetMapping("/cart")
    public Map<String, Object> getCart() {
        return Map.of(
                "ingredients", cartService.getIngredients(),
                "cocktails", cartService.getCocktailsForSelection()
        );
    }

    @PostMapping("/cart/actions/add-cocktail")
    public void addCocktailToCart(@RequestBody Map<String, Long> payload) {
        Long cocktailId = payload.get("cocktailId");
        if (cocktailId != null) {
            cartService.addCocktail(cocktailId);
        }
    }

    @DeleteMapping("/cart/items/{ingredientId}")
    public void removeIngredientFromCart(@PathVariable Long ingredientId) {
        cartService.removeIngredient(ingredientId);
    }

    @DeleteMapping("/cart")
    public void clearCart() {
        cartService.clear();
    }

    @GetMapping("/favorites")
    public Collection<FavoriteCocktailDTO> getFavoritesOverview() {
        return favoriteService.getFavoritesOverview();
    }

    @PostMapping("/favorites/toggle")
    public Map<String, Object> toggleFavorite(@RequestBody Map<String, Long> payload) {
        Long cocktailId = payload.get("cocktailId");
        boolean favorite = cocktailId != null && favoriteService.toggleFavorite(cocktailId);
        return Map.of("favorite", favorite);
    }

}
