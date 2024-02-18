package com.example.greeting;

import org.junit.*;
import org.junit.experimental.categories.*;

import static org.junit.Assert.*;

public class Greeting4Test {

    @BeforeClass
    public static void setupAll() {
        System.out.println("setupAll()");
    }

    @Before
    public void setup() {
        System.out.println("  setup()");
    }

    @Test
    @Category(FastTest.class)
    public void testWorld() {
        System.out.println("    testWorld()");
        String expected = "Hello World!";
        String actual = Greeting.getGreeting("World");
        String msg = String.format("'%s' expected but got '%s'", expected, actual);
        assertEquals(msg, expected, actual);
    }

    @Test
    public void testMars() {
        System.out.println("    testMars()");
        String expected = "Hello Mars!";
        String actual = Greeting.getGreeting("Mars");
        String msg = String.format("'%s' expected but got '%s'", expected, actual);
        assertEquals(msg, expected, actual);
    }

    @After
    public void tearDown() {
        System.out.println("  tearDown()");
    }

    @AfterClass
    public static void tearDownAll() {
        System.out.println("tearDownAll()");
    }

}
