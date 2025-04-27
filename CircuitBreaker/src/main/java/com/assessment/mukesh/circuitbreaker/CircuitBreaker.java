package com.assessment.mukesh.circuitbreaker;

public abstract class CircuitBreaker {
    protected int failureThreshold = 3;
    protected int failureCount = 0;
    protected long retryTimePeriod = 3000;
    protected State state = State.CLOSED;
    public abstract boolean allowRequest();
    public abstract void recordFailure();
    public abstract void recordSuccess();
    public abstract String getType();

    public String getState() {
        return state.toString();
    }
    public int getFailureCount() {
        return failureCount;
    }
    public int getFailureThreshold() {
        return failureThreshold;
    }
    public long getRetryTimePeriod() { return retryTimePeriod;}

}
