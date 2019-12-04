package ru.itmo.phonebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.phonebook.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByNameLike(String name);
}
