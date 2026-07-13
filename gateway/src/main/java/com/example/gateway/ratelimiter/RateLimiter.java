package com.example.gateway.ratelimiter;

public interface RateLimiter {

    boolean allowRequest(String clientId);

}