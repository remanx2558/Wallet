package com.yashwant.gahlot.Wallet.controller;

import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.repository.WalletRepository;
import com.yashwant.gahlot.Wallet.service.UserService;
import com.yashwant.gahlot.Wallet.service.WalletService;
import com.yashwant.gahlot.Wallet.utilities.PostValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@RestController
public class UserWalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserService userService;
    private Logger logger = Logger.getLogger(this.getClass().getName());


    // using User object as a request body where it will have only one field, i.e, mobile number and type
    @PostMapping("/merchant/register")
    public Wallet createMerchanWallet(@Valid @RequestBody Wallet walletBody) throws ResourceNotFoundException {// we will be requiring request body to get data to fill
        PostValidator.walletPostValidate(walletBody, userService, walletService);
        walletBody.setCustomer(false);
        walletBody.setHasWallet(true);
        walletService.addWallet(walletBody);
        ///////////////////////making user wallet coexisting
        User curr = userService.findbyMobile(walletBody.getPhone()).get(0);
        curr.setHaswallet(true);
        userService.save(curr);
        /////////////////////
        return walletBody;
    }

    @PostMapping("/customer/register")
    public Wallet createCustomereWallet(@Valid @RequestBody Wallet walletBody) throws ResourceNotFoundException {// we will be requiring request body to get data to fill
        PostValidator.walletPostValidate(walletBody, userService, walletService);
        walletBody.setCustomer(true);
        walletBody.setHasWallet(true);
        walletService.addWallet(walletBody);
        ///////////////////////making user wallet coexisting
        User curr = userService.findbyMobile(walletBody.getPhone()).get(0);
        curr.setHaswallet(true);
        userService.save(curr);
        /////////////////////
        return walletBody;
    }

    //PUT
    //activate or deactivate wallet
    @PutMapping("/wallet/activate/{act}")
    public Wallet WalletActivation(@Valid @RequestBody Wallet walletBody, @PathVariable(value = "act") int activation) {
        // PostValidator.walletPostValidate(walletBody, userService,walletService);
        Wallet existingUser = this.walletRepository.findByPhone(walletBody.getPhone()).get(0);
        User curr = userService.findbyMobile(walletBody.getPhone()).get(0);

        if (activation == 0) {
            walletBody.setHasWallet(false);
            curr.setHaswallet(false);
        } else if (activation == 1) {
            walletBody.setHasWallet(true);
            curr.setHaswallet(true);
        }
        userService.save(curr);

        logger.log(Level.INFO, walletBody.toString());
        ///////////////////////making user wallet coexisting
        /////////////////////
        return this.walletRepository.save(existingUser);//this will directly save into database
    }

}
