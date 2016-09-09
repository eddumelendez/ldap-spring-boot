/*
 * Copyright 2016 Eddu Melendez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.eddumelendez.autoconfigure.data.ldap;

import com.github.eddumelendez.autoconfigure.ldap.LdapAutoConfiguration;
import org.junit.After;
import org.junit.Test;

import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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

	@Test
	public void resolvePrimaryContextSourceBean() {
		this.context = new AnnotationConfigApplicationContext();
		EnvironmentTestUtils.addEnvironment(this.context,
				"spring.ldap.urls:ldap://localhost:389");
		this.context.register(PropertyPlaceholderAutoConfiguration.class,
				LdapCustomConfiguration.class, LdapAutoConfiguration.class,
				LdapDataAutoConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBeanNamesForType(LdapTemplate.class).length)
				.isEqualTo(0);
	}


	@Configuration
	static class LdapCustomConfiguration {

		@Bean
		public ContextSource contextSource1() {
			return mock(LdapContextSource.class);
		}

		@Bean
		public ContextSource contextSource2() {
			return mock(LdapContextSource.class);
		}

	}

}
