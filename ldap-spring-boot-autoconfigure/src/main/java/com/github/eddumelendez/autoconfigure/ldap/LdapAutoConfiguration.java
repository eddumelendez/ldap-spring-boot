package com.github.eddumelendez.autoconfigure.ldap;

import java.util.Collections;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for LDAP.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(ContextSource.class)
@EnableConfigurationProperties(LdapProperties.class)
public class LdapAutoConfiguration {

	private LdapProperties properties;

	private Environment environment;

	public LdapAutoConfiguration(LdapProperties properties,
		Environment environment) {
		this.properties = properties;
		this.environment = environment;
	}

	@Bean
	@ConditionalOnMissingBean
	public ContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUserDn(this.properties.getUsername());
		contextSource.setPassword(this.properties.getPassword());
		contextSource.setBase(this.properties.getBase());
		contextSource.setUrls(this.properties.determineUrls(this.environment));
		contextSource.setBaseEnvironmentProperties(Collections
				.<String, Object>unmodifiableMap(this.properties.getBaseEnvironment()));
		return contextSource;
	}

}
