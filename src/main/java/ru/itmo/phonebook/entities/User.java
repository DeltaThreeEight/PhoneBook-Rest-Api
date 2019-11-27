package ru.itmo.phonebook.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue
    private long id;
    @NonNull
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("{\"id\":%s,\"name\":\"%s\"}",id, name);
    }
}
