//first version
package com.example.wallet.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Version
    private Long version;

    public Wallet() {}

    public Wallet(UUID id, BigDecimal balance) {
        this.id = id;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
    }

    // Геттеры
    public UUID getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public Long getVersion() { return version; }

    // Сеттеры
    public void setId(UUID id) { this.id = id; }
    public void setBalance(BigDecimal balance) {
        this.balance = balance != null ? balance : BigDecimal.ZERO;
    }

    // Методы для операций
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }
}