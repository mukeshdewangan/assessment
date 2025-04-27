package com.assessment.mukesh.circuitbreaker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class CountBasedCircuitBreakerTest extends CircuitBreakerBaseTest {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        circuitBreaker = CircuitBreakerFactory
                .createCircuitBreaker(CircuitBreakerType.COUNT, 3, 2000);
        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("Count - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
    }

    @Test
    public void testCircuitOpensAfterFailures() {
        // Act and Assert
        for (int i = 0; i < 3; i++) {
            assertEquals("FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC, fallbackRPC));
        }
        try {
            Thread.sleep(2000);
            assertEquals( "RPC SUCCESSFUL!", circuitBreaker.call(healthyRPC,fallbackRPC));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCircuitHalfOpenAfterRetryTime() throws InterruptedException {
        assertEquals("FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC, fallbackRPC));
        assertEquals("FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC, fallbackRPC));
        assertEquals("FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC, fallbackRPC));

        assertEquals(State.OPEN.toString(), circuitBreaker.getState());
        Thread.sleep(2100); // Sleep longer than retryTimePeriod

        assertEquals("RPC SUCCESSFUL!" ,circuitBreaker.call(healthyRPC, fallbackRPC));
        assertEquals(State.HALF_OPEN.toString(), circuitBreaker.getState());
        assertEquals("RPC SUCCESSFUL!" ,circuitBreaker.call(healthyRPC, fallbackRPC));
        assertEquals(State.CLOSED.toString(), circuitBreaker.getState());
    }
}

