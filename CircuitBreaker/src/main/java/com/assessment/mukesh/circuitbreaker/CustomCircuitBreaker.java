package com.assessment.mukesh.circuitbreaker;

public class CustomCircuitBreaker {
    private int failureThreshold;    // Number of allowed failures
    private int failureCount = 0;     // Current number of failures
    private long retryTimePeriod;     // Time to wait before retrying (ms)
    private long lastFailureTime = 0;
    private State state = State.CLOSED;

    public CustomCircuitBreaker(int failureThreshold, long retryDuration ){
        this.failureThreshold = failureThreshold;
        this.retryTimePeriod = retryDuration;
    }

    public boolean allowRequest() {
        if (state == State.OPEN) {
            if ((System.currentTimeMillis() - lastFailureTime) > retryTimePeriod) {
                state = State.HALF_OPEN;
                return true; // Allow a test request
            } else {
                // Block the request
                return false;
            }
        }
        // CLOSED or HALF_OPEN
        return true;
    }
    public void recordSuccess() {
        failureCount = 0;
        state = State.CLOSED;
    }

    public void recordFailure() {
        failureCount++;
        if (failureCount >= failureThreshold) {
            state = State.OPEN;
            lastFailureTime = System.currentTimeMillis();
        }
    }
}
