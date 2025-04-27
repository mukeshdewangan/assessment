package com.assessment.mukesh;

import com.assessment.mukesh.circuitbreaker.CircuitBreakerMetric;
import com.assessment.mukesh.circuitbreaker.CustomCircuitBreaker;
import com.assessment.mukesh.circuitbreaker.MetricLogger;

import java.util.Map;

public class Driver {
    public static void main(String[] args) {

        CustomCircuitBreaker circuitBreaker = new CustomCircuitBreaker(5, 3);

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
                totalFailureCount(circuitBreaker.getTotalFailureCount()).
                failuresInTimeWindow(circuitBreaker.getFailuresInTimeWindow()).
                build();

        new MetricLogger().logCircuitBreakerMetrics(metric);

    }
}