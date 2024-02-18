package com.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Calculator Tests")
public class Calculator5Test {

    private static final Calculator calculator = new Calculator();

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {

        @Test
        void addTwoNumbers() {
            int result = calculator.add(3, 5);
            assertEquals(8, result);
        }

        @Test
        void subtractTwoNumbers() {
            int result = calculator.subtract(7, 4);
            assertEquals(3, result);
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {

        @Test
        void multiplyTwoNumbers() {
            int result = calculator.multiply(2, 6);
            assertEquals(12, result);
        }

        @Test
        void divideTwoNumbers() {
            int result = calculator.divide(9, 3);
            assertEquals(3, result);
        }

    }
}
