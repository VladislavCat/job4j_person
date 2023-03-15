package ru.job4j.persons.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.bank.model.Operations;
import ru.job4j.persons.repository.PersonRepository;
import ru.job4j.persons.model.Person;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private PersonRepository users;
    private BCryptPasswordEncoder encoder;

    public UserController(PersonRepository users,
                          BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    @Validated(Operations.OnCreate.class)
    public ResponseEntity<?> signUp(@Valid @RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return users.save(person).getId() != 0
                ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        Object body = users.findAll();
        return new ResponseEntity(
                body,
                HttpStatus.OK
        );
    }
}