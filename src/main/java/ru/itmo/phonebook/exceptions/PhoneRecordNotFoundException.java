package ru.itmo.phonebook.exceptions;

public class PhoneRecordNotFoundException extends RuntimeException {
    public PhoneRecordNotFoundException(long id) {
        super("No phone record found with id " + id);
    }
}
