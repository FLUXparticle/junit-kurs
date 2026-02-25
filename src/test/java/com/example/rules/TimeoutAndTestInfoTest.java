package com.example.rules;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class TimeoutAndTestInfoTest {

    @Test
    void assertTimeout_replacesTimeoutRule() {
        // Timeout wird deklarativ am getesteten Block gesetzt.
        // Vorteil: kein Rule-Objekt nötig, Test bleibt lokal verständlich.
        assertTimeout(Duration.ofMillis(200), () -> Thread.sleep(20));
    }

    @Test
    void testInfo_replacesTestNameRule(TestInfo testInfo) {
        // TestInfo wird als Parameter injiziert und liefert Metadaten zum aktuellen Test.
        // Vorteil: flexibler und expliziter als eine TestName-Rule als Feld.
        assertAll(
                () -> assertTrue(testInfo.getDisplayName().contains("testInfo_replacesTestNameRule")),
                () -> assertEquals(TimeoutAndTestInfoTest.class, testInfo.getTestClass().orElseThrow()),
                () -> assertTrue(testInfo.getTestMethod().isPresent())
        );
    }
}
