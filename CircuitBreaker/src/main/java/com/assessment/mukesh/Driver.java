package com.assessment.mukesh;

import com.assessment.mukesh.circuitbreaker.*;

import java.util.Map;

public class Driver {
    public static void main(String[] args) {
        CircuitBreaker circuitBreaker = CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.COUNT, 5, 3000);

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

        // Fetch and print metrics
        CircuitBreakerMetric metric = new CircuitBreakerMetric.Builder().
                state(circuitBreaker.getState()).
                type(circuitBreaker.getType()).
                totalFailureCount(circuitBreaker.getFailureCount()).
                build();

        new MetricLogger().logCircuitBreakerMetrics(metric);

    }
}