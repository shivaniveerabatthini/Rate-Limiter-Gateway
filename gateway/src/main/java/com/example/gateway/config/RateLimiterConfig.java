package com.example.gateway.config;

import com.example.gateway.ratelimiter.FixedWindowRateLimiter;
import com.example.gateway.ratelimiter.LeakyBucketRateLimiter;
import com.example.gateway.ratelimiter.RateLimiter;
import com.example.gateway.ratelimiter.SlidingWindowCounterRateLimiter;
import com.example.gateway.ratelimiter.TokenBucketRateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    @Value("${rate-limiter.algorithm}")
    private String algorithm;

    private final TokenBucketRateLimiter tokenBucketRateLimiter;
    private final SlidingWindowCounterRateLimiter slidingWindowCounterRateLimiter;
    private final FixedWindowRateLimiter fixedWindowRateLimiter;
    private final LeakyBucketRateLimiter leakyBucketRateLimiter;

    public RateLimiterConfig(
            TokenBucketRateLimiter tokenBucketRateLimiter,
            SlidingWindowCounterRateLimiter slidingWindowCounterRateLimiter,
            FixedWindowRateLimiter fixedWindowRateLimiter,
            LeakyBucketRateLimiter leakyBucketRateLimiter) {

        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
        this.slidingWindowCounterRateLimiter = slidingWindowCounterRateLimiter;
        this.fixedWindowRateLimiter = fixedWindowRateLimiter;
        this.leakyBucketRateLimiter = leakyBucketRateLimiter;
    }

    @Bean
    public RateLimiter rateLimiter() {

        return switch (algorithm.toLowerCase()) {

            case "token-bucket" -> tokenBucketRateLimiter;

            case "sliding-window",
                 "sliding-window-counter" -> slidingWindowCounterRateLimiter;

            case "fixed-window" -> fixedWindowRateLimiter;

            case "leaky-bucket" -> leakyBucketRateLimiter;

            default -> fixedWindowRateLimiter;
        };
    }
}