package com.assessment.mukesh.circuitbreaker;

public class CountBasedCircuitBreaker extends CircuitBreaker{
    private long lastFailureTime = 0;

    public CountBasedCircuitBreaker(int failureThreshold, long retryDuration){
        this.failureThreshold = failureThreshold;
        this.retryTimePeriod = retryDuration;
    }

    public boolean allowRequest() {
        if (state == State.OPEN) {
            if ((System.currentTimeMillis() - lastFailureTime) > retryTimePeriod) {
                state = State.HALF_OPEN;
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
    public void recordSuccess() {
        failureCount = 0;
        state = State.CLOSED;
    }

    public void recordFailure() {
        if (state == State.HALF_OPEN) {
            state = State.OPEN;
            lastFailureTime = System.currentTimeMillis();
            failureCount = failureThreshold;
        } else {
            failureCount++;
            if (failureCount >= failureThreshold) {
                state = State.OPEN;
                lastFailureTime = System.currentTimeMillis();
            }
        }
    }

    public String getType(){
        return CircuitBreakerType.COUNT.toString();
    }
}
