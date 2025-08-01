package org.mine.kb.spring.data.jpa.user.custom;

import java.util.List;

import org.mine.kb.spring.data.jpa.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Implementation fo the custom repository functionality declared in
 * {@link UserRepositoryCustom} based on JPA. To use
 * this implementation in combination with Spring Data JPA you can either
 * register it programmatically:
 *
 * <pre>
 * EntityManager em = ... // Obtain EntityManager
 *
 * UserRepositoryCustom custom = new UserRepositoryImpl();
 * custom.setEntityManager(em);
 *
 * RepositoryFactorySupport factory = new JpaRepositoryFactory(em);
 * UserRepository repository = factory.getRepository(UserRepository.class, custom);
 * </pre>
 *
 * Using the Spring namespace the implementation will just get picked up due to
 * the classpath scanning for
 * implementations with the {@code Impl} postfix.
 *
 * <pre>
 * &lt;jpa:repositories base-package=&quot;com.acme.repository&quot; /&gt;
 * </pre>
 *
 * If you need to manually configure the custom instance see
 * {@link UserRepositoryImplJdbc} for an example.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * Configure the entity manager to be used.
     *
     * @param em the {@link EntityManager} to set.
     */
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /*
     * (non-Javadoc)
     * 
     * @see example.springdata.jpa.UserRepositoryCustom#myCustomBatchOperation()
     */
    public List<User> myCustomBatchOperation() {

        var criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
}