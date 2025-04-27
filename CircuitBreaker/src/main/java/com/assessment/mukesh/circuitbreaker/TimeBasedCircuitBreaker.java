package com.assessment.mukesh.circuitbreaker;

public class TimeBasedCircuitBreaker implements CircuitBreaker{
    @Override
    public boolean allowRequest() {
        return false;
    }

    @Override
    public void recordFailure() {

    }

    @Override
    public void recordSuccess() {

    }
}
