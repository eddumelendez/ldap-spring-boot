package com.github.eddumelendez.autoconfigure.ldap.embedded;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Embedded LDAP.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "ldap.embedded")
public class EmbeddedLdapProperties {

	/**
	 * Embedded LDAP port.
	 */
	private int port = 0;

	/**
	 * Embedded LDAP credentials.
	 */
	private Credential credential = new Credential();

	/**
	 * LDAP partition suffix.
	 */
	private String partitionSuffix;

	/**
	 * Schema (LDIF) script resource reference.
	 */
	private String ldif;

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Credential getCredential() {
		return this.credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public String getPartitionSuffix() {
		return this.partitionSuffix;
	}

	public void setPartitionSuffix(String partitionSuffix) {
		this.partitionSuffix = partitionSuffix;
	}

	public String getLdif() {
		return this.ldif;
	}

	public void setLdif(String ldif) {
		this.ldif = ldif;
	}

	static class Credential {

		/**
		 * Embedded LDAP username.
		 */
		private String username;

		/**
		 * Embedded LDAP password.
		 */
		private String password;

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

	}

}
