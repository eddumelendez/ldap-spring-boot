package com.github.eddumelendez.autoconfigure.ldap;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;

/**
 * Configuration properties to configure {@link LdapTemplate}.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "ldap")
public class LdapProperties {

	private static final int DEFAULT_PORT = 389;

	/**
	 * LDAP urls.
	 */
	private String[] urls = new String[0];

	/**
	 * Base suffix from which all operations should originate.
	 */
	private String base;

	/**
	 * Login user of the LDAP.
	 */
	private String username;

	/**
	 * Login password of the LDAP.
	 */
	private String password;

	/**
	 * LDAP custom environment properties.
	 */
	private Map<String, String> baseEnvironment = new HashMap<String, String>();

	public String[] getUrls() {
		return this.urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, String> getBaseEnvironment() {
		return this.baseEnvironment;
	}

	public void setBaseEnvironment(Map<String, String> baseEnvironment) {
		this.baseEnvironment = baseEnvironment;
	}

	public String[] determineUrls(Environment environment) {
		if (this.urls.length == 0) {
			String protocol = "ldap://";
			String host = "localhost";
			int port = determinePort(environment);
			String[] ldapUrls = new String[1];
			ldapUrls[0] = protocol + host + ":" + port;
			return ldapUrls;
		}
		return this.urls;
	}

	private int determinePort(Environment environment) {
		if (environment != null) {
			String localPort = environment.getProperty("local.ldap.port");
			if (localPort != null) {
				return Integer.valueOf(localPort);
			}
			else {
				return DEFAULT_PORT;
			}
		}
		throw new IllegalStateException(
				"No local ldap port configuration is available");
	}

}
