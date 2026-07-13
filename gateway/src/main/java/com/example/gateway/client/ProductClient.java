package com.example.gateway.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String PRODUCT_SERVICE_URL =
            "https://product-service-bd11.onrender.com/products";

    @Retry(name = "productService")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Bulkhead(name = "productService", fallbackMethod = "fallback")
    public String getProducts() {

        return restTemplate.getForObject(PRODUCT_SERVICE_URL, String.class);

    }

    public String fallback(Throwable t) {

        System.out.println("====================================");
        System.out.println("PRODUCT SERVICE CALL FAILED");
        System.out.println("Exception Type : " + t.getClass().getName());
        System.out.println("Exception Msg  : " + t.getMessage());
        t.printStackTrace();
        System.out.println("====================================");

        return """
                {
                  "message":"Product Service is currently unavailable."
                }
                """;
    }
}