package ru.job4j.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.bank.model.Operations;
import ru.job4j.bank.model.User;
import ru.job4j.bank.service.BankService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());

    private final BankService bankService;

    private final ObjectMapper objectMapper;

    public UserController(BankService bankService, ObjectMapper objectMapper) {
        this.bankService = bankService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @Validated(Operations.OnCreate.class)
    public User save(@Valid @RequestBody Map<String, String> body) {
        var passport = body.get("passport");
        var username = body.get("username");
        if (username == null || passport == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (passport.length() < 6) {
            throw new IllegalArgumentException("Invalid passport number");
        }
        var user = new User(passport, username);
        bankService.addUser(user);
        return user;
    }

    @GetMapping
    public User findByPassport(@RequestParam String passport) {
        return bankService.findByPassport(passport).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passport is not found. Please, check username."
        ));
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

}
