package ru.itmo.phonebook.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PhoneRecord {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @ManyToOne
    private User user;
}
