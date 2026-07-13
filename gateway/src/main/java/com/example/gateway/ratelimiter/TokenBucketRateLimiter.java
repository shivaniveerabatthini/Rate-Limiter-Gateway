package com.example.gateway.ratelimiter;

import com.example.gateway.model.Bucket;
import com.example.gateway.model.ClientTier;
import com.example.gateway.model.RateLimitConfig;
import com.example.gateway.service.ClientTierService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketRateLimiter implements RateLimiter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final ClientTierService clientTierService;

    public TokenBucketRateLimiter(ClientTierService clientTierService) {
        this.clientTierService = clientTierService;
    }

    @Override
    public boolean allowRequest(String clientId) {

        ClientTier tier = clientTierService.getTier(clientId);

        RateLimitConfig config = getConfig(tier);

        Bucket bucket = buckets.computeIfAbsent(
                clientId,
                key -> new Bucket(
                        config.getCapacity(),
                        config.getRefillRate()
                )
        );

        refill(bucket);

        synchronized (bucket) {

            if (bucket.getTokens() <= 0) {
                return false;
            }

            bucket.setTokens(bucket.getTokens() - 1);

            return true;
        }
    }

    private void refill(Bucket bucket) {

        long currentTime = System.currentTimeMillis();

        long elapsed =
                (currentTime - bucket.getLastRefillTime()) / 1000;

        if (elapsed <= 0) {
            return;
        }

        int tokensToAdd =
                (int) (elapsed * bucket.getRefillRate());

        bucket.setTokens(
                Math.min(
                        bucket.getCapacity(),
                        bucket.getTokens() + tokensToAdd
                )
        );

        bucket.setLastRefillTime(currentTime);
    }

    private RateLimitConfig getConfig(ClientTier tier) {

        return switch (tier) {

            case FREE ->
                    new RateLimitConfig(10, 1);

            case PREMIUM ->
                    new RateLimitConfig(100, 10);

            case ADMIN ->
                    new RateLimitConfig(1000, 100);
        };
    }
}