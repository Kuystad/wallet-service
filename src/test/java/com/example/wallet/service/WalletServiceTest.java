// src/test/java/com/example/wallet/service/WalletServiceTest.java
package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private UUID walletId;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        walletId = UUID.randomUUID();
        wallet = new Wallet(walletId, new BigDecimal("1000.00"));
    }

    @Test
    void getBalance_shouldReturnBalance_whenWalletExists() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        BigDecimal balance = walletService.getBalance(walletId);

        assertEquals(new BigDecimal("1000.00"), balance);
        verify(walletRepository).findById(walletId);
    }

    @Test
    void getBalance_shouldThrowException_whenWalletNotFound() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            walletService.getBalance(walletId);
        });

        verify(walletRepository).findById(walletId);
    }

    @Test
    void processTransaction_shouldDeposit_whenOperationIsDeposit() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        walletService.processTransaction(walletId, "DEPOSIT", new BigDecimal("500.00"));

        verify(walletRepository).findById(walletId);
        verify(walletRepository).save(wallet);
    }

    @Test
    void processTransaction_shouldWithdraw_whenOperationIsWithdraw() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        walletService.processTransaction(walletId, "WITHDRAW", new BigDecimal("300.00"));

        verify(walletRepository).findById(walletId);
        verify(walletRepository).save(wallet);
    }

    @Test
    void processTransaction_shouldCreateWallet_whenWalletDoesNotExist() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        walletService.processTransaction(walletId, "DEPOSIT", new BigDecimal("1000.00"));

        verify(walletRepository).findById(walletId);
        verify(walletRepository, times(2)).save(any(Wallet.class));
    }

    @Test
    void processTransaction_shouldThrowException_whenWithdrawAmountExceedsBalance() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(IllegalArgumentException.class, () -> {
            walletService.processTransaction(walletId, "WITHDRAW", new BigDecimal("1500.00"));
        });

        verify(walletRepository).findById(walletId);
        verify(walletRepository, never()).save(any());
    }

    @Test
    void processTransaction_shouldThrowException_whenInvalidOperationType() {
        assertThrows(IllegalArgumentException.class, () -> {
            walletService.processTransaction(walletId, "INVALID", new BigDecimal("100.00"));
        });

        verify(walletRepository, never()).findById(any());
        verify(walletRepository, never()).save(any());
    }
}