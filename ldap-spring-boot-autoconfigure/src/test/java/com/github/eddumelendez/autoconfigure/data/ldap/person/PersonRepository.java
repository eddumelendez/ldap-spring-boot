package com.github.eddumelendez.autoconfigure.data.ldap.person;

import javax.naming.Name;

import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, Name> {

}
