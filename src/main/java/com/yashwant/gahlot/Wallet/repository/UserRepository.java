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

/*
* 1.The @Repository annotation is a specialization of the @Component annotation and is used to indicate that the interface is a repository component, providing data access and persistence operations.
* 2. In JpaRepository<User, Long> The Long type represents the data type of the primary key in the User entity class.
* 3.  By using @Repository, you enable Spring to automatically detect and create an instance of the repository bean during application startup.The repository bean acts as a bridge between your application and the underlying data source, providing a way to interact with the data and perform CRUD operations.
* 4. The @Repository annotation is a stereotype annotation that is itself annotated with @Component.when you annotate an interface with @Repository, it is treated as a bean and canbenefit from the features provided by the Spring container for component scanning,dependency injection, and lifecycle management.



 *
* */