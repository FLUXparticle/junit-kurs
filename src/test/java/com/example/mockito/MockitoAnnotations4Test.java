package com.example.mockito;

import com.example.cocktails.entity.*;
import com.example.cocktails.repository.*;
import com.example.cocktails.service.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// Neuer Bereich: Mockito-Annotationen mit JUnit 4.
// Initialisierung erfolgt hier über den MockitoJUnitRunner.
@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotations4Test {

    // @Mock erzeugt einen reinen Test-Dummy ohne echte Logik.
    // Vorteil: Abhängigkeiten sind isoliert und präzise kontrollierbar.
    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    // @InjectMocks baut das Testobjekt und injiziert automatisch die passenden Mocks.
    // Vorteil: weniger Setup-Code als manuelle Konstruktion.
    @InjectMocks
    private CocktailService cocktailService;

    // @Captor erzeugt typsichere ArgumentCaptor-Instanzen ohne new ArgumentCaptor(...).
    @Captor
    private ArgumentCaptor<Collection<Long>> ingredientIdsCaptor;

    // @Spy nutzt ein echtes Objekt, erlaubt aber Verify/Stubbing wie bei Mocks.
    // Vorteil: nützlich für "teilweise echt" ohne eigene Fake-Klasse.
    @Spy
    private List<String> auditTrail = new ArrayList<>();

    @Test
    public void annotationsMockInjectMocksAndCaptor_areUsedTogether() {
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
        assertTrue(ids.contains(10L));
        assertTrue(ids.contains(20L));
        assertEquals(2, ids.size());
    }

    @Test
    public void spy_tracksRealMethodCalls() {
        auditTrail.add("search started");

        verify(auditTrail).add("search started");
        assertEquals(1, auditTrail.size());
    }
}

