package com.assessment.mukesh.circuitbreaker;

public class CountBasedCircuitBreaker extends CircuitBreaker{
    private long lastFailureTime = 0;

    public CountBasedCircuitBreaker(int failureThreshold, long retryDuration){
        this.failureThreshold = failureThreshold;
        this.retryTimePeriod = retryDuration;
    }

    @Override
    public synchronized boolean allowRequest() {
        if (state == State.OPEN) {
            if ((System.currentTimeMillis() - lastFailureTime) > retryTimePeriod) {
                changeState(State.HALF_OPEN);
                return true; // Allow a test request
            } else {
                return false;
            }
        }
        // HALF  OPEN or CLOSED
        else {
            return true;
        }
    }

    @Override
    public synchronized void recordSuccess() {
        failureCount = 0;
        changeState(State.CLOSED);
    }

    @Override
    public synchronized void recordFailure() {
        if (state == State.HALF_OPEN) {
            changeState( State.OPEN);
            lastFailureTime = System.currentTimeMillis();
            failureCount = failureThreshold;
        } else {
            failureCount++;
            if (failureCount >= failureThreshold) {
                changeState(State.OPEN);
                lastFailureTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public String getType(){
        return CircuitBreakerType.COUNT.toString();
    }
}
