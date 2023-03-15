package ru.job4j.persons.model;

import lombok.*;
import ru.job4j.bank.model.Operations;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Table(name = "person")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @NotNull(message = "Id must be non null", groups = {
            Operations.OnUpdate.class, Operations.OnDelete.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Login must be non empty")
    private String login;
    @NotEmpty(message = "Password must be non empty")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
