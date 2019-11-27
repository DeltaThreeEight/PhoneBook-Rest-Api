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
    public User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PutMapping
    public User editUser(@RequestBody User editedUser) {
        return userRepository.findById(editedUser.getId()).map(user -> {
            user.setName(editedUser.getName());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(editedUser.getId()));
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        if (!userRepository.findById(id).isPresent())
            throw new UserNotFoundException(id);

        userRepository.deleteById(id);
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
