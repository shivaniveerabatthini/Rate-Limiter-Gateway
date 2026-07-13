package com.example.gateway.controller;

import com.example.gateway.client.ProductClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private final ProductClient productClient;

    public GatewayController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/gateway/products")
    public String getProducts() {
        return productClient.getProducts();
    }
}