package com.github.eddumelendez.autoconfigure.data.ldap;

import javax.naming.ldap.LdapContext;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ldap.repository.LdapRepository;
import org.springframework.ldap.repository.support.LdapRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Couchbase
 * Repositories.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ LdapContext.class, LdapRepository.class })
@ConditionalOnProperty(prefix = "ldap.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean(LdapRepositoryFactoryBean.class)
@Import(LdapRepositoriesRegistrar.class)
public class LdapRepositoriesAutoConfiguration {

}
