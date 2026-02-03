// src/test/java/com/example/wallet/WalletServiceApplicationTests.java
package com.example.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = WalletServiceApplication.class)
public class WalletServiceApplicationTests {
	@Test
	void contextLoads() {
		// Просто проверяем что тест запускается
		assertTrue(true);
	}
}