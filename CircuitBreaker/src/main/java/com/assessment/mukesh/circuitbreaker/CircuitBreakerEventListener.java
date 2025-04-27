package com.assessment.mukesh.circuitbreaker;

// Functional interface
public interface CircuitBreakerEventListener {
    void onStateChange(State oldState, State newState);
}

