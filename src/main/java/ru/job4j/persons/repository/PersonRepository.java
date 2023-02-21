package ru.job4j.persons.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.persons.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
}