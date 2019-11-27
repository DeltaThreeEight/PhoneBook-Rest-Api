package ru.itmo.phonebook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.phonebook.exceptions.PhoneRecordNotFoundException;
import ru.itmo.phonebook.exceptions.UserNotFoundException;

@ControllerAdvice
public class ControllersAdvice  {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PhoneRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String phoneRecordNotFoundHandler(PhoneRecordNotFoundException ex) {
        return ex.getMessage();
    }

}
