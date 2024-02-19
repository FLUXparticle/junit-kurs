package com.example.calculator;

import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.*;
import static org.junit.jupiter.api.DynamicTest.*;

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
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {

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

        @Test
        void exception() {
            assertThrowsExactly(ArithmeticException.class, () -> {
                calculator.divide(9, 0);
            });
        }

    }

    @Nested
    @DisplayName("Permutation Tests")
    class PermutationTests {

        @TestFactory
        Stream<DynamicContainer> dynamicTestsWithCollection() {
            List<Integer> values = List.of(1, 2, 3);

            return values.stream()
                    .map(a -> dynamicContainer("First Value: " + a, values.stream()
                            .map(b -> dynamicTest("Second Value: " + b, () -> assertEquals(a + b, calculator.add(a, b))))
                    ));
        }

    }

}
