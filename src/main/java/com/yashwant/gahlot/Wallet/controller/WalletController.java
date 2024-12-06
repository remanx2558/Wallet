package com.yashwant.gahlot.Wallet.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.yashwant.gahlot.Wallet.utilities.UtilityMethods;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.repository.WalletRepository;
import com.yashwant.gahlot.Wallet.service.WalletService;
import org.springframework.web.bind.annotation.*;

import static com.yashwant.gahlot.Wallet.utilities.PutValidator.canBalanceBeAddedWallet;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/wallet")
@Slf4j
@RestController
public class WalletController {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	    private WalletService walletService;
	 
	  @Autowired
	    private WalletRepository walletRepository;


	  //POST
	    //will create wallet for a user
	    @PostMapping(value = "/add")
	    public String addWallet(@RequestBody Wallet walletModel) {
	        List<Wallet> phone_number = walletService.findByPhone(walletModel.getPhone()); // check for same phone number

	        if (!phone_number.isEmpty()) {
	            return "Wallet already exists";
	        } else walletService.addWallet(walletModel);
	        return "Wallet created";

	    }

		//PUT


	@PutMapping("/add/money/{amount}")
	public Wallet ChangeWalletBalance(@Valid @RequestBody Wallet walletBody, @PathVariable(value = "amount") long bal) throws ResourceNotFoundException {

		long num = walletBody.getPhone();
		canBalanceBeAddedWallet(walletService,num,walletBody);

		List<Wallet> existingUsers = this.walletRepository.findByPhone(num);
		if (existingUsers == null) {
			throw new ResourceNotFoundException("user not Fondd with number : " + num);
		}
		Wallet existingUser = existingUsers.get(0);
		long pre_bal = existingUser.getBalance();
		existingUser.setBalance((int) (bal + pre_bal));
		logger.log(Level.INFO, walletBody.toString());
		return this.walletRepository.save(existingUser);//this will directly save into database
	}

		//GET

	    @GetMapping(value = "/all")
	    public List<Wallet> displayAll() {
			logger.log(Level.INFO, "list of all wallets returned at " + UtilityMethods.getCurrentTime());
			return walletService.getWallets();
	    }
	    //2: display specific wallet user

	    @GetMapping(value = "/phone/{phone}")
	    public List<Wallet> displaySpecific(@PathVariable(value = "phone") long phone) throws ResourceNotFoundException {
	    	List<Wallet> ans=this.walletService.findByPhone(phone);
	    	if(!ans.isEmpty() && ans.size()>0) {
	    		return ans;
	    	}
	    	throw new ResourceNotFoundException("user not Fondd with phone : "+phone);
	    }

	@GetMapping(value = "/walletId", params = "walletId")
	public ResponseEntity<?> get(@RequestParam("walletId") Long id) {
		try {
			Wallet wallet = walletService.findById(id);
			ResponseEntity<Wallet> r = new ResponseEntity<>(wallet, OK);
			logger.log(Level.INFO, wallet.toString());
			return r;
		} catch (NoSuchElementException e) {
			String msg="Cannot read nonexistent wallet";
			logger.log(Level.INFO, msg);
			return new ResponseEntity<>(msg, NOT_FOUND);
		}
	}

		//DELETE
	    @DeleteMapping(value = "/delete/{phone}")
		public ResponseEntity<Wallet> deleteUser(@PathVariable("phone") long phone) throws ResourceNotFoundException {
			
	    	List<Wallet> ans=this.walletService.findByPhone(phone);
	    	if(ans.size()>0) {
	    		this.walletService.delete(ans.get(0));
				return ResponseEntity.ok().build();

	    	}
	    	throw new ResourceNotFoundException("user not Fondd with phone : "+phone);
	   }

	    @DeleteMapping(value = "/wallet", params = "walletId")
    public ResponseEntity<String> deleteWalletByID(@RequestParam("walletId") Long id) {
        String responseBody;
        try {
            Wallet existingWallet = walletService.findById(id);
            responseBody = new String("Deleted wallet successfully with id = "+id);
            logger.log(Level.INFO, existingWallet.toString());
            walletService.delete(id);
            return new ResponseEntity<>(responseBody, OK);
        }
        catch (NoSuchElementException e) {
            responseBody = new String("Cannot delete nonexistent wallet");
            logger.log(Level.INFO, responseBody.toString());
            return new ResponseEntity<>(responseBody, NOT_FOUND);
        }
    }
	    

	    
}
