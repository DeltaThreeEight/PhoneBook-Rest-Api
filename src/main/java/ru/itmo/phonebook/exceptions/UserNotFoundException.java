package ru.itmo.phonebook.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("No user found with id " + id);
    }
}
