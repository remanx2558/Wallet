
    package com.yashwant.gahlot.Wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yashwant.gahlot.Wallet.entity.TransModel;
import com.yashwant.gahlot.Wallet.repository.TransRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for managing transactions in the Wallet application.
 *
 * <p>This class provides methods to perform CRUD operations and
 * retrieve transactions based on various criteria.</p>
 *
 * <p>Utilizes constructor-based dependency injection for better testability
 * and immutability.</p>
 *
 * <p>Pagination support is provided for retrieving transactions in a paginated manner.</p>
 */
@Service//this annotation marks the class as a service component in Spring, allowing it to be automatically detected and instantiated as a bean.
public class TransService {

    @Autowired
//The @Autowired annotation injects an instance of the TransRepository interface into the transRepository field, allowing access to its methods.
    private TransRepository transRepository;

    /**
     * Saves a single transaction to the database.
     *
     * @param transModel The transaction to save.
     * @return The saved {@link TransModel}.
     */
    public TransModel saveTransaction(TransModel transModel) {
        return transRepository.save(transModel);
    }

    /**
     * Retrieves all transactions from the database.
     *
     * @return A list of all {@link TransModel} entities.
     */
    public List<TransModel> getAllTransactions() {
        return transRepository.findAll();
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionId The ID of the transaction.
     * @return An {@link Optional} containing the {@link TransModel} if found, or empty otherwise.

     *  why use Optional?: Expressing intent: The use of Optional<TransModel> in the return type of the
     *   method makes it clear to the caller that the result may or may not be present.
     *   It serves as a reminder that the caller should handle both cases appropriately.
     */
    public Optional<TransModel> getTransactionById(Integer transactionId) {
        return transRepository.findById(transactionId);
    }

    /**
     * Retrieves transactions by transaction ID.
     *
     * <p>Note: Since transaction ID is the primary key, this method might be redundant
     * if {@link #getTransactionById(Integer)} suffices. Consider removing if not needed.</p>
     *
     * @param transactionId The transaction ID to filter by.
     * @return A list of {@link TransModel} entities matching the specified transaction ID.
     */
    public List<TransModel> getTransactionsByTransactionId(Integer transactionId) {
        return transRepository.findByTransactionid(transactionId);
    }

    /**
     * Retrieves transactions by the payer's phone number.
     *
     * @param payerPhone The payer's phone number.
     * @return A list of {@link TransModel} entities with the specified payer phone number.
     */
    public List<TransModel> getTransactionsByPayerPhone(Integer payerPhone) {
        return transRepository.findByPayerphone(payerPhone);
    }

    /**
     * Retrieves transactions by the payee's phone number.
     *
     * @param payeePhone The payee's phone number.
     * @return A list of {@link TransModel} entities with the specified payee phone number.
     */
    public List<TransModel> getTransactionsByPayeePhone(Integer payeePhone) {
        return transRepository.findByPayeephone(payeePhone);
    }

    /**
     * Retrieves a paginated list of transactions.
     *
     * @param pageNo   The page number (0-based).
     * @param pageSize The size of the page.
     * @return A list of {@link TransModel} entities for the specified page.
     */
    public List<TransModel> getPaginatedTransactions(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        //findAll(Pageable pageable) method provided by JpaRepository interface: This method internally handles the pagination and returns a Page object containing the requested page of TransModel objects.
        Page<TransModel> pagedResult = transRepository.findAll(pageRequest);
        return pagedResult.hasContent() ? pagedResult.getContent() : List.of();
    }


}
