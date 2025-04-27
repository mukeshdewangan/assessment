package com.assessment.mukesh.circuitbreaker;

public class CustomCircuitBreaker {
    private int failureThreshold;
    private int failureCount = 0;
    private long retryTimePeriod;
    private long lastFailureTime = 0;
    private State state = State.CLOSED;

    public CustomCircuitBreaker(int failureThreshold, long retryDuration ){
        this.failureThreshold = failureThreshold;
        this.retryTimePeriod = retryDuration;
    }

    public boolean allowRequest() {
        if (state == State.CLOSED ) return true;
        if (state == State.OPEN) {
            if ((System.currentTimeMillis() - lastFailureTime) > retryTimePeriod) {
                state = State.HALF_OPEN;
                return true; // Allow a test request
            } else {
                return false;
            }
        }
        // HALF  OPEN
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
}
