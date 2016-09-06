package com.github.eddumelendez.sample;

import org.springframework.ldap.repository.LdapRepository;
import org.springframework.ldap.repository.Query;

/**
 * @author Eddú Meléndez
 */
public interface PersonRepository extends LdapRepository<Person> {

	@Query("(uid={0})")
	Person findByUid(String uid);

}
