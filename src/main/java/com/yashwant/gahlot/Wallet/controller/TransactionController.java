package com.yashwant.gahlot.Wallet.controller;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.yashwant.gahlot.Wallet.entity.Transaction;
import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.service.TransactionService;
import com.yashwant.gahlot.Wallet.service.WalletService;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private WalletService walletService;

	/**
	 * Retrieve all transactions.
	 *
	 * @return List of all transactions.
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Transaction>> displayAll() {
		List<Transaction> transactions = transactionService.getAllTransactions();
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	/**
	 * Retrieve paginated transactions.
	 *
	 * @param pageNo   Page number (0-based).
	 * @param pageSize Number of records per page.
	 * @return Paginated list of transactions.
	 */
	@GetMapping("/allPage")
	public ResponseEntity<List<Transaction>> displayPage(
			@RequestParam(value = "page", defaultValue = "0") int pageNo,
			@RequestParam(value = "size", defaultValue = "10") int pageSize) {

		List<Transaction> transactions = transactionService.getPaginatedTransactions(pageNo, pageSize);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	/**
	 * Get the status of a specific transaction.
	 *
	 * @param transactionId ID of the transaction.
	 * @return Status message.
	 */
	@GetMapping("/{transactionId}/status")
	public ResponseEntity<String> getTransactionStatus(@PathVariable("transactionId") Integer transactionId) {
		log.info("Checking status for transaction ID: {}", transactionId);
		Optional<Transaction> transactionOpt = transactionService.getTransactionById(transactionId);
		if (transactionOpt.isPresent()) {
			return ResponseEntity.ok("Transaction Status: Successful");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction Status: Failed");
		}
	}

	/**
	 * Retrieve a specific transaction by ID.
	 *
	 * @param transactionId ID of the transaction.
	 * @return Transaction details.
	 */
	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> displayTransaction(@PathVariable("transactionId") Integer transactionId) throws ResourceNotFoundException {
		Optional<Transaction> transactionOpt = transactionService.getTransactionById(transactionId);
		if (transactionOpt.isPresent()) {
			return new ResponseEntity<>(transactionOpt.get(), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Transaction not found with ID: " + transactionId);
		}
	}

	/**
	 * Retrieve all unique transactions associated with a mobile number (as sender or receiver).
	 *
	 * @param phone Mobile number.
	 * @return List of unique transactions.
	 */
	@GetMapping("/all/{mobile}")
	public ResponseEntity<List<Transaction>> displayTransactions(@PathVariable("mobile") long phone) throws ResourceNotFoundException {

		List<Wallet> userList = walletService.findByPhone(phone);

		if (!userList.isEmpty()) {
			// Fetch transactions for sender and receiver
			List<Transaction> senderTransactions = transactionService.findBySender(phone);
			List<Transaction> receiverTransactions = transactionService.findByReceiver(phone);

			// Use a Set to ensure unique transactions
			Set<Transaction> uniqueTransactions = new HashSet<>();
			uniqueTransactions.addAll(senderTransactions);
			uniqueTransactions.addAll(receiverTransactions);

			// Convert the Set back to a List and return
			List<Transaction> transactionList = new ArrayList<>(uniqueTransactions);
			return new ResponseEntity<>(transactionList, HttpStatus.OK);
		}

		throw new ResourceNotFoundException("This Number does not exist. Please provide a valid number.");
	}

	/**
	 * Retrieve paginated transactions for a specific mobile number (as sender or receiver).
	 *
	 * @param phone    Mobile number.
	 * @param pageNo   Page number (0-based).
	 * @param pageSize Number of records per page.
	 * @return Paginated list of transactions.
	 */
	@GetMapping("/user/{phone}/paginated")
	public ResponseEntity<List<Transaction>> getPaginated(
			@PathVariable("phone") long phone,
			@RequestParam(value = "page", defaultValue = "0") int pageNo,
			@RequestParam(value = "size", defaultValue = "10") int pageSize) throws ResourceNotFoundException {

		// Validate the existence of the user
		List<Wallet> userList = walletService.findByPhone(phone);
		if (userList.isEmpty()) {
			throw new ResourceNotFoundException("This Number does not exist. Please provide a valid number.");
		}

		// Fetch transactions for both sender and receiver, ensuring uniqueness
		Set<Transaction> transactions = new HashSet<>();
		transactions.addAll(transactionService.findBySender(phone));
		transactions.addAll(transactionService.findByReceiver(phone));

		// Convert to a list and calculate pagination indices
		List<Transaction> transactionList = new ArrayList<>(transactions);
		int fromIndex = pageNo * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, transactionList.size());

		if (fromIndex > transactionList.size()) {
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
		}

		List<Transaction> paginatedList = transactionList.subList(fromIndex, toIndex);
		return new ResponseEntity<>(paginatedList, HttpStatus.OK);
	}

	/**
	 * Transfer money between wallets.
	 *
	 * This operation involves:
	 * 1. Validating the existence of payer and payee.
	 * 2. Checking for sufficient balance.
	 * 3. Saving the transaction.
	 * 4. Updating wallet balances.
	 *
	 * The entire operation is transactional to ensure atomicity.
	 *
	 * @param transaction Transaction details.
	 * @return Saved transaction.
	 */
	@PostMapping
	@Transactional
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) throws ResourceNotFoundException {
		log.info("Initiating transaction: {}", transaction);

		// Fetch payer and payee wallets
		List<Wallet> payerWallets = walletService.findByPhone(transaction.getPayerphone());
		List<Wallet> payeeWallets = walletService.findByPhone(transaction.getPayeephone());

		// Validate payee existence
		if (payeeWallets.isEmpty()) {
			throw new ResourceNotFoundException("Payee phone number does not exist.");
		}

		// Validate payer existence
		if (payerWallets.isEmpty()) {
			throw new ResourceNotFoundException("Payer phone number does not exist.");
		}

		Wallet payerWallet = payerWallets.get(0);
		Wallet payeeWallet = payeeWallets.get(0);

		// Check for sufficient balance
		if (payerWallet.getBalance() < transaction.getAmount()) {
			throw new ResourceNotFoundException("Insufficient balance in payer's wallet.");
		}

		// Save the transaction
		Transaction savedTransaction = transactionService.saveTransaction(transaction);
		log.info("Transaction saved: {}", savedTransaction);

		// Update wallet balances
		walletService.updateWalletAmount(payerWallet, -transaction.getAmount());
		walletService.updateWalletAmount(payeeWallet, transaction.getAmount());
		log.info("Updated balances - Payer: {}, Payee: {}", payerWallet.getBalance(), payeeWallet.getBalance());

		return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
	}
}
