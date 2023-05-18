package com.example.Wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Wallet.entity.Wallet;

//the CrudRepository interface provides generic CRUD operations for working with entities.
// It defines the findAll() method along with other commonly used methods like save(), findById(), delete(), etc.

//The CrudRepository interface (or its subinterfaces) provides a default implementation for the findAll() method.
// This default implementation is based on certain conventions and uses the underlying persistence
// mechanism (such as a relational database) to retrieve all the entities of the specified
// type (in this case, Wallet). The default implementation handles the necessary database queries and mapping of the results to a List of Wallet objects.
public interface WalletRepository extends JpaRepository<Wallet,Integer> {
    //The List<Wallet> findAll() method in the WalletRepository interface works without providing its implementation directly
    // because it is inherited from the CrudRepository interface or one of its subinterfaces, such as JpaRepository.
    // These interfaces are part of Spring Data, which provides default implementations for common CRUD operations.

    //It's worth noting that the JpaRepository<Wallet, Integer> interface acts as a base interface that provides default
    // implementations for various CRUD operations. However, Spring Data JPA allows you to customize and extend
    // these operations based on your specific needs. By declaring the findAll() method in the WalletRepository interface,
    // you can override the default implementation and provide your own implementation that returns a List instead of Iterable.By returning
    // a List instead of Iterable, you can ensure that the method directly returns a List object,
    // which can be more convenient and compatible with other parts of your code that expect a List as the result.
    List<Wallet> findAll();

    //By extending the JpaRepository<Wallet, Integer> interface, you
    // inherit the basic CRUD operations, such as save(), findAll(), findById(),
    // and deleteById(). However, if you need to perform more complex queries that are
    // not covered by the basic operations, you can define additional custom query
    // methods using the findBy... naming convention.


    //The findBy... naming convention is a part of Spring Data JPA's method naming convention,
    // which allows you to derive queries based on method names. The framework analyzes the method name
    // and automatically generates the corresponding query for you. In this case, the findByPhone method
    // will generate a query to retrieve wallets based on the phone parameter.
    public List<Wallet> findByPhone(Integer phone);

    //The @Query annotation in Spring Data JPA allows you to define custom queries using JPQL (Java Persistence Query Language)
    // or native SQL directly within your repository interfaces.

    //By using the @Query annotation, you can write more complex and specific queries that go beyond the basic CRUD operations provided by Spring Data JPA.


    //Parameter Binding: You can use named parameters (prefixed with a colon, e.g., :minBalance) in the query and bind
    // them to method parameters using the @Param annotation. In the example, the minBalance parameter is bound
    // to the :minBalance named parameter in the query.
    @Query("SELECT w FROM Wallet w WHERE w.balance > :minBalance")
    List<Wallet> findWalletsWithMinBalance(@Param("minBalance") BigDecimal minBalance);
}
}