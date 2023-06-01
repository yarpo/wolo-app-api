package pl.pjwstk.woloappapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="organisation_event")
public class Organisation_event {
    private long organisation_id;
    private long event_id;
}
