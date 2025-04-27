package com.assessment.mukesh.circuitbreaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeBasedCircuitBreakerTest {
    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
        circuitBreaker = CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.TIME, 3, 2000,3000);
    }

    @Test
    void testCircuitBreakerInitiallyClosed() {
        assertTrue(circuitBreaker.allowRequest());
    }

    @Test
    void testCircuitBreakerOpensAfterFailuresWithinTimeWindow() {
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();

        assertFalse(circuitBreaker.allowRequest(), "Circuit should be open after 3 failures");
    }

    @Test
    void testCircuitBreakerDoesNotOpenIfFailuresSpreadOutsideTimeWindow() throws InterruptedException {
        circuitBreaker.recordFailure();
        Thread.sleep(2500); // Wait more than timeWindow (2000ms)
        circuitBreaker.recordFailure();
        Thread.sleep(2500);
        circuitBreaker.recordFailure();

        assertTrue(circuitBreaker.allowRequest(), "Circuit should stay closed as failures are outside time window");
    }

    @Test
    void testCircuitBreakerClosesAfterSuccess() {
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();
        circuitBreaker.recordFailure();

        assertFalse(circuitBreaker.allowRequest(), "Circuit should be open after 3 failures");

        // Now record a success
        circuitBreaker.recordSuccess();

        assertTrue(circuitBreaker.allowRequest(), "Circuit should be closed after a successful call");
    }
}

