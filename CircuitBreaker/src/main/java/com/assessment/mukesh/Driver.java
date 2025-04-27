package com.assessment.mukesh;

import com.assessment.mukesh.circuitbreaker.*;

public class Driver {
    public static void main(String[] args) {
        countBasedCircuitBreaker();
    }

    private static void countBasedCircuitBreaker() {
        // Driver method to test the Count Based circuit breaker
        CircuitBreaker circuitBreaker =
                CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.COUNT, 3, 3000);

        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("COUNT - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
        CircuitBreakerMetric metric;
        // Simulate some failures
        for (int i = 0; i < 6; i++) {
            if (circuitBreaker.allowRequest()) {
                try {
                    throw new RuntimeException("fail");
                } catch (Exception e) {
                    circuitBreaker.recordFailure();
                }
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);

        if (circuitBreaker.allowRequest()) {
            try {
                throw new RuntimeException("fail");
            } catch (Exception e) {
                circuitBreaker.recordFailure();
            }
        }

        // Simulate a successful call
        circuitBreaker.recordSuccess();

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