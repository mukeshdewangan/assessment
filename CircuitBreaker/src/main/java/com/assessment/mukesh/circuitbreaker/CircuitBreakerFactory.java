package com.assessment.mukesh.circuitbreaker;

public class CircuitBreakerFactory {
    public static CircuitBreaker createCircuitBreaker(CircuitBreakerType type, int failureThreshold, int retryDuration, long... timeWindow) {
        switch (type) {
            case COUNT:
                return new CountBasedCircuitBreaker(failureThreshold, retryDuration);
            case TIME:
                if (timeWindow.length!= 1) {
                    throw new IllegalArgumentException("Invalid time window");
                }
                return new TimeBasedCircuitBreaker(failureThreshold,timeWindow[0]);
            default:
                throw new IllegalArgumentException("Invalid circuit breaker type");
        }
    }
}
