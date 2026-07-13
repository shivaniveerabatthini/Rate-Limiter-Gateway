package com.example.gateway.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;

@Component
public class ProductClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Retry(name = "productService")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Bulkhead(name = "productService", fallbackMethod = "fallback")
    public String getProducts() {
        return restTemplate.getForObject(
                "http://product-service:9090/products",
                String.class
        );
    }

    public String fallback(Throwable t) {
        return """
                {
                  "message":"Product Service is currently unavailable."
                }
                """;
    }
}