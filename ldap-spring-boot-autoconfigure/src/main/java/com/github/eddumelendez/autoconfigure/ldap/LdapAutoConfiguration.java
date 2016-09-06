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
