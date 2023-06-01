package pl.pjwstk.woloappapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="term")
public class Term {
    @Id
    private long id;
    private long event_id;
    private Date date_start;
    private Date date_end;
    private int slots;
    private int free_slots;
    private int reserve_list;



}
