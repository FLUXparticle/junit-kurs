package com.example.cocktails;

import com.example.cocktails.controller.*;
import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import com.google.inject.*;
import com.google.inject.name.*;
import com.sun.net.httpserver.*;
import org.apache.commons.dbcp2.*;

import javax.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CocktailApp {

    public static void main(String[] args) throws IOException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:sqlite::resource:cocktails.db");

        Injector injector = Guice.createInjector(
                new JdbiModule(),
                binder -> binder
                        .bind(DataSource.class)
                        .annotatedWith(Names.named("data"))
                        .toInstance(dataSource)
        );

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        LoggingFilter loggingFilter = new LoggingFilter();

        server.createContext("/", new StaticHttpHandler("static"))
                .getFilters().add(loggingFilter);

        server.createContext("/api/", injector.getInstance(CocktailController.class))
                .getFilters().add(loggingFilter);

        server.createContext("/api/user/", injector.getInstance(UserController.class))
                .getFilters().add(loggingFilter);

        server.start();
        System.out.println("Server started...");


//        IngredientRepository ingredientRepository = injector.getInstance(Key.get(IngredientRepository.class, Names.named("data")));
//        Collection<Ingredient> ingredients = ingredientRepository.findAll();
//        ingredients.forEach(System.out::println);

//        CocktailRepository cocktailRepository = injector.getInstance(Key.get(CocktailRepository.class, Names.named("data")));
//        cocktailRepository.findByNameContains("Milk")
//                .forEach(System.out::println);

//        CocktailService cocktailService = injector.getInstance(CocktailService.class);
//        Collection<Cocktail> cocktails = cocktailService.getAllCocktails();
//        cocktails.forEach(System.out::println);

//        FridgeService fridgeService = injector.getInstance(FridgeService.class);
//        Collection<Cocktail> possibleCocktails = fridgeService.getPossibleCocktails();
//        System.out.println("possibleCocktails = " + possibleCocktails);
    }

}
