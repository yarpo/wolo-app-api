package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="role")

public class Role {
    @Id
    private long id;
    private String name;
}
