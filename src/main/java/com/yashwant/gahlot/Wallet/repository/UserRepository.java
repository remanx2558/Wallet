package com.yashwant.gahlot.Wallet.repository;

import com.yashwant.gahlot.Wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// by extending JpaRepository this interface contains all pre-created methods for logic of HTTP requests
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // these methods are custom finder methods, i.e for custom queries for mysql
    // Here an example query is SELECT * FROM USER WHERE emailid = 'SOME_STRING'
    public List<User> findByEmail(String emailid);

    public List<User> findByMobile(long mobilenumber);

    public List<User> findByGender(String gender);

    public List<User> findByHaswallet(boolean haswallet);


}