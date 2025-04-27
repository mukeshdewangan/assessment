package com.assessment.mukesh.circuitbreaker;

public abstract class CircuitBreaker {
    protected int failureThreshold = 3;
    protected int failureCount = 0;
    protected long retryTimePeriod = 3000;
    protected State state = State.CLOSED;
    protected long stateChangeTime = 0;
    protected CircuitBreakerEventListener eventListener;
    public abstract boolean allowRequest();
    public abstract void recordFailure();
    public abstract void recordSuccess();
    public abstract String getType();
    protected CircuitBreakerMetric metric;

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
    public void setEventListener(CircuitBreakerEventListener listener){ this.eventListener = listener;}
    protected void changeState(State newState) {
        State oldState = this.state;
        this.state = newState;
        stateChangeTime = System.currentTimeMillis();
        if (eventListener != null) {
            eventListener.onStateChange(oldState, newState);
        }
    }

    public CircuitBreakerMetric getMetric(){
        // Fetch and print metrics
        CircuitBreakerMetric metric = new CircuitBreakerMetric.Builder().
                state(this.getState()).
                type(this.getType()).
                totalFailureCount(this.getFailureCount()).
                build();
        return metric;
    }
}
