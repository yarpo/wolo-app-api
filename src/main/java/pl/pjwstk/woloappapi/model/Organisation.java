package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="organisation")
public class Organisation {

    @Id
    private long id;
    private String image;
    private String name;
    private String description;
    private String email;
    private long telephone;
    private String city;
    private String country;
    private String street;


}
