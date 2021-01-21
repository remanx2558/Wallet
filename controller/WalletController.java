package com.example.Wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.entity.Wallet;
import com.example.Wallet.repository.WalletRepository;
import com.example.Wallet.service.WalletService;

@RestController
public class WalletController {
	 @Autowired
	    private WalletService walletService;
	 
	  @Autowired
	    private WalletRepository walletRepository;

	    //will create wallet for a user
	    @PostMapping(value = "/wallet")
	    public String addWallet(@RequestBody Wallet walletModel) {
	        List<Wallet> phone_number = walletService.findbyPhone(walletModel.getPhone()); // check for same phone number

	        if (!phone_number.isEmpty()) {
	            return "Wallet already exists";
	        } else walletService.addWallet(walletModel);
	        return "Wallet created";

	    }

	    //for displaying all the wallets present in the database
	    @GetMapping(value = "/wallet/all")
	    public List<Wallet> displayAll() {
	        return walletService.getWallets();
	    }
}
