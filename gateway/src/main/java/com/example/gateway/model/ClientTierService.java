package com.example.gateway.service;

import com.example.gateway.model.ClientTier;
import org.springframework.stereotype.Service;

@Service
public class ClientTierService {

    public ClientTier getTier(String apiKey) {

        if (apiKey == null) {
            return ClientTier.FREE;
        }

        return switch (apiKey.toLowerCase()) {

            case "premium-user" -> ClientTier.PREMIUM;

            case "admin-user" -> ClientTier.ADMIN;

            default -> ClientTier.FREE;
        };
    }
}