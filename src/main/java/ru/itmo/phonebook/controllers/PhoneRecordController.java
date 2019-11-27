package ru.itmo.phonebook.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.entities.User;
import ru.itmo.phonebook.exceptions.PhoneRecordNotFoundException;
import ru.itmo.phonebook.exceptions.UserNotFoundException;
import ru.itmo.phonebook.repositories.PhoneRecordRepository;
import ru.itmo.phonebook.repositories.UserRepository;

@RestController
@RequestMapping("records")
public class PhoneRecordController {
    private PhoneRecordRepository phoneRecordRepository;
    private UserRepository userRepository;

    public PhoneRecordController(PhoneRecordRepository recordRepository, UserRepository userRepository) {
        this.phoneRecordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String newPhoneRecord(@RequestBody PhoneRecord newPhoneRecord) {
        userRepository.findById(newPhoneRecord.getUser().getId()).map(user -> {
            newPhoneRecord.setUser(user);
            return phoneRecordRepository.save(newPhoneRecord);
        }).orElseThrow(() -> new UserNotFoundException(newPhoneRecord.getUser().getId()));

        return "Phone record successfully created";
    }

    @PutMapping("{id}")
    public String editPhoneRecord(@PathVariable Long id, @RequestBody PhoneRecord editedPhoneRecord) {
        phoneRecordRepository.findById(id).map(record -> {
            User user = userRepository.findById(editedPhoneRecord.getUser().getId()).orElseThrow(() -> new UserNotFoundException(editedPhoneRecord.getUser().getId()));

            record.setUser(user);
            record.setDescription(editedPhoneRecord.getDescription());
            record.setPhoneNumber(editedPhoneRecord.getPhoneNumber());
            return phoneRecordRepository.save(record);
        }).orElseThrow(() -> new PhoneRecordNotFoundException(editedPhoneRecord.getId()));

        return "Phone record successfully edited";
    }

    @DeleteMapping("{id}")
    public String deletePhoneRecord(@PathVariable Long id) {
        if (!phoneRecordRepository.findById(id).isPresent())
            throw new PhoneRecordNotFoundException(id);

        phoneRecordRepository.deleteById(id);

        return "Phone record successfully deleted";
    }

    @GetMapping("{id}")
    public PhoneRecord getPhoneRecord(@PathVariable Long id) {
        return phoneRecordRepository.findById(id)
                .orElseThrow(() -> new PhoneRecordNotFoundException(id));
    }

    @GetMapping("search")
    public PhoneRecord getPhoneRecordByNumber(String phone) {
        return phoneRecordRepository.findByPhoneNumberEquals(phone);
    }
}
