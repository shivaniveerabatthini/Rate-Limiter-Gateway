package com.example.gateway.ratelimiter;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

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

            if (currentTime - window.windowStart >= WINDOW_SIZE) {

                window.windowStart = currentTime;
                window.requestCount = 0;
            }

            if (window.requestCount >= MAX_REQUESTS) {
                return false;
            }

            window.requestCount++;

            return true;
        }
    }

    private static class Window {

        long windowStart;

        int requestCount;

        Window(long windowStart) {
            this.windowStart = windowStart;
        }
    }
}