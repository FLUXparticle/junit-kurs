package com.example.cocktails;

import com.example.cocktails.repository.*;
import com.google.inject.*;
import com.google.inject.name.*;
import org.jdbi.v3.core.*;
import org.jdbi.v3.guice.*;
import org.jdbi.v3.sqlobject.*;

class JdbiModule extends AbstractJdbiDefinitionModule {

    public JdbiModule() {
        super(Names.named("data"));
    }

    @Provides
    @Singleton
    IngredientRepository createIngredientRepository(Jdbi jdbi) {
        return jdbi.onDemand(IngredientRepository.class);
    }

    @Provides
    @Singleton
    CocktailRepository createCocktailRepository(Jdbi jdbi) {
        return jdbi.onDemand(CocktailRepository.class);
    }

    @Override
    public void configureJdbi() {
        bindPlugin().toInstance(new SqlObjectPlugin());

        exposeBinding(IngredientRepository.class);
        exposeBinding(CocktailRepository.class);

//        bindRowMapper().to(BrokenRowMapper.class);
//        bindColumnMapper().to(CustomStringMapper.class);
    }

}
