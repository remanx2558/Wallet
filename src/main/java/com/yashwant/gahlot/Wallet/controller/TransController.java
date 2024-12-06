package com.yashwant.gahlot.Wallet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yashwant.gahlot.Wallet.entity.TransModel;
import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.service.TransService;
import com.yashwant.gahlot.Wallet.service.WalletService;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransController {

	@Autowired
	TransService transService;

	@Autowired
	WalletService walletService;

	    @GetMapping(value = "/all")
	    public ResponseEntity<List<TransModel>> displayAll(
				@RequestParam(value = "page", defaultValue = "0") int pageNo,
				@RequestParam(value = "size", defaultValue = "10") int pageSize) {

			List<TransModel> transactions = transService.getPaginatedTransactions(pageNo, pageSize);
			return new ResponseEntity<>(transactions, HttpStatus.OK);
	    }

	    
//	    //2. Display specific
//	    @GetMapping(value = "/transaction/{transactionid}")
//	    TransModel displayTransaction2(@PathVariable("transactionid") Integer transactionid){
//	    	return transService.displayTransaction(transactionid);
//	    }

	  
	    //3:  transaction status
		@GetMapping("/{transactionId}/status")
		public ResponseEntity<String> getTransactionStatus(@PathVariable("transactionId") Integer transactionId) {
			log.info("Checking status for transaction ID: {}", transactionId);
			Optional<TransModel> transactionOpt = transService.getTransactionById(transactionId);
			if (transactionOpt.isPresent()) {
				return new ResponseEntity<>("Transaction Status: Successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Transaction Status: Failed", HttpStatus.NOT_FOUND);
			}
		}
	    
	    //4: transfer Money :Post
	    @PostMapping
	    public TransModel addtransaction(@RequestBody TransModel transModel) throws ResourceNotFoundException {

	        List<Wallet> payerWallet = walletService.findbyPhone(transModel.getPayerphone());
	        List<Wallet> payeeWallet = walletService.findbyPhone(transModel.getPayeephone());
	        
	        if(payeeWallet.isEmpty()) {
	        	throw new ResourceNotFoundException("Payer phone not exist");
	        	
	        }
	        else if(payerWallet.isEmpty()) {
	        	throw new ResourceNotFoundException("Payee phone not exist");
	        }
	        else if(payerWallet.get(0).getBalance() < transModel.getAmount()) {
	        	throw new ResourceNotFoundException("Insufficient balance");
	        }
	       
	                    transService.saveTransaction(transModel);
//	                    payee_phone.get(0).changeBalance(+transModel.getAmount());
//	                    payer_phone.get(0).changeBalance(-transModel.getAmount());
	                    
	                    walletService.updateUserWallet(payerWallet.get(0), -(transModel.getAmount()));
	                    walletService.updateUserWallet(payeeWallet.get(0), +transModel.getAmount());
	                    
	                    return transModel;
	                   // return "transaction successful";
	              
	    }
	    
	    //5: Transaction by User
	  //for checking transaction of particular phone number
	    @GetMapping(value = "/transaction/all/{mobile}")
	    public List<TransModel> displayTransactions(@PathVariable("mobile") int phone) throws ResourceNotFoundException {
//	            return transService.displayTransaction(transactionid);
	    	
	    	List<Wallet> user_list = walletService.findbyPhone(phone);
	    	if(user_list.size()>0) {
	    		List<TransModel> payer_phone = transService.getTransactionsByPayerPhone(phone);
		        List<TransModel> payee_phone = transService.getTransactionsByPayeePhone(phone);
		        
		        List<TransModel> newList = new ArrayList<TransModel>();
		        newList.addAll(payee_phone);
		        newList.addAll(payer_phone);
		        return newList;
	    	}
	    	throw new ResourceNotFoundException("This Number do not exist put a valid number");
	    	
	        
	    }

	    
	    
	    
	    
//	    //5:pagenation 1 : sender


	@GetMapping("/user/{phone}/paginated")           //part 3
	    public List<TransModel> getPaginated(@PathVariable("phone") Integer phone,
											 @RequestParam(value = "page", defaultValue = "0") int pageNo,
											 @RequestParam(value = "size", defaultValue = "10") int pageSize) throws ResourceNotFoundException {
	        List<Wallet> user_list = walletService.findbyPhone(phone);
	        
	        if(!user_list.isEmpty()) {
	            int phone_number = user_list.get(0).getPhone();  //get phones from id
	            List<TransModel> receiver_list = transService.getTransactionsByPayerPhone(phone_number);
	            List<TransModel> sender_list = transService.getTransactionsByPayeePhone(phone_number);
	            sender_list.addAll(receiver_list);
				//The getPaginated method retrieves the transactions associated with the provided phone number (as either the payer or the payee).
				// It then performs pagination by calculating the starting and ending indices based on the page number and page size parameters.
				// Finally, it returns the sublist of transactions for the current page.

				//The front variable calculates the starting index of the sublist, and the back variable calculates the ending index of the sublist.
	            int front=Math.min(pageNo*pageSize, sender_list.size());
	            int back=Math.min((pageNo+1)*pageSize, sender_list.size());
	            
	            List<TransModel> return_list = sender_list.subList(front,back);
	            return return_list;
	        }
	        else {
	        	throw new ResourceNotFoundException("This Number do not exist put a valid number");
	        }
	    }


	    
	  
}



