package com.yashwant.gahlot.Wallet;

import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.repository.WalletRepository;
import com.yashwant.gahlot.Wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class WalletApplicationTests {

	@Test
	void contextLoads() {
		// This test ensures that the Spring application context loads successfully.
	}

	@Autowired
	private WalletService walletService;

//	@MockBean
//	private WalletRepository walletRepository;

	/**
	 * Test Case 1: Delete a Wallet
	 *
	 * This test verifies that the delete operation is performed exactly once
	 * when a Wallet is deleted via the WalletService.
	 */
//	@Test
//	public void deleteWalletTest() {
//		// Arrange: Create a sample Wallet
//		Wallet wallet = new Wallet(123456, 995);
//
//		// Act: Perform the delete operation
//		walletService.delete(wallet);
//
//		// Assert: Verify that the repository's delete method was called once with the correct Wallet
//		verify(walletRepository, times(1)).delete(wallet);
//	}

	/**
	 * Test Case 2: Retrieve All Wallets
	 *
	 * This test checks whether the WalletService correctly retrieves all Wallets.
	 */
//	@Test
//	public void getAllWalletsTest() {
//		// Arrange: Mock the repository to return a predefined list of Wallets
//		List<Wallet> mockWallets = Stream.of(
//				new Wallet(37612344, 31),
//				new Wallet(95812345, 35)
//		).collect(Collectors.toList());
//
//		when(walletRepository.findAll()).thenReturn(mockWallets);
//
//		// Act: Retrieve all wallets using the service
//		List<Wallet> wallets = walletService.getWallets();
//
//		// Assert: Verify that the retrieved list matches the mocked list size
//		assertEquals(2, wallets.size(), "The size of the retrieved wallets should be 2.");
//	}

	/**
	 * Test Case 3: Find Wallet by Phone Number
	 *
	 * This test verifies that the WalletService can find a Wallet by its phone number.
	 */
//	@Test
//	public void findByPhoneTest() {
//		// Arrange: Define a phone number and mock the repository response
//		int phone = 12345;
//		List<Wallet> mockWallet = Stream.of(new Wallet(12345, 5000)).collect(Collectors.toList());
//
//		when(walletRepository.findByPhone(phone)).thenReturn(mockWallet);
//
//		// Act: Find wallets by phone number using the service
//		List<Wallet> wallets = walletService.findByPhone(phone);
//
//		// Assert: Verify that exactly one Wallet is returned
//		assertEquals(1, wallets.size(), "There should be exactly one wallet with the provided phone number.");
//	}

	/**
	 * Test Case 4: Add a New Wallet
	 *
	 * This test ensures that a new Wallet is correctly saved via the WalletService.
	 */
//	@Test
//	public void addWalletTest(){
//		// Arrange: Create a sample Wallet and mock the repository's save method
//		Wallet user = new Wallet(99377379, 33);
//		when(walletRepository.save(user)).thenReturn(user);
//
//		// Act: Add the wallet using the service
//		Wallet savedWallet = walletService.addWallet(user);
//
//		// Assert: Verify that the saved Wallet matches the input Wallet
//		assertEquals(user, savedWallet, "The saved wallet should match the input wallet.");
//	}
}
