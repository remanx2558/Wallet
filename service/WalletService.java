package com.example.Wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Wallet.entity.Wallet;
import com.example.Wallet.repository.WalletRepository;

@Service
public class WalletService {
	  @Autowired
	    private WalletRepository walletRepository;

	    //method for adding wallet in the database
	    public Wallet addWallet(Wallet walletModel) {
	        return walletRepository.save(walletModel);
	    }

	    //method for getting all the wallets from the database
	    public List<Wallet> getWallets() {
	        return walletRepository.findAll();
	    }

	    //it will return object of walletmodel type if phone is present
	    public List<Wallet> findbyPhone(Integer phone) {return walletRepository.findByPhone(phone);}
}
