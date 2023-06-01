package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private long experience_id;
    private long role_id;


}
