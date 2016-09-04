package com.github.eddumelendez.autoconfigure.data.ldap;

import com.github.eddumelendez.autoconfigure.ldap.LdapAutoConfiguration;
import org.junit.After;
import org.junit.Test;

import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ldap.core.LdapTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapDataAutoConfiguration}
 *
 * @author Eddú Meléndez
 */
public class LdapDataAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@After
	public void close() {
		this.context.close();
	}

	@Test
	public void templateExists() {
		this.context = new AnnotationConfigApplicationContext();
		EnvironmentTestUtils.addEnvironment(this.context,
				"spring.ldap.urls:ldap://localhost:389");
		this.context.register(PropertyPlaceholderAutoConfiguration.class,
				LdapAutoConfiguration.class, LdapDataAutoConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBeanNamesForType(LdapTemplate.class).length)
				.isEqualTo(1);
	}

}
