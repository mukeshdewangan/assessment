package com.assessment.mukesh.circuitbreaker;

public class TimeBasedCircuitBreaker extends CircuitBreaker{
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
    public String getType(){
        return CircuitBreakerType.TIME.toString();
    }
}
