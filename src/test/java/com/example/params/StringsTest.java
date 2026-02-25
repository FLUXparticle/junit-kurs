package com.example.params;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class StringsTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
        assertTrue(Strings.isBlank(input));
    }

    @ParameterizedTest
    @NullSource
    void isBlank_ShouldReturnTrueForNullInputs(String input) {
        assertTrue(Strings.isBlank(input));
    }

    @ParameterizedTest
    @EmptySource
    void isBlank_ShouldReturnTrueForEmptyStrings(String input) {
        assertTrue(Strings.isBlank(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isBlank_ShouldReturnTrueForNullAndEmptyStrings(String input) {
        assertTrue(Strings.isBlank(input));
    }

    @ParameterizedTest(name = "{index}: |{0}|")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void isBlank_ShouldReturnTrueForAllTypesOfBlankStrings(String input) {
        assertTrue(Strings.isBlank(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "efg"})
    void isBlank_Fail(String input) {
        assertFalse(Strings.isBlank(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "efg"})
    void isBlank_2D(String input1) {
        assertFalse(Strings.isBlank(input1));
    }

}
