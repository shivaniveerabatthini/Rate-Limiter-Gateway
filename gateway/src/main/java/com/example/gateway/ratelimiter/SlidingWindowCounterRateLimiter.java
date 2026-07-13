package com.example.gateway.ratelimiter;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlidingWindowCounterRateLimiter implements RateLimiter {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SIZE = 60_000;

    private final Map<String, Window> windows = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String clientId) {

        long currentTime = System.currentTimeMillis();

        Window window = windows.computeIfAbsent(
                clientId,
                key -> new Window(currentTime)
        );

        synchronized (window) {

            long elapsed = currentTime - window.windowStart;

            if (elapsed >= WINDOW_SIZE) {

                window.previousCount = window.currentCount;
                window.currentCount = 0;
                window.windowStart = currentTime;

                elapsed = 0;
            }

            double weight =
                    (double) (WINDOW_SIZE - elapsed) / WINDOW_SIZE;

            double estimatedCount =
                    (window.previousCount * weight)
                            + window.currentCount;

            if (estimatedCount >= MAX_REQUESTS) {
                return false;
            }

            window.currentCount++;

            return true;
        }
    }

    private static class Window {

        long windowStart;

        int currentCount;

        int previousCount;

        Window(long windowStart) {
            this.windowStart = windowStart;
        }
    }
}