package com.github.eddumelendez.autoconfigure.ldap.embedded;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import com.github.eddumelendez.autoconfigure.ldap.LdapAutoConfiguration;
import com.github.eddumelendez.autoconfigure.ldap.LdapProperties;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Embedded LDAP.
 *
 * @author Eddú Meléndez
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({LdapProperties.class, EmbeddedLdapProperties.class })
@AutoConfigureBefore(LdapAutoConfiguration.class)
@ConditionalOnClass(InMemoryDirectoryServer.class)
public class EmbeddedLdapAutoConfiguration {

	private InMemoryDirectoryServer server;

	private EmbeddedLdapProperties embeddedProperties;

	private LdapProperties properties;

	private ConfigurableApplicationContext applicationContext;

	private Environment environment;

	public EmbeddedLdapAutoConfiguration(EmbeddedLdapProperties embeddedProperties,
		LdapProperties properties,
		ConfigurableApplicationContext applicationContext,
		Environment environment) {
		this.embeddedProperties = embeddedProperties;
		this.properties = properties;
		this.applicationContext = applicationContext;
		this.environment = environment;
	}

	@Bean
	@DependsOn("directoryServer")
	@ConditionalOnMissingBean
	public ContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();

		EmbeddedLdapProperties.Credential credential = this.embeddedProperties
				.getCredential();
		if (StringUtils.hasText(credential.getUsername()) &&
				StringUtils.hasText(credential.getPassword())) {
			contextSource.setUserDn(credential.getUsername());
			contextSource.setPassword(credential.getPassword());
		}
		contextSource.setUrls(this.properties.determineUrls(this.environment));
		return contextSource;
	}

	@Bean
	public InMemoryDirectoryServer directoryServer() throws LDAPException {
		InMemoryDirectoryServerConfig config =
				new InMemoryDirectoryServerConfig(this.embeddedProperties
						.getPartitionSuffix());

		EmbeddedLdapProperties.Credential credential = this.embeddedProperties
				.getCredential();
		if (StringUtils.hasText(credential.getUsername()) &&
				StringUtils.hasText(credential.getPassword())) {
			config.addAdditionalBindCredentials(credential
					.getUsername(), credential.getPassword());
		}

		config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("LDAP",
				this.embeddedProperties.getPort()));

		this.server = new InMemoryDirectoryServer(config);

		populateDirectoryServer();

		this.server.startListening();
		publishPortInfo(this.server.getListenPort());
		return this.server;
	}

	private void publishPortInfo(int port) {
		setPortProperty(this.applicationContext, port);
	}

	private void setPortProperty(ApplicationContext currentContext,
		int port) {
		if (currentContext instanceof ConfigurableApplicationContext) {
			MutablePropertySources sources = ((ConfigurableApplicationContext)
					currentContext).getEnvironment().getPropertySources();
			getLdapPorts(sources).put("local.ldap.port", port);
		}
		if (currentContext.getParent() != null) {
			setPortProperty(currentContext.getParent(), port);
		}
	}

	private Map<String, Object> getLdapPorts(MutablePropertySources sources) {
		PropertySource<?> propertySource = sources.get("ldap.ports");
		if (propertySource == null) {
			propertySource = new MapPropertySource("ldap.ports",
					new HashMap<String, Object>());
			sources.addFirst(propertySource);
		}
		return (Map<String, Object>) propertySource.getSource();
	}

	private void populateDirectoryServer() throws LDAPException {
		String location = this.embeddedProperties.getLdif();
		if (StringUtils.hasText(location)) {
			try {
				Resource resource = this.applicationContext.getResource(
						this.embeddedProperties.getLdif());
				if (resource.exists()) {
					File tempFile = File.createTempFile("ldap_test_data", ".ldif");
					try {
						InputStream inputStream = resource.getInputStream();
						FileCopyUtils.copy(inputStream, new FileOutputStream(tempFile));
						this.server.importFromLDIF(true, new LDIFReader(tempFile));
					}
					catch (LDAPException e) {
						e.printStackTrace();
					}
					finally {
						tempFile.delete();
					}
				}
			}
			catch (IOException ex) {
				throw new IllegalStateException(
						"Unable to load resource from " + location, ex);
			}
		}
	}

	@PreDestroy
	public void close() {
		if (this.server != null) {
			this.server.shutDown(true);
		}
	}

}
