package pl.pjwstk.woloappapi.database;

import javax.persistence.*;


@Entity
@Table(name="account")
public class Account {

    @Column(name="account_name")
    String name;
    @Id
    @GeneratedValue
    @Column(name="account_id")
    Long id;

    @Column(name="account_email")
    String email;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
