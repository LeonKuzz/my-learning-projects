package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class FibonacciCalculatorTest {

    private FibonacciCalculator calculator;

    @BeforeEach
    public void setUp () {
        calculator = new FibonacciCalculator();
    }

    @Test
    @DisplayName("given negative index when getFibonacciNumber then exception")
    public void givenNegativeIndex_whenGetFibonacciNumber_thenException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.getFibonacciNumber(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10 ,15})
    @DisplayName("given various index when getFibonacciNumber then number")
    public void givenVariousIndex_whenGetFibonacciNumber_thenNumber (int index) {
        Integer execute = calculator.getFibonacciNumber(index);
        if (index == 1 || index == 2) {
            assertEquals(1, execute);
        }
        if (index == 10) {
            assertEquals(55, execute);
        }
        if (index == 15) {
            assertEquals(610, execute);
        }
    }
}
