// src/main/java/com/example/wallet/controller/WalletController.java
package com.example.wallet.controller;

import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<?> getBalance(@PathVariable UUID id) {
        try {
            BigDecimal balance = walletService.getBalance(id);
            return ResponseEntity.ok()
                    .body("{\"walletId\": \"" + id + "\", \"balance\": " + balance + "}");
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body("{\"error\": \"Wallet not found: " + id + "\"}");
        }
    }

    @PostMapping("/wallet")
    public ResponseEntity<?> processTransaction(@RequestBody TransactionRequest request) {
        try {
            walletService.processTransaction(
                    request.getWalletId(),
                    request.getOperationType(),
                    request.getAmount()
            );
            return ResponseEntity.ok()
                    .body("{\"status\": \"success\", \"message\": \"Transaction processed\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("{\"error\": \"Internal server error: " + e.getMessage() + "\"}");
        }
    }

    // DTO для запроса
    public static class TransactionRequest {
        private UUID walletId;
        private String operationType; // "DEPOSIT" или "WITHDRAW"
        private BigDecimal amount;

        // геттеры и сеттеры
        public UUID getWalletId() { return walletId; }
        public void setWalletId(UUID walletId) { this.walletId = walletId; }

        public String getOperationType() { return operationType; }
        public void setOperationType(String operationType) { this.operationType = operationType; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }
}