package com.example.wallet.dto;

import com.example.wallet.model.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletRequest {

    @NotNull(message = "Wallet ID is mandatory")
    private UUID walletId;

    @NotNull(message = "Operation type is mandatory")
    private OperationType operationType; // Теперь используем enum

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    // Конструкторы
    public WalletRequest() {}

    public WalletRequest(UUID walletId, OperationType operationType, BigDecimal amount) {
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }

    // Геттеры и сеттеры
    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WalletRequest{" +
                "walletId=" + walletId +
                ", operationType=" + operationType +
                ", amount=" + amount +
                '}';
    }
}