package org.mine.kb.spring.data.jpa.user.custom;

import java.util.List;

import org.mine.kb.spring.data.jpa.user.User;

/**
 * Interface for repository functionality that ought to be implemented manually.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
interface UserRepositoryCustom {

	/**
	 * Custom repository operation.
	 *
	 * @return
	 */
	List<User> myCustomBatchOperation();
}