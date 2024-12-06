
    package com.yashwant.gahlot.Wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yashwant.gahlot.Wallet.entity.Transaction;
import com.yashwant.gahlot.Wallet.repository.TransactionRepository;

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
public class TransactionService {

    @Autowired
   private TransactionRepository transactionRepository;


    //GET
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


        public Optional<Transaction> getTransactionById(Integer transactionId) {
            return transactionRepository.findById(transactionId);
        }


        public List<Transaction> findById(Integer transactionId) {
            return transactionRepository.findByTransactionid(transactionId);
        }


        public List<Transaction> findBySender(Long payerPhone) {
            return transactionRepository.findByPayerphone(payerPhone);
        }


        public List<Transaction> findByReceiver(Long payeePhone) {
            return transactionRepository.findByPayeephone(payeePhone);
        }


        public List<Transaction> getPaginatedTransactions(int pageNo, int pageSize) {
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
            //findAll(Pageable pageable) method provided by JpaRepository interface: This method internally handles the pagination and returns a Page object containing the requested page of TransModel objects.
            Page<Transaction> pagedResult = transactionRepository.findAll(pageRequest);
            return pagedResult.hasContent() ? pagedResult.getContent() : List.of();
        }
    //POST
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public void save(Transaction transaction) { transactionRepository.save(transaction); }

        //PUT


}

/*
*
* The @Autowired annotation injects an instance of the TransRepository interface into the transRepository field, allowing access to its methods.

*
*
 * why use Optional?: Expressing intent: The use of Optional<TransModel> in the return type of the
*method makes it clear to the caller that the result may or may not be present.
* It serves as a reminder that the caller should handle both cases appropriately.
*
* */
