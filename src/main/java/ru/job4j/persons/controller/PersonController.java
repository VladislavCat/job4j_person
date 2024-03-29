package ru.job4j.persons.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.bank.model.Operations;
import ru.job4j.persons.model.Person;
import ru.job4j.persons.service.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService persons;

    @GetMapping("/")
    public List<Person> findAll() {
        return persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    @Validated(Operations.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        return new ResponseEntity<Person>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operations.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        return this.persons.save(person).getId() != 0 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        if (persons.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        this.persons.delete(person);
        return persons.findById(id).isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }
}

