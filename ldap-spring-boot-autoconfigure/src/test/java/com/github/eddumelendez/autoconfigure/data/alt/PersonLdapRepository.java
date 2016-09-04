package com.github.eddumelendez.autoconfigure.data.alt;

import javax.naming.Name;

import com.github.eddumelendez.autoconfigure.data.ldap.person.Person;

import org.springframework.data.repository.Repository;

public interface PersonLdapRepository extends Repository<Person, Name> {

}
