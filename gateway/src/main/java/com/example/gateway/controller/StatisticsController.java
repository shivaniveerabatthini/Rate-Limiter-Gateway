package com.example.gateway.controller;

import com.example.gateway.service.ApiUsageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatisticsController {

    private final ApiUsageService apiUsageService;

    public StatisticsController(ApiUsageService apiUsageService) {
        this.apiUsageService = apiUsageService;
    }

    @GetMapping("/statistics")
    public Map<String, Integer> getStatistics() {
        return apiUsageService.getUsage();
    }
}