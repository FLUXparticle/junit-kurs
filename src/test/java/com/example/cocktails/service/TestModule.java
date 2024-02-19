package com.example.cocktails.service;

import com.example.cocktails.repository.*;
import com.google.inject.*;
import com.google.inject.name.*;

public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Key.get(CocktailRepository.class, Names.named("data"))).to(TestCocktailRepository.class);
        bind(Key.get(IngredientRepository.class, Names.named("data"))).to(TestIngredientRepository.class);
    }

}
