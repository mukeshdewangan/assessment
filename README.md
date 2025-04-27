# It is a sample maven based project on the Circuit Breaker. 

Below are steps to start using this Circuit Breaker  
git clone `https://github.com/mukeshdewangan/assessment.git`
cd `CircuitBreaker`
`mvn clean install`


The major features of the this Circuit Breaker -  
- It provides in memory circuit breaker for an application that serves large number of requests.
- Two variant of Circuit breaker can be created - 
   1. CountBasedCircuitBreaker
   2. TimeBasedCircuitBreaker
- `CircuitBreaker` is Base class where common variables and common method are placed. `CountBasedCircuitBreaker` and `TimeBasedCircuitBreaker` are child classes and contains specific implementation according to strategy.
- These variants can be created via Factory method - `CircuitBreakerFactory.createCircuitBreaker()`.
- For understanding the sample usage, you can refer to `Driver.java`
- To support the scaling and concurrency control, the required methods like `recordSuccess`, `recordFailure` are synchronized .  
- The parameters for these CircuitBreakers can be fine-tuned such as the `failureThreshold`, `duration of retry`, `timeWindow` for TimeBasedCircuitBreaker. 
- To call the rpc and fallback method we are using the Supplier<T> functional interface.
- Circuit breaker support emitting metrics.
- Logging features enabled using slf4j and logback.

Added test cases 
`CircuitBreakerBaseTest` is the BaseTest class which is extended by `CountBasedCircuitBreakerTest` and `TimeBasedCircuitBreakerTest`.
