package com.example.gateway.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiUsageService {

    private final Map<String, Integer> usage = new ConcurrentHashMap<>();

    public void increment(String clientId) {
        usage.merge(clientId, 1, Integer::sum);
    }

    public Map<String, Integer> getUsage() {
        return usage;
    }
}