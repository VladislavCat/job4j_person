package ru.job4j.persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.job4j.persons.repository.PersonRepository;

@SpringBootApplication
@ComponentScan("ru.job4j.persons")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
