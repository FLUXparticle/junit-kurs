package com.example.cocktails.controller;

import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import com.google.inject.*;
import com.sun.net.httpserver.*;
import org.json.simple.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static java.util.stream.Collectors.*;

public class CocktailController implements HttpHandler {

    public static final String GET = "GET";
    private final CocktailService cocktailService;

    @Inject
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath().substring(4);
        try {
            if (method.equals(GET) && path.equals("/cocktails")) {
                sendResponse(exchange, HttpURLConnection.HTTP_OK, cocktails());
            } else if (method.equals(GET) && path.startsWith("/cocktails/")) {
                long id = Long.parseLong(path.substring(11));
                sendResponse(exchange, HttpURLConnection.HTTP_OK, cocktail(id));
            } else if (method.equals(GET) && path.equals("/ingredients")) {
                sendResponse(exchange, HttpURLConnection.HTTP_OK, ingredients());
            } else if (method.equals(GET) && path.startsWith("/ingredients/")) {
                long id = Long.parseLong(path.substring(13));
                sendResponse(exchange, HttpURLConnection.HTTP_OK, ingredient(id));
            } else if (method.equals(GET) && path.equals("/search")) {
                String query = exchange.getRequestURI().getQuery()
                        .substring(6)
                        .replace('+', ' ');
                sendResponse(exchange, HttpURLConnection.HTTP_OK, search(query));
            } else {
                sendResponse(exchange, HttpURLConnection.HTTP_NOT_FOUND, Map.of("error", "Not Found"));
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, Map.of("error", "Internal Server Error"));
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, Object responseBody) throws IOException {
        byte[] responseBytes = Jsoner.serialize(responseBody).getBytes();

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
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
