package com.assessment.mukesh;

import com.assessment.mukesh.circuitbreaker.*;

import java.util.Map;

public class Driver {
    public static void main(String[] args) {
        countBasedCircuitBreaker();
    }

    private static void countBasedCircuitBreaker() {
        // Driver method to test the Count Based circuit breaker
        CircuitBreaker circuitBreaker =
                CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.COUNT, 3, 10000);

        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("COUNT - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
        // Simulate some failures
        for (int i = 0; i < 4; i++) {
            if (circuitBreaker.allowRequest()) {
                try {
                    throw new RuntimeException("fail");
                } catch (Exception e) {
                    circuitBreaker.recordFailure();
                }
            }
        }
        circuitBreaker.allowRequest();
        CircuitBreakerMetric metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        circuitBreaker.allowRequest();
        metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);
    }

    private static void timeBasedCircuitBreaker() {
        // Driver method to test the Count Based circuit breaker
        CircuitBreaker circuitBreaker =
                CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.TIME, 5, 1000);

        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("TIME - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        CircuitBreakerMetric metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);
    }
}