package com.assessment.mukesh.circuitbreaker;

public class CircuitBreakerFactory {
    public static CircuitBreaker createCircuitBreaker(CircuitBreakerType type, int failureThreshold, int retryDuration, long... timeWindow) {
        switch (type) {
            case COUNT:
                return new CountBasedCircuitBreaker(failureThreshold, retryDuration);
            case TIME:
                return new TimeBasedCircuitBreaker();
            default:
                throw new IllegalArgumentException("Invalid circuit breaker type");
        }
    }
}
