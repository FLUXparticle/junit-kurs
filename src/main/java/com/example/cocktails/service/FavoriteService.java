package com.example.cocktails.service;

import com.example.cocktails.dto.*;
import com.example.cocktails.entity.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.annotation.*;

import java.util.*;

@Service
@SessionScope
public class FavoriteService {

    private final CocktailService cocktailService;
    private final Set<Long> favorites = new HashSet<>();

    public FavoriteService(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    public boolean toggleFavorite(Long cocktailId) {
        if (cocktailId == null || cocktailService.getCocktailWithID(cocktailId) == null) {
            return false;
        }

        if (favorites.contains(cocktailId)) {
            favorites.remove(cocktailId);
            return false;
        }

        favorites.add(cocktailId);
        return true;
    }

    public List<FavoriteCocktailDTO> getFavoritesOverview() {
        return cocktailService.getAllCocktails().stream()
                .sorted(Comparator.comparing(Cocktail::getName))
                .map(cocktail -> {
                    boolean favorite = favorites.contains(cocktail.getId());
                    long favoriteCount = favorite ? 1 : 0;
                    return new FavoriteCocktailDTO(cocktail.getId(), cocktail.getName(), favorite, favoriteCount);
                })
                .toList();
    }

}
