package com.assessment.mukesh.circuitbreaker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MetricLogger {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final LogHelper log = LogHelper.getLogger(MetricLogger.class);

    public static void logCircuitBreakerMetrics(CircuitBreakerMetric metrics) {
        try {
            String serializedMetrics = objectMapper.writeValueAsString(metrics);
            log.info(serializedMetrics);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
