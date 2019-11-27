package ru.itmo.phonebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.entities.User;

import java.util.Collection;

@RepositoryRestResource
public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, Long> {
    Collection<PhoneRecord> findAllByUser(User user);

    PhoneRecord findByPhoneNumberEquals(String number);
}
