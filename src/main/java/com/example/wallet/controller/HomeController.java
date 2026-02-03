// src/main/java/com/example/wallet/controller/HomeController.java
package com.example.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Wallet Service API is running!<br>" +
                "Available endpoints:<br>" +
                "GET /api/v1/wallets/{id} - Get wallet balance<br>" +
                "POST /api/v1/wallet - Process transaction<br>" +
                "GET /health - Health check";
    }

    @GetMapping("/health")
    public String health() {
        return "{\"status\": \"UP\", \"database\": \"PostgreSQL\"}";
    }
}