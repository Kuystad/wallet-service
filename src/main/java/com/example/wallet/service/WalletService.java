// src/main/java/com/example/wallet/service/WalletService.java
package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(Wallet::getBalance)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found: " + walletId));
    }

    @Transactional
    public void processTransaction(UUID walletId, String operationType, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet(walletId, BigDecimal.ZERO);
                    return walletRepository.save(newWallet);
                });

        if ("DEPOSIT".equalsIgnoreCase(operationType)) {
            wallet.deposit(amount);
        } else if ("WITHDRAW".equalsIgnoreCase(operationType)) {
            wallet.withdraw(amount);
        } else {
            throw new IllegalArgumentException("Invalid operation type: " + operationType);
        }

        walletRepository.save(wallet);
    }
}