package pl.pjwstk.woloappapi.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Builder

@AllArgsConstructor
@Table(name="person")
public class Person {

    @Id
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
