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

package com.github.eddumelendez.sample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.LdapTemplate;

/**
 * @author Eddú Meléndez
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	private LdapTemplate ldapTemplate;

	public Application(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... args) throws Exception {
		List<String> members = this.ldapTemplate.list("ou=company1,c=Sweden,dc=spring,dc=org");
		for (String member : members) {
			System.out.println(member);
		}
	}

}
