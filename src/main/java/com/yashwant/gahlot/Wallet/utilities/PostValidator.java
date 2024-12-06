package com.yashwant.gahlot.Wallet.utilities;



import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.exception.ResourceNotFoundException;
import com.yashwant.gahlot.Wallet.repository.UserRepository;
import com.yashwant.gahlot.Wallet.service.UserService;
import com.yashwant.gahlot.Wallet.service.WalletService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostValidator {
    private static Logger logger = Logger.getLogger("PostValidator");

    public static boolean isEmailValidated(String emailID, UserRepository userRepository) {
        return userRepository.findByEmail(emailID).isEmpty();
    }

    public static boolean isMobileNumberValidated(long mobileNumber, UserRepository userRepository) {
        if (mobileNumber == 0) return true;
        return userRepository.findByMobile(mobileNumber).isEmpty();
    }

    public static void postResponseMessageUser(User user, UserRepository userRepository) throws ResourceNotFoundException {
        Logger logger = Logger.getLogger(user.getClass().getName());


        if (user.getFirstName() == null) {
            logger.log(Level.INFO, "undesired input");
            throw new ResourceNotFoundException("Firstname cannot be empty");
        } else if (user.getLastName() == null) {
            logger.log(Level.INFO, "undesired input");
            throw new ResourceNotFoundException("Lastname cannot be empty");
        } else if (!isEmailValidated(user.getEmail(), userRepository)) {
            logger.log(Level.INFO, "undesired input");
            throw new ResourceNotFoundException("User with an identical email already exists");

        } else if (!isMobileNumberValidated(user.getMobile(), userRepository)) {
            logger.log(Level.INFO, "undesired input");
            throw new ResourceNotFoundException("User with an identical mobile number already exists");
        }
        // else return "";//returning no error
    }

    public static void walletPostValidate(Wallet walletBody, UserService userService, WalletService walletService) throws ResourceNotFoundException {
        //information cannot be null byDefault as using table such a way
        // if mobile number we get from request body is 0
        if(walletBody.getPhone()==null){
            throw new ResourceNotFoundException("mobile number field cannot be empty");
        }


        // getting a list of user with the specified mobile number
        List<User> listUsers = userService.findbyMobile(walletBody.getPhone());
        if (listUsers.isEmpty()) {
            //throw no such user exist
            throw new ResourceNotFoundException("No user with this mobile exist");

        }
        List<Wallet> listWallet = walletService.findByPhone(walletBody.getPhone());
        if (!listWallet.isEmpty()) {
            //throw Wallet already exist
            throw new ResourceNotFoundException("Wallet already exist with this mobile");

        }

        return;
    }


}
