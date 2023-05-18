package com.example.Wallet.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.Wallet.entity.TransModel;
import com.example.Wallet.repository.TransRepository;
import com.sun.el.stream.Optional;

@Service//this annotation marks the class as a service component in Spring, allowing it to be automatically detected and instantiated as a bean.
public class TransService {
	@Autowired//The @Autowired annotation injects an instance of the TransRepository interface into the transRepository field, allowing access to its methods.
    private TransRepository transRepository;

    //1:saving transactions in the database
    public TransModel saveSingle(TransModel transModel) {
        return transRepository.save(transModel);
    }

   //2: Display all
    public List<TransModel> displayall() {
        return transRepository.findAll();
    }

    //3:Dispaly Single
    public TransModel displayTransaction(int transactionid) {
        //why use Optional?: Expressing intent: The use of Optional<TransModel> in the return type of the
        // method makes it clear to the caller that the result may or may not be present.
        // It serves as a reminder that the caller should handle both cases appropriately.
        java.util.Optional<TransModel> optionalUser = transRepository.findById(transactionid);
        return optionalUser.orElse(null);
    }

    //returning object of type transmodel if the given transaction id is present
    public List<TransModel> findByTransactionid(int transactionid) {
        return transRepository.findByTransactionid(transactionid);
    }

    //returning object of type transmodel if the given payerphone is present
    public List<TransModel> findbyPayerPhone(Integer payerphone) {
        return transRepository.findByPayerphone(payerphone);
    }

    //returning object of type transmodel if the given payeephone is present
    public List<TransModel> findbyPayeePhone(Integer payeephone) {
        return transRepository.findByPayeephone(payeephone);
    }

    public List<TransModel> getAllTransactions(Integer pageNo, Integer pageSize) {
        //Create a PageRequest object:
        //
        //    PageRequest is a class provided by Spring Data that represents a pagination request.
        //    PageRequest.of(pageNo, pageSize) creates a new PageRequest object with the specified page number and page size.
        PageRequest paging =  PageRequest.of(pageNo, pageSize);
        //Retrieve paginated data using transRepository.findAll(paging):
        //
        //    The transRepository.findAll(paging) method is called to retrieve a page of TransModel objects based on the provided pagination information.
        //    The result is assigned to a Page<TransModel> variable named pagedResult.

        //The findAll method is not part of the PagingAndSortingRepository interface.
        //
        //To achieve pagination in the getAllTransactions method, you can make use of the findAll(Pageable pageable) method provided by the JpaRepository interface.

        //This method internally handles the pagination and returns a Page object containing the requested page of TransModel objects.
        Page<TransModel> pagedResult = transRepository.findAll(paging);

        //The getContent() method of the Page object
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<TransModel>();
        }
    }
    
    
   
    
    }
