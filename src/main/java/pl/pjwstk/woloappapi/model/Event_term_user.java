package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="event_term_user")
public class Event_term_user {
    private long event_id;
    private long term_id;
    private long user_id;
}
