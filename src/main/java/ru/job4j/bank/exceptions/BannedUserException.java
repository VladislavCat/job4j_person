package ru.job4j.bank.exceptions;

import ru.job4j.bank.model.User;

public class BannedUserException extends Exception {
    public BannedUserException(String message, User user) {
        super(message + ": " + user);
    }
}
