package com.assessment.mukesh.circuitbreaker;

public interface CircuitBreaker {
    boolean allowRequest();
    void recordFailure();
    void recordSuccess();
}
