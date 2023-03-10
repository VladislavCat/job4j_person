package ru.job4j.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("ru.job4j.bank")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
