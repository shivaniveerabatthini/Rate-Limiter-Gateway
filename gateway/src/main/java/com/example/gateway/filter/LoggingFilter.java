package com.example.gateway.filter;

import com.example.gateway.service.ApiUsageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final ApiUsageService apiUsageService;

    public LoggingFilter(ApiUsageService apiUsageService) {
        this.apiUsageService = apiUsageService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String clientId = request.getHeader("X-API-Key");

        if (clientId == null || clientId.isBlank()) {
            clientId = request.getRemoteAddr();
        }

        apiUsageService.increment(clientId);

        filterChain.doFilter(request, response);

        long end = System.currentTimeMillis();

        System.out.println("========================================");
        System.out.println("Request URI : " + request.getRequestURI());
        System.out.println("Method      : " + request.getMethod());
        System.out.println("Client      : " + clientId);
        System.out.println("Status      : " + response.getStatus());
        System.out.println("Time Taken  : " + (end - start) + " ms");
        System.out.println("========================================");
    }
}