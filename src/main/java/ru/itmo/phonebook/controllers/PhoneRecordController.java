package ru.itmo.phonebook.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.exceptions.PhoneRecordNotFoundException;
import ru.itmo.phonebook.repositories.PhoneRecordRepository;

@RestController
@RequestMapping("records")
public class PhoneRecordController {
    private PhoneRecordRepository phoneRecordRepository;

    public PhoneRecordController(PhoneRecordRepository recordRepository) {
        this.phoneRecordRepository = recordRepository;
    }

    @PostMapping
    public PhoneRecord newPhoneRecord(@RequestBody PhoneRecord newPhoneRecord) {
        return phoneRecordRepository.save(newPhoneRecord);
    }

    @PutMapping
    public PhoneRecord editPhoneRecord(@RequestBody PhoneRecord editedPhoneRecord) {
        return phoneRecordRepository.findById(editedPhoneRecord.getId()).map(record -> {
            record.setDescription(editedPhoneRecord.getDescription());
            record.setPhoneNumber(editedPhoneRecord.getPhoneNumber());
            return phoneRecordRepository.save(record);
        }).orElseThrow(() -> new PhoneRecordNotFoundException(editedPhoneRecord.getId()));
    }

    @DeleteMapping("{id}")
    public void deletePhoneRecord(@PathVariable Long id) {
        if (!phoneRecordRepository.findById(id).isPresent())
            throw new PhoneRecordNotFoundException(id);

        phoneRecordRepository.deleteById(id);
    }

    @GetMapping("{id}")
    public PhoneRecord getPhoneRecord(@PathVariable Long id) {
        return phoneRecordRepository.findById(id).orElseThrow(() -> new PhoneRecordNotFoundException(id));
    }

    @GetMapping("search")
    public PhoneRecord getPhoneRecordByNumber(String phone) {
        System.out.println("PHONE RECORD BY NUMBER "+phone);
        return phoneRecordRepository.findByPhoneNumberEquals(phone);
    }
}
