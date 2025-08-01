package org.mine.kb.spring.data.jpa.user.custom;

import java.util.List;

import org.mine.kb.spring.data.jpa.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@link User} instances. Provides basic CRUD
 * operations due to the extension of
 * {@link JpaRepository}. Includes custom implemented functionality by extending
 * {@link UserRepositoryCustom}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

    /**
     * Find the user with the given username. This method will be translated into a
     * query using the
     * {@link jakarta.persistence.NamedQuery} annotation at the {@link User} class.
     *
     * @param username
     * @return
     */
    User findByTheUsersName(String username);

    /**
     * Find all users with the given lastname. This method will be translated into a
     * query by constructing it directly
     * from the method name as there is no other query declared.
     *
     * @param lastname
     * @return
     */
    List<User> findByLastname(String lastname);

    /**
     * Returns all users with the given firstname. This method will be translated
     * into a query using the one declared in
     * the {@link Query} annotation declared one.
     *
     * @param firstname
     * @return
     */
    @Query("select u from User u where u.firstname = :firstname")
    List<User> findByFirstname(String firstname);
}