package com.assessment.mukesh.circuitbreaker;

public class CountBasedCircuitBreaker extends CircuitBreaker{
    private long lastFailureTime = 0;
    private int maxCallsInHalfOpen = 2;
    private int callCount =0;
    public CountBasedCircuitBreaker(int failureThreshold, long retryDuration){
        this.failureThreshold = failureThreshold;
        this.retryTimePeriod = retryDuration;
        this.metric = new CircuitBreakerMetric();
    }

//    @Override
//    public synchronized boolean allowRequest() {
//        if (state == State.OPEN) {
//            if ((System.currentTimeMillis() - lastFailureTime) > retryTimePeriod) {
//                changeState(State.HALF_OPEN);
//                return true; // Allow a test request
//            } else {
//                return false;
//            }
//        }
//        // HALF OPEN or CLOSED
//        else {
//            return true;
//        }
//    }

    @Override
    public synchronized void recordSuccess() {
        if (state == State.HALF_OPEN) {
            callCount++;
            if (callCount >= maxCallsInHalfOpen) {
                changeState(State.CLOSED);
                callCount = 0;
            }
        }
        metric.recordSuccess();
    }

    @Override
    public synchronized void recordFailure() {
        failureCount++;
        if (state == State.HALF_OPEN) {
            changeState(State.OPEN);
        } else if (failureCount >= failureThreshold) {
            changeState(State.OPEN);
            failureCount = 0;
        }
        metric.recordFailure();
    }

    @Override
    public String getType(){
        return CircuitBreakerType.COUNT.toString();
    }
}
