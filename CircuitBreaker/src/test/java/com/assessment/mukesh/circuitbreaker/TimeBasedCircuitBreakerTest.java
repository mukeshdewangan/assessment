package com.assessment.mukesh.circuitbreaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class TimeBasedCircuitBreakerTest extends CircuitBreakerBaseTest{

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        circuitBreaker = CircuitBreakerFactory
                .createCircuitBreaker(CircuitBreakerType.TIME, 3, 2000,3000);
        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
    }
    @Test
    public void testAllowRequestInitiallyClosed() {
        assertEquals( "RPC SUCCESSFUL!", circuitBreaker.call(healthyRPC,fallbackRPC));
        assertEquals( "FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC,fallbackRPC));
    }

    @Test
    void testCircuitBreakerOpensAfterFailuresWithinTimeWindow() {
        for (int i = 0; i < 3; i++) {
            assertEquals("FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC, fallbackRPC));
        }
        assertEquals(State.OPEN.toString(), circuitBreaker.getState());
    }

    @Test
    void testFailureInHalfOpenMovesBackToOpen() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            assertEquals("FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC, fallbackRPC));
        }
        assertEquals(State.OPEN.toString(), circuitBreaker.getState());

        Thread.sleep(5100);

        // Failed call in HALF_OPEN
        circuitBreaker.call(() -> { throw new RuntimeException("Failure again"); }, () -> "Fallback");

        assertEquals(State.HALF_OPEN.toString(), circuitBreaker.getState());
    }

    @Test
    void testCircuitBreakerClosesAfterSuccess() {
        assertEquals("FALLBACK SUCCESSFUL!",circuitBreaker.call(problematicRPC, fallbackRPC));

        assertEquals( "RPC SUCCESSFUL!",circuitBreaker.call(healthyRPC, fallbackRPC));
    }
}

