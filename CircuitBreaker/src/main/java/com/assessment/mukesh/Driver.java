package com.assessment.mukesh;

import com.assessment.mukesh.circuitbreaker.*;

import java.util.function.Supplier;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        countBasedCircuitBreaker();
        timeBasedCircuitBreaker();
    }

    private static void countBasedCircuitBreaker() throws InterruptedException {
        // Driver method to test the Count Based circuit breaker
        CircuitBreaker circuitBreaker =
                CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.COUNT, 3, 3000);

        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("COUNT - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
        Supplier<String> problematicRPC = () -> {
            throw new RuntimeException("NOT SUCCESSFUL!");
        };

        Supplier<String> fallbackRPC = () -> {
            return ("FALLBACK SUCCESSFUL!");
        };
        CircuitBreakerMetric metric;
        // Simulate some failures
        for (int i = 0; i < 5; i++) {
            String response = circuitBreaker.call(problematicRPC, fallbackRPC);
            System.out.println("Response: " + response);
            Thread.sleep(500);
        }
        metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);
    }

    private static void timeBasedCircuitBreaker() throws InterruptedException {
        // Driver method to test the Count Based circuit breaker
        CircuitBreaker circuitBreaker =
                CircuitBreakerFactory.createCircuitBreaker(CircuitBreakerType.TIME, 5, 1000,1000);

        circuitBreaker.setEventListener((oldState, newState) -> {
            System.out.println("TIME - Change from " + oldState + " to " + newState + " at " + System.currentTimeMillis());
        });
        Supplier<String> problematicRPC = () -> {
            throw new RuntimeException("NOT SUCCESSFUL!");
        };

        Supplier<String> fallbackRPC = () -> {
            return ("FALLBACK SUCCESSFUL!");
        };
        for (int i = 0; i < 5; i++) {
            String response = circuitBreaker.call(problematicRPC, fallbackRPC);
            System.out.println("Response: " + response);
            Thread.sleep(700); // within 2 seconds sliding window
        }

        CircuitBreakerMetric metric = circuitBreaker.getMetric();
        MetricLogger.logCircuitBreakerMetrics(metric);
    }
}