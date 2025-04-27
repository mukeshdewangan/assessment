package com.assessment.mukesh.circuitbreaker;

import com.assessment.mukesh.circuitbreaker.State;
public class CustomCircuitBreaker {

    private State state = State.CLOSED;


    public CustomCircuitBreaker(int failureThreshold, long retryDuration ){

    }

}
