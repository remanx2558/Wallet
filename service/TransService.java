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

@Service
public class TransService {
	@Autowired
    private TransRepository transRepository;

    //saving transactions in the database
    public TransModel addtransaction(TransModel transModel) {
        return transRepository.save(transModel);
    }

    //method for displaying all the transaction in the database
//    public List<TransModel> displayall() {
//        return transRepository.findAll();
//    }

    //method for displaying particular transaction
    public TransModel displayTransaction(int transactionid) {
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
        PageRequest paging =  PageRequest.of(pageNo, pageSize);

        Page<TransModel> pagedResult = transRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<TransModel>();
        }
    }
    }