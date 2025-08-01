package org.mine.kb.spring.data.jpa.user.custom;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mine.kb.spring.data.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the basic usage of {@link UserRepository}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Divya Srivastava
 */

@Transactional
@SpringBootTest
// @ActiveProfiles("jdbc") // Uncomment @ActiveProfiles to enable the JDBC
// Implementation of the custom repository
public class UserRepositoryCustomImplTest {

    @Autowired
    UserRepository repository;

    /**
     * Tests inserting a user and asserts it can be loaded again.
     */
    @Test
    void testInsert() {

        var user = new User();
        user.setUsername("username");

        user = repository.save(user);

        assertThat(repository.findById(user.getId())).hasValue(user);
    }

    @Test
    void saveAndFindByLastNameAndFindByUserName() {

        var user = new User();
        user.setUsername("foobar");
        user.setLastname("lastname");

        user = repository.save(user);

        var users = repository.findByLastname("lastname");

        assertThat(users).contains(user);
        assertThat(user).isEqualTo(repository.findByTheUsersName("foobar"));
    }

    /**
     * Test invocation of custom method.
     */
    @Test
    void testCustomMethod() {

        var user = new User();
        user.setUsername("username");

        user = repository.save(user);

        var users = repository.myCustomBatchOperation();

        assertThat(users).contains(user);
    }
}
