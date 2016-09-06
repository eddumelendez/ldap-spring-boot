package com.github.eddumelendez.sample;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Eddú Meléndez
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@ClassRule
	public static OutputCapture output = new OutputCapture();

	@Test
	public void load() {
		assertThat(this.output.toString()).contains("cn=Some Person");
	}

}
