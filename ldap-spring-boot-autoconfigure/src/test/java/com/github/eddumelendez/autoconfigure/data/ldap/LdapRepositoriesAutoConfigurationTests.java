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

import com.github.eddumelendez.autoconfigure.TestAutoConfigurationPackage;
import com.github.eddumelendez.autoconfigure.ldap.LdapAutoConfiguration;
import com.github.eddumelendez.autoconfigure.data.alt.PersonLdapRepository;
import com.github.eddumelendez.autoconfigure.data.empty.EmptyDataPackage;
import com.github.eddumelendez.autoconfigure.data.ldap.person.Person;
import com.github.eddumelendez.autoconfigure.data.ldap.person.PersonRepository;
import org.junit.After;
import org.junit.Test;

import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.repository.config.EnableLdapRepositories;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapRepositoriesAutoConfiguration}
 *
 * @author Eddú Meléndez
 */
public class LdapRepositoriesAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@After
	public void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void testDefaultRepositoryConfiguration() throws Exception {
		load(TestConfiguration.class);
		assertThat(this.context.getBean(PersonRepository.class)).isNotNull();
	}

	@Test
	public void testNoRepositoryConfiguration() throws Exception {
		load(EmptyConfiguration.class);

		assertThat(this.context.getBeanNamesForType(PersonRepository.class).length)
				.isEqualTo(0);
	}

	@Test
	public void doesNotTriggerDefaultRepositoryDetectionIfCustomized() {
		load(CustomizedConfiguration.class);
		assertThat(this.context.getBean(PersonLdapRepository.class)).isNotNull();
	}

	private void load(Class<?>... configurationClasses) {
		this.context = new AnnotationConfigApplicationContext();
		EnvironmentTestUtils.addEnvironment(this.context,
				"spring.ldap.urls:ldap://localhost:389");
		this.context.register(configurationClasses);
		this.context.register(LdapAutoConfiguration.class,
				LdapDataAutoConfiguration.class,
				LdapRepositoriesAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
	}

	@Configuration
	@TestAutoConfigurationPackage(Person.class)
	protected static class TestConfiguration {

	}

	@Configuration
	@TestAutoConfigurationPackage(EmptyDataPackage.class)
	protected static class EmptyConfiguration {

	}

	@Configuration
	@TestAutoConfigurationPackage(LdapRepositoriesAutoConfigurationTests.class)
	@EnableLdapRepositories(basePackageClasses = PersonLdapRepository.class)
	protected static class CustomizedConfiguration {

	}

}
