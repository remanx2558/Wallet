

package com.yashwant.gahlot.Wallet.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yashwant.gahlot.Wallet.entity.Wallet;

/**
 * Repository interface for managing {@link Wallet} entities.
 *
 * <p>This interface extends {@link JpaRepository}, which provides a full set of CRUD operations,
 * pagination, and sorting capabilities without the need for boilerplate code. By default, you get:
 * <ul>
 *     <li>{@code findAll()} - Retrieves all {@code Wallet} entities.</li>
 *     <li>{@code findById(ID)} - Retrieves an entity by its primary key.</li>
 *     <li>{@code save(ENTITY)} - Saves or updates an entity.</li>
 *     <li>{@code deleteById(ID)} - Deletes an entity by its primary key.</li>
 * </ul>
 *
 * <p>Additional queries can be defined by following Spring Data JPA's naming conventions (like findBy..) or
 * using the {@link Query} annotation for more complex queries.</p>
 *
 * <p>
 *     The findBy... naming convention is a part of Spring Data JPA's method naming convention,
 *     which allows you to derive queries based on method names. The framework analyzes the method name
 *     and automatically generates the corresponding query for you. In this case, the findByPhone method
 *     will generate a query to retrieve wallets based on the phone parameter.
 * </p>
 */


public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    /**
     * Retrieves a list of {@link Wallet} entities filtered by their phone number.
     *
     * <p>Spring Data JPA derives the query from the method name. For example, the method name
     * {@code findByPhone} translates to a query:
     * {@code SELECT w FROM Wallet w WHERE w.phone = :phone}.</p>
     */
    List<Wallet> findByPhone(Integer phone);


    /**
     * <p>The {@link Query} annotation allows defining a custom JPQL query. Here, we use a named parameter
     * {@code :minBalance} bound to the method parameter using the {@link Param} annotation.</p>
     */
    @Query("SELECT w FROM Wallet w WHERE w.balance > :minBalance")
    List<Wallet> findWalletsWithMinBalance(@Param("minBalance") BigDecimal minBalance);

}


/*
* By declaring the findAll() method in the WalletRepository interface,
  you can override the default implementation and provide your own implementation that returns a List instead of Iterable.By returning
  a List instead of Iterable, you can ensure that the method directly returns a List object,
  which can be more convenient and compatible with other parts of your code that expect a List as the result.
* */