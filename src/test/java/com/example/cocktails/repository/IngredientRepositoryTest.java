package com.example.cocktails.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.*;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class IngredientRepositoryTest {

    // TODO Ebenfalls einen Test CocktailRepositoryTest.testFindByNameContains aber für IngredientRepository

}
