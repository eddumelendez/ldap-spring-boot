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

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.repository.config.LdapRepositoryConfigurationExtension;


/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data LDAP
 * Repositories.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
class LdapRepositoriesRegistrar
		extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableLdapRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableLdapRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new LdapRepositoryConfigurationExtension();
	}

	@EnableLdapRepositories
	private static class EnableLdapRepositoriesConfiguration {

	}

}
