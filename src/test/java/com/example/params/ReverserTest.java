package com.example.params;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class ReverserTest {

    @ParameterizedTest
    @CsvSource({"test,tset", "tEst,tsEt", "Java,avaJ"})
    void commaSeperated(String input, String expected) {
        String actualValue = Reverser.reverseString(input);
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"test:tset", "tEst:tsEt", "Java:avaJ"}, delimiter = ':')
    void colonSeperated(String input, String expected) {
        String actualValue = Reverser.reverseString(input);
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void fromCSVFile(String input, String expected) {
        String actualValue = Reverser.reverseString(input);
        assertEquals(expected, actualValue);
    }

}
