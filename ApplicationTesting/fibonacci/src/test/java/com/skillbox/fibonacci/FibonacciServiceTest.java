package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FibonacciServiceTest {

    private FibonacciService fibonacciService;
    private final FibonacciRepository repository = Mockito.mock(FibonacciRepository.class);
    private final FibonacciCalculator calculator = Mockito.mock(FibonacciCalculator.class);
    @BeforeEach
    public void setUp () {
    fibonacciService = new FibonacciService(repository, calculator);
        FibonacciNumber fibonacciNumber = new FibonacciNumber(2,1);
        when(repository.findByIndex(2)).thenReturn(Optional.of(fibonacciNumber));
    }

    @Test
    @DisplayName("given illegal index when fibonacciNumber then exception")
    public void givenIllegalIndex_whenFibonacciNumber_thenException () {
        assertThrows(IllegalArgumentException.class, () -> fibonacciService.fibonacciNumber(-1));
    }

    @Test
    @DisplayName("given added index when fibonacciNumber then number loaded")
    public void givenAddedIndex_whenFibonacciNumber_thenNumberLoaded () {
        int value = fibonacciService.fibonacciNumber(2).getValue();
        verify(repository, times(1)).findByIndex(2);
        assertEquals(1, value);
        verify(repository, times(0)).save(any(FibonacciNumber.class));
    }

    @Test
    @DisplayName("given not added index when fibonacciNumber then create number")
    public void givenNotAddedIndex_whenFibonacciNumber_thenCreateNumber () {
        FibonacciNumber number = fibonacciService.fibonacciNumber(10);
        verify(repository, times(1)).findByIndex(10);
        verify(repository, times(1)).save(number);
    }
}
