package com.github.eddumelendez.autoconfigure.ldap;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ldap.core.ContextSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapAutoConfiguration}.
 *
 * @author Eddú Meléndez
 */
public class LdapAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@Before
	public void setUp() {
		this.context = new AnnotationConfigApplicationContext();
	}

	@After
	public void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void testDefaultUrl() {
		load();
		assertThat(this.context.getBeanNamesForType(ContextSource.class).length)
				.isEqualTo(1);
		assertThat(ReflectionTestUtils.getField(this.context.getBean(ContextSource.class),
				"urls")).isEqualTo(new String[]{"ldap://localhost:389"});
	}

	@Test
	public void testContextSourceSetOneUrl() {
		load("ldap.urls:ldap://localhost:123");
		assertThat(this.context.getBeanNamesForType(ContextSource.class).length)
				.isEqualTo(1);
		assertThat(ReflectionTestUtils.getField(this.context.getBean(ContextSource.class),
				"urls")).isEqualTo(new String[]{"ldap://localhost:123"});
	}

	@Test
	public void testContextSourceSetTwoUrls() {
		load("ldap.urls:ldap://localhost:123,ldap://mycompany:123");
		assertThat(this.context.getBeanNamesForType(ContextSource.class).length)
				.isEqualTo(1);
		Assertions.assertThat(this.context.getBean(LdapProperties.class).getUrls().length)
				.isEqualTo(2);
		assertThat(ReflectionTestUtils.getField(this.context.getBean(ContextSource.class),
				"urls"))
				.isEqualTo(new String[]{"ldap://localhost:123", "ldap://mycompany:123"});
	}

	@Test
	public void testContextSourceWithMoreProperties() {
		load("ldap.urls:ldap://localhost:123",
				"ldap.username:root",
				"ldap.password:root",
				"ldap.base:cn=SpringDevelopers",
				"ldap.baseEnvironment.java.naming.security.authentication:DIGEST-MD5");
		assertThat(this.context.getBeanNamesForType(ContextSource.class).length)
				.isEqualTo(1);
		assertThat(this.context.getBean(LdapProperties.class).getBaseEnvironment())
				.containsEntry("java.naming.security.authentication", "DIGEST-MD5");
	}

	private void load(String... properties) {
		EnvironmentTestUtils.addEnvironment(this.context, properties);
		this.context
				.register(LdapAutoConfiguration.class,
						PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
	}

}
