package com.example.gateway.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class LeakyBucketRateLimiter implements RateLimiter {

    private static final int CAPACITY = 10;
    private static final int LEAK_RATE = 1;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String clientId) {

        Bucket bucket = buckets.computeIfAbsent(
                clientId,
                key -> new Bucket()
        );

        synchronized (bucket) {

            long currentTime = System.currentTimeMillis();

            long elapsed =
                    (currentTime - bucket.lastLeakTime) / 1000;

            if (elapsed > 0) {

                int leaked =
                        (int) (elapsed * LEAK_RATE);

                bucket.water =
                        Math.max(0, bucket.water - leaked);

                bucket.lastLeakTime = currentTime;
            }

            if (bucket.water >= CAPACITY) {
                return false;
            }

            bucket.water++;

            return true;
        }
    }

    private static class Bucket {

        int water = 0;

        long lastLeakTime = System.currentTimeMillis();
    }
}