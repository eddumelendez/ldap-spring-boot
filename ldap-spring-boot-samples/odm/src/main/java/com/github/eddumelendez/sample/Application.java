package com.github.eddumelendez.sample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eddú Meléndez
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	private PersonRepository repository;

	public Application(PersonRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... args) throws Exception {
		Person person = this.repository.findByUid("some.person2");
		System.out.println(person);
	}

}
