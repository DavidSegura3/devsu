package com.devsu.demo.ms.client.repositories;


import com.devsu.demo.ms.client.models.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository extends CrudRepository<Person, Long> {
}