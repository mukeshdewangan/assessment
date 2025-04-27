package com.assessment.mukesh.circuitbreaker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomCircuitBreakerTest {
    private CustomCircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
        circuitBreaker = new CustomCircuitBreaker(3, 2000);
    }

    @Test
    public void testAllowRequestInitiallyClosed() {
        assertTrue(circuitBreaker.allowRequest());
    }

    @Test
    public void testCircuitOpensAfterFailures() {
        // Arrange
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();
        // Act and Assert
        assertTrue(circuitBreaker.allowRequest());

        circuitBreaker.recordFailure();

        assertFalse(circuitBreaker.allowRequest());
    }

    @Test
    public void testCircuitHalfOpenAfterRetryTime() throws InterruptedException {
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure(); // Opens the circuit

        assertFalse(circuitBreaker.allowRequest());

        Thread.sleep(2100); // Sleep longer than retryTimePeriod

        assertTrue(circuitBreaker.allowRequest()) ;
    }

    @Test
    public void testCircuitClosesAfterSuccess() throws InterruptedException {
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure(); // Open

        Thread.sleep(2100); // wait for half-open

        assertTrue(circuitBreaker.allowRequest());

        circuitBreaker.recordSuccess();

        assertTrue(circuitBreaker.allowRequest());
    }
}

