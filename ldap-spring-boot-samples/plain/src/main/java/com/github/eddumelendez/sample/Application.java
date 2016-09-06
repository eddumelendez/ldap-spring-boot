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
