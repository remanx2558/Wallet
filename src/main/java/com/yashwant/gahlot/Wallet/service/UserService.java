package com.yashwant.gahlot.Wallet.service;

import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;




    //POST
    public void save(User user) {
        userRepository.save(user);
    }

    //DELETE
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    //GET
    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findByEmail(String emailid) {
        return userRepository.findByEmail(emailid);
    }

    public List<User> findbyMobile(long mobilenumber) {
        return userRepository.findByMobile(mobilenumber);
    }

    public List<User> findbyGender(String gender) {
        return userRepository.findByGender(gender);
    }

    public List<User> findbyHaswallet(boolean haswallet) {
        return userRepository.findByHaswallet(haswallet);
    }


}