package com.example.cocktails.controller;

import com.example.cocktails.entity.*;
import com.example.cocktails.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CocktailRestController.class)
class CocktailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CocktailService cocktailService;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private FavoriteService favoriteService;

    @Test
    void testGetCocktails() throws Exception {
        List<Cocktail> cocktails = List.of(new Cocktail(1L, "Mojito"));
        when(cocktailService.getAllCocktails()).thenReturn(cocktails);

        mockMvc.perform(get("/api/cocktails"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Mojito"));
    }

    @Test
    void testSearch() throws Exception {
        String query = "Mojito";
        List<Cocktail> foundCocktails = List.of(new Cocktail(1L, "Mojito"));
        when(cocktailService.search(query)).thenReturn(foundCocktails);

        mockMvc.perform(get("/api/cocktails/search").param("query", query))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Mojito"));
    }

}
