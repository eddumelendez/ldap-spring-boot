package com.github.eddumelendez.autoconfigure.data.ldap;

import javax.naming.ldap.LdapContext;

import com.github.eddumelendez.autoconfigure.ldap.LdapAutoConfiguration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.repository.LdapRepository;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's LDAP support.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ LdapContext.class, LdapRepository.class })
@AutoConfigureAfter(LdapAutoConfiguration.class)
public class LdapDataAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(LdapOperations.class)
	public LdapTemplate ldapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}

}
