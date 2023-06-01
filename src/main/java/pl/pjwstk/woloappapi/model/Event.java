package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="event")
public class Event {

    @Id
    private long id;
    private String image;
    private String name;
    private String description;
    private String requests;
    private String city;
    private String country;
    private String street;
}
