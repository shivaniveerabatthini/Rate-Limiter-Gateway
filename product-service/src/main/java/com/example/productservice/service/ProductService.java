package com.example.productservice.service;

import com.example.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<Product> getAllProducts() {

        return List.of(
                new Product(1L, "Laptop", 75000),
                new Product(2L, "Mouse", 1200),
                new Product(3L, "Keyboard", 2500),
                new Product(4L, "Monitor", 15000)
        );
    }
}