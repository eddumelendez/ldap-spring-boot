package com.github.eddumelendez.sample;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * @author Eddú Meléndez
 */
@Entry(objectClasses = {"person", "top"}, base = "ou=company1")
public class Person {

	@Id
	private Name dn;

	@Attribute(name = "cn")
	private String fullName;

	public Name getDn() {
		return this.dn;
	}

	public void setDn(Name dn) {
		this.dn = dn;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "Person{" +
				"dn=" + this.dn +
				", fullName='" + this.fullName + '\'' +
				'}';
	}
}
