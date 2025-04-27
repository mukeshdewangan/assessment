package com.assessment.mukesh.circuitbreaker;

public class CircuitBreakerMetric {
    private String circuitBreakerType;
    private String state;
    private int totalFailureCount;
    private int totalSuccessCount;
    private int failuresInTimeWindow;
    private long lastFailureTime;
    private long timeSinceLastFailureMs;

    private CircuitBreakerMetric(Builder builder) {
        this.state = builder.state;
        this.totalFailureCount = builder.totalFailureCount;
        this.failuresInTimeWindow = builder.failuresInTimeWindow;
        this.lastFailureTime = builder.lastFailureTime;
        this.timeSinceLastFailureMs = builder.timeSinceLastFailureMs;
        this.circuitBreakerType = builder.type;
    }

    public CircuitBreakerMetric(){

    }
    // Getters (optional if you want immutability)
    public String getState() {
        return state;
    }

    public void setCurrentState(State currentState) {
        this.state = currentState.toString();
    }

    public int getTotalFailureCount() {
        return totalFailureCount;
    }

    public int getFailuresInTimeWindow() {
        return failuresInTimeWindow;
    }

    public long getLastFailureTime() {
        return lastFailureTime;
    }

    public long getTimeSinceLastFailureMs() {
        return timeSinceLastFailureMs;
    }

    public String getCircuitBreakerType() {
        return circuitBreakerType;
    }

    public synchronized void recordFailure() {
        totalFailureCount++;
    }

    public synchronized void recordSuccess() {
        totalSuccessCount++;
    }


    // Builder class
    public static class Builder {
        private String state;
        private int totalFailureCount;
        private int failuresInTimeWindow;
        private long lastFailureTime;
        private long timeSinceLastFailureMs;
        private String type;

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder totalFailureCount(int totalFailureCount) {
            this.totalFailureCount = totalFailureCount;
            return this;
        }

        public Builder failuresInTimeWindow(int failuresInTimeWindow) {
            this.failuresInTimeWindow = failuresInTimeWindow;
            return this;
        }

        public Builder lastFailureTime(long lastFailureTime) {
            this.lastFailureTime = lastFailureTime;
            return this;
        }

        public Builder timeSinceLastFailureMs(long timeSinceLastFailureMs) {
            this.timeSinceLastFailureMs = timeSinceLastFailureMs;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public CircuitBreakerMetric build() {
            return new CircuitBreakerMetric(this);
        }
    }
}
