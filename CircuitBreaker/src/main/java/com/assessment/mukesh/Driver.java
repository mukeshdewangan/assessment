package com.assessment.mukesh;

import com.assessment.mukesh.circuitbreaker.*;

import java.util.Map;

public class Driver {
    public static void main(String[] args) {

        // Driver method to test the Count Based circuit breaker
        CircuitBreaker circuitBreaker =
                CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.COUNT, 5, 3000);

        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("Count - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
        // Simulate some failures
        for (int i = 0; i < 3; i++) {
            if (circuitBreaker.allowRequest()) {
                try {
                    throw new RuntimeException("fail");
                } catch (Exception e) {
                    circuitBreaker.recordFailure();
                }
            }
        }

        CircuitBreakerMetric metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);

    }
}