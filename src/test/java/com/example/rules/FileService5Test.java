package com.example.rules;

import com.example.files.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.*;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FileService5Test {

    // JUnit 5 erzeugt pro Test ein temporäres Verzeichnis und räumt es automatisch auf.
    // Vorteil gegenüber TemporaryFolder-Rule: weniger Boilerplate und keine manuelle Pflege.
    @TempDir
    Path tempDir;

    @Test
    void tempDirAndAssertAll_replaceTemporaryFolderAndErrorCollector() throws IOException {
        FileService fileService = new FileService();
        Path file = tempDir.resolve("cocktail.txt");
        Files.writeString(file, "Mojito");

        // assertAll sammelt mehrere fachlich zusammengehörige Checks.
        // Vorteil: alle Fehlschläge werden in einem Lauf sichtbar (wie ErrorCollector-Idee).
        assertAll(
                () -> assertTrue(fileService.exists(file)),
                () -> assertTrue(fileService.isReadable(file)),
                () -> assertEquals("Mojito", fileService.read(file))
        );
    }

    @Test
    void assertThrowsExactly_replacesExpectedExceptionRule() {
        FileService fileService = new FileService();
        Path file = tempDir.resolve("missing.txt");

        // Erwartete Exception wird lokal direkt am Aufruf geprüft.
        // Vorteil: klarer Kontrollfluss, kein globaler Rule-Zustand.
        var exception = assertThrowsExactly(
                IllegalStateException.class,
                () -> fileService.read(file)
        );

        assertTrue(exception.getMessage().contains("does not exist"));
    }
}
