package com.example.gateway.ratelimiter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisFixedWindowRateLimiter implements RateLimiter {
    private static final int MAX_REQUESTS = 10;
    private static final int WINDOW_SECONDS = 60;

    private final StringRedisTemplate redisTemplate;

    public RedisFixedWindowRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean allowRequest(String clientId) {

        String key = "rate_limit:" + clientId;

        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
        }

        return count != null && count <= MAX_REQUESTS;
    }
}