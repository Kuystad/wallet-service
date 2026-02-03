package com.example.wallet.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID walletId, BigDecimal currentBalance, BigDecimal requestedAmount) {
        super(String.format("Insufficient funds in wallet %s. Current balance: %.2f, requested: %.2f",
                walletId, currentBalance, requestedAmount));
    }
}