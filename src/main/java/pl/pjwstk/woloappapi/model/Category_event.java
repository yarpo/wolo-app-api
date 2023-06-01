package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="category_event")
public class Category_event {
    private long category_id;
    private long event_id;


}
