package com.example.calculator;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Calculator4Test.AdditionTests.class,
        Calculator4Test.MultiplicationTests.class
})
public class Calculator4Test {

    private static final Calculator calculator = new Calculator();

    public static class AdditionTests {

        @Test
        public void addTwoNumbers() {
            int result = calculator.add(3, 5);
            assertEquals(8, result);
        }

        @Test
        public void subtractTwoNumbers() {
            int result = calculator.subtract(7, 4);
            assertEquals(3, result);
        }


    }

    public static class MultiplicationTests {

        @Test
        public void multiplyTwoNumbers() {
            int result = calculator.multiply(2, 6);
            assertEquals(12, result);
        }

        @Test
        public void divideTwoNumbers() {
            int result = calculator.divide(9, 3);
            assertEquals(3, result);
        }

        @Test(expected = ArithmeticException.class)
        public void exception() {
            calculator.divide(9, 0);
        }

    }

}
