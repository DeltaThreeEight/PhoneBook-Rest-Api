package ru.itmo.phonebook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PhoneRecord {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @ManyToOne
    private User user;

    public String toString() {
        return String.format("{\"id\":%s,\"description\":\"%s\",\"phoneNumber\":\"%s\",\"user\":%s}",id, description, phoneNumber, user);
    }
}
