package com.assessment.mukesh.circuitbreaker;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CustomCircuitBreakerTest {

    @Test
    public void test() {
        CustomCircuitBreaker circuitBreaker = new CustomCircuitBreaker(1, 1000);

        assertTrue(circuitBreaker.allowRequest());
    }
}

