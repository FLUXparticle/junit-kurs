package com.example.mockito;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Neuer Bereich: Mockito-Annotationen mit JUnit 5.
// Initialisierung erfolgt über die MockitoExtension statt Runner.
@ExtendWith(MockitoExtension.class)
class MockitoAnnotations5Test {

    // @Mock erzeugt kontrollierbare Testdoubles für Abhängigkeiten.
    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    // @InjectMocks erstellt das echte Objekt und macht es bereit für Mocking
    @InjectMocks
    private CocktailService cocktailService;

    // @Captor für typsichere Capture-Prüfungen.
    @Captor
    private ArgumentCaptor<Collection<Long>> ingredientIdsCaptor;

    // @Spy: echtes Objekt + Mockito-Verify.
    @Spy
    private List<String> auditTrail = new ArrayList<>();

    @Test
    void annotationsMockInjectMocksAndCaptor_areUsedTogether() {
        when(cocktailRepository.findByNameContains("rum")).thenReturn(Collections.emptyList());
        when(ingredientRepository.findByNameContains("rum")).thenReturn(List.of(
                new Ingredient(10L, "White Rum"),
                new Ingredient(20L, "Dark Rum")
        ));
        when(cocktailRepository.findDistinctByInstructionsIngredientIdIn(anyCollection()))
                .thenReturn(Collections.emptyList());

        cocktailService.search("rum");

        verify(cocktailRepository).findDistinctByInstructionsIngredientIdIn(ingredientIdsCaptor.capture());
        Collection<Long> ids = ingredientIdsCaptor.getValue();
        assertAll(
                () -> assertTrue(ids.contains(10L)),
                () -> assertTrue(ids.contains(20L)),
                () -> assertEquals(2, ids.size())
        );
    }

    @Test
    void spy_tracksRealMethodCalls() {
        auditTrail.add("search started");

        verify(auditTrail).add("search started");
        assertEquals(1, auditTrail.size());
    }
}

