package com.example.greeting;

import com.example.greeting.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Greeting5Test {

    @BeforeAll
    static void setupAll() {
        System.out.println("setupAll()");
    }

    @BeforeEach
    void setup() {
        System.out.println("  setup()");
    }

    @Test
    @Tag("FastTest")
    @Tag("Important")
    void testWorld() {
        System.out.println("    testWorld()");
        String expected = "Hello World!";
        String actual = Greeting.getGreeting("World");
        assertEquals(expected, actual, () -> String.format("'%s' expected but got '%s'", expected, actual));
    }

    @Test
    @Tag("Important")
    void testMars() {
        System.out.println("    testMars()");
        String expected = "Hello Mars!";
        String actual = Greeting.getGreeting("Mars");
        assertEquals(expected, actual, () -> String.format("'%s' expected but got '%s'", expected, actual));
    }

    @AfterEach
    void tearDown() {
        System.out.println("  tearDown()");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("tearDownAll()");
    }

}
