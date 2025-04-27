package com.assessment.mukesh.circuitbreaker;

import java.util.Queue;
import java.util.LinkedList;

public class TimeBasedCircuitBreaker extends CircuitBreaker {
    private long timeWindow; // Time window in milliseconds
    private Queue<Long> failureTimestamps = new LinkedList<>();

    public TimeBasedCircuitBreaker(int failureThreshold, long timeWindow) {
        this.failureThreshold = failureThreshold;
        this.timeWindow = timeWindow;
    }

    @Override
    public synchronized boolean allowRequest() {
        if (state == State.OPEN) {
            return false; // Block requests when OPEN
        }
        return true; // Allow requests when CLOSED or HALF_OPEN
    }

    @Override
    public synchronized void recordFailure() {
        long now = System.currentTimeMillis();
        failureTimestamps.add(now);

        // Sliding window approach to remove old failures outside time window
        while (!failureTimestamps.isEmpty() && now - failureTimestamps.peek() > timeWindow) {
            failureTimestamps.poll();
        }

        if (failureTimestamps.size() >= failureThreshold) {
            changeState(State.OPEN);
        }
    }

    @Override
    public synchronized void recordSuccess() {
        failureTimestamps.clear();
        changeState(State.CLOSED);
    }

    @Override
    public String getType(){
        return CircuitBreakerType.TIME.toString();
    }
}
