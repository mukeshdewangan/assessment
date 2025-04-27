package com.assessment.mukesh.circuitbreaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CircuitBreakerBaseTest{
    protected CircuitBreaker circuitBreaker;
    protected Supplier<String> problematicRPC;
    protected Supplier<String> healthyRPC;
    protected Supplier<String> fallbackRPC;
    @BeforeEach
    void setUp() {
        problematicRPC = () -> {
            throw new RuntimeException("NOT SUCCESSFUL!");
        };
        fallbackRPC = () -> {
            return "FALLBACK SUCCESSFUL!";
        };
        healthyRPC = () -> {
            return "RPC SUCCESSFUL!";
        };
    }

    @Test
    public void testAllowRequestInitiallyClosed() {
        assertEquals( "RPC SUCCESSFUL!", circuitBreaker.call(healthyRPC,fallbackRPC));
        assertEquals( "FALLBACK SUCCESSFUL!", circuitBreaker.call(problematicRPC,fallbackRPC));
    }
}
