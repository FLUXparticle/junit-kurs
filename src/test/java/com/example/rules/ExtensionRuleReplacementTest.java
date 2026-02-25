package com.example.rules;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

// Zeigt, wie mehrere JUnit-5-Extensions zusammen klassische Rule-Szenarien ersetzen:
// ExternalResource, TestWatcher, Verifier und RuleChain-Reihenfolge.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ExtensionRuleReplacementTest.CountingWatcher.class)
class ExtensionRuleReplacementTest {

    private static final List<String> events = new CopyOnWriteArrayList<>();

    @Order(1)
    @RegisterExtension
    // Simuliert eine externe Ressource (z. B. DB).
    static final OrderedResourceExtension database = new OrderedResourceExtension("db", events);

    @Order(2)
    @RegisterExtension
    // Zweite Ressource; die @Order-Werte machen die Reihenfolge transparent.
    static final OrderedResourceExtension api = new OrderedResourceExtension("api", events);

    @BeforeAll
    static void resetState() {
        events.clear();
        CountingWatcher.reset();
    }

    @Test
    @Order(1)
    void combinedExtensions_replaceRuleChainAndExternalResource() {
        // Beide Ressourcen sind im Test aktiv und wurden in definierter Reihenfolge gestartet.
        assertAll(
                () -> assertTrue(database.isActive()),
                () -> assertTrue(api.isActive()),
                () -> assertEquals(List.of("before:db", "before:api"), new ArrayList<>(events))
        );
    }

    @Test
    @Order(2)
    void testWatcher_replacesRuleBasedWatcher() {
        // Erfolgreicher Test erhöht den Counter im TestWatcher.
        assertTrue(database.isActive());
    }

    @AfterAll
    static void verifyCallbacks_afterAllLikeVerifier() {
        // Zentrale Nachprüfung nach allen Tests (Verifier-ähnliches Muster).
        assertAll(
                () -> assertEquals(2, CountingWatcher.successfulCount()),
                () -> assertEquals(2, database.beforeCount()),
                () -> assertEquals(2, database.afterCount()),
                () -> assertEquals(2, api.beforeCount()),
                () -> assertEquals(2, api.afterCount()),
                () -> assertEquals(
                        List.of(
                                "before:db", "before:api", "after:api", "after:db",
                                "before:db", "before:api", "after:api", "after:db"
                        ),
                        new ArrayList<>(events)
                )
        );
    }

    static final class OrderedResourceExtension implements BeforeEachCallback, AfterEachCallback {

        private final String name;
        private final List<String> events;
        private final AtomicInteger before = new AtomicInteger();
        private final AtomicInteger after = new AtomicInteger();
        private final ThreadLocal<Boolean> active = ThreadLocal.withInitial(() -> false);

        private OrderedResourceExtension(String name, List<String> events) {
            this.name = name;
            this.events = events;
        }

        @Override
        public void beforeEach(ExtensionContext context) {
            // Setup pro Testlauf.
            active.set(true);
            before.incrementAndGet();
            events.add("before:" + name);
        }

        @Override
        public void afterEach(ExtensionContext context) {
            // Cleanup pro Testlauf.
            events.add("after:" + name);
            after.incrementAndGet();
            active.remove();
        }

        boolean isActive() {
            return active.get();
        }

        int beforeCount() {
            return before.get();
        }

        int afterCount() {
            return after.get();
        }
    }

    static final class CountingWatcher implements TestWatcher {

        private static final AtomicInteger successful = new AtomicInteger();

        @Override
        public void testSuccessful(ExtensionContext context) {
            // Reaktion auf Testereignis (ersetzt typische TestWatcher-Rule-Logik).
            successful.incrementAndGet();
        }

        static int successfulCount() {
            return successful.get();
        }

        static void reset() {
            successful.set(0);
        }
    }
}
