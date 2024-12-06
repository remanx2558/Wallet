package com.yashwant.gahlot.Wallet.utilities;


import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.repository.UserRepository;
import com.yashwant.gahlot.Wallet.service.WalletService;

import java.util.List;

public class PutValidator {
    //check email and mobile number for User
    public static void canBeUpdatedUser(User newUser, User existingUser, UserRepository userRepository) throws ResourceNotFoundException {

        /// m e: nn tn nt tt
        if (newUser.getMobile() != null && !userRepository.findByMobile(newUser.getMobile()).isEmpty()) {
            throw new ResourceNotFoundException("cannot change to this mobile number");
        }
        if (newUser.getEmail() != null && !userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            throw new ResourceNotFoundException("cannot change to this email id ");
        }
        if (!userRepository.findById(newUser.getUid()).isEmpty()) {
            throw new ResourceNotFoundException("cannot change the UID its permanent");
        }
    }

    public static void canBalanceBeAddedWallet(WalletService walletService, Long mob,
                                               Wallet balanceWallet) throws ResourceNotFoundException {
        // find list of wallet by userID
        List<Wallet> wallets = walletService.findByPhone(mob);

        // if wallet list is empty, user doesn't exist
        if (wallets.isEmpty()) {
            throw new ResourceNotFoundException("Wallet does not exist with this mobile number");
        }

        // getting wallet object from list and then balance
        Wallet wallet = wallets.get(0);
        if (wallet.isHasWallet() == false) {
            throw new ResourceNotFoundException("Wallet is de-activated");
        }
        long balance = balanceWallet.getBalance();

        // adding balance = 0 is insignificant, less than 0 is not possible
//        if (balance < 1) {
//            wallets.remove(0);
//            throw new ResourceNotFoundException("Cannot add balance <= 0");
//
////            Constants.setWalletPutMessage("Cannot add balance <= 0");
////            return wallets;
//        }

    }
}
