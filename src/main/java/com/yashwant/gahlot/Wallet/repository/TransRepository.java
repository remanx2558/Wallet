
package com.yashwant.gahlot.Wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yashwant.gahlot.Wallet.entity.TransModel;

/**
 * Repository interface for managing {@link TransModel} entities.
 *
 * <p>This interface extends both {@link JpaRepository} and {@link PagingAndSortingRepository}:
 * <ul>
 *   <li>{@code JpaRepository<TransModel, Integer>} provides CRUD operations, pagination, sorting,
 *       and JPA-specific functionalities.</li>
 *   <li>{@code PagingAndSortingRepository<TransModel, Integer>} also provides methods for pagination
 *       and sorting, although it's generally redundant since {@code JpaRepository} already inherits
 *       from it. Here, it's explicitly included for illustration.</li>
 * </ul>
 *
 * <p>By extending these Spring Data JPA interfaces, you get a variety of methods without writing
 * their implementations—Spring provides them at runtime. You can also define additional query
 * methods by following the naming conventions of Spring Data JPA, or by using annotations like
 * {@code @Query} for more complex queries.</p>
 *
 *
 * <p></>It is used for performing CRUD operations and pagination on the TransModel entity.
 * </p>
 * <p>findAll(Pageable pageable): Retrieves all entities sorted by the given Pageable object.
 * findAll(Sort sort): Retrieves all entities sorted by the given Sort object.
 * findAllById(Iterable<ID> ids, Pageable pageable): Retrieves a page of entities with the specified IDs and Pageable object.
 * findAllById(Iterable<ID> ids, Sort sort): Retrieves entities with the specified IDs and sorts them according to the given Sort object.</p>
 */
public interface TransRepository
        extends JpaRepository<TransModel, Integer> {

    /**
     * Retrieves a list of {@link TransModel} entities by their transaction ID.
     *
     * <p>Spring Data JPA will derive the appropriate query from the method name:
     * {@code findByTransactionid} translates to:
     * {@code SELECT t FROM TransModel t WHERE t.transactionid = :transactionid}.</p>
     *
     * @param transactionid The transaction ID to filter by.
     * @return A list of {@link TransModel} entities matching the specified transaction ID.
     */
    List<TransModel> findByTransactionid(Integer transactionid);

    /**
     * Retrieves a list of {@link TransModel} entities filtered by the payee's phone number.
     *
     * <p>This query is also derived from the method name. By naming the method
     * {@code findByPayeephone}, Spring Data JPA creates the necessary query:
     * {@code SELECT t FROM TransModel t WHERE t.payeephone = :payeephone}.</p>
     *
     * @param payeephone The payee's phone number to filter by.
     * @return A list of {@link TransModel} entities with the specified payee phone number.
     */
    List<TransModel> findByPayeephone(Integer payeephone);

    /**
     * Retrieves a list of {@link TransModel} entities filtered by the payer's phone number.
     *
     * <p>Like the previous methods, Spring Data JPA derives a query from the method name:
     * {@code SELECT t FROM TransModel t WHERE t.payerphone = :payerphone}.</p>
     *
     * @param payerphone The payer's phone number to filter by.
     * @return A list of {@link TransModel} entities with the specified payer phone number.
     */
    List<TransModel> findByPayerphone(Integer payerphone);

}
