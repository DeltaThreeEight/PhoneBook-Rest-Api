package ru.itmo.phonebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.entities.User;

import java.util.Collection;

public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, Long> {
    Collection<PhoneRecord> findAllByUser(User user);

    PhoneRecord findByPhoneNumberEquals(String number);
}
