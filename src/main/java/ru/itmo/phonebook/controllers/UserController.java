package ru.itmo.phonebook.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.entities.User;
import ru.itmo.phonebook.exceptions.UserNotFoundException;
import ru.itmo.phonebook.repositories.PhoneRecordRepository;
import ru.itmo.phonebook.repositories.UserRepository;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private UserRepository userRepository;
    private PhoneRecordRepository recordRepository;

    public UserController(UserRepository userRepository, PhoneRecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}/records")
    public Collection<PhoneRecord> getAllUserPhoneRecords(@PathVariable long id) {
        return userRepository.findById(id).map(user -> recordRepository.findAllByUser(user))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("search")
    public List<User> findAllUsersByName(String name) {
        return userRepository.findUsersByNameLike(name);
    }

    @PostMapping
    public String newUser(@RequestBody User newUser) {
        userRepository.save(newUser);
        return "User successfully created";
    }

    @PutMapping("{id}")
    public String editUser(@PathVariable long id, @RequestBody User editedUser) {
        userRepository.findById(id).map(user -> {
            user.setName(editedUser.getName());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));

        return "User successfully edited";
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable Long id) {
        if (!userRepository.findById(id).isPresent())
            throw new UserNotFoundException(id);

        userRepository.deleteById(id);

        return "User successfully deleted";
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
