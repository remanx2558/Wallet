package com.yashwant.gahlot.Wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.repository.WalletRepository;

@Service
public class WalletService {
	  @Autowired
	  private WalletRepository walletRepository;

	//PUT
	public String updateWalletAmount(Wallet existingWalletuser, int amount)
	{
		int finalAmount=existingWalletuser.getBalance()+amount;
		existingWalletuser.setBalance(finalAmount);                // updating the wallet balance
		walletRepository.save(existingWalletuser);
		return "wallet updated for :"+existingWalletuser.getPhone();
	}
	//POST
	public Wallet addWallet(Wallet walletModel) {
		return walletRepository.save(walletModel);
	}
	//GET
	public List<Wallet> findByHaswallet(boolean haswallet) { return walletRepository.findByHasWallet(haswallet); }
	public List<Wallet> findByIsCustomer(boolean isCustomer) { return walletRepository.findByIsCustomer(isCustomer); }
	public List<Wallet> getWallets() {
		return walletRepository.findAll();
	}
	public List<Wallet> findByPhone(Long phone) {return walletRepository.findByPhone(phone);}
	public Wallet findById(Long id) { return walletRepository.findById(id).get(); }

	//DELETE
	public void delete(Wallet wallet) {this.walletRepository.delete( wallet);}
	public void delete(Long id) { walletRepository.deleteById(id); }
	public void deleteAll() {walletRepository.deleteAll();}
		    
		
		
		
}
