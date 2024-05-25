package pl.pjwstk.woloappapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "faq")
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_pl", nullable = false)
    private String questionPL;

    @Column(name = "answer_pl", nullable = false)
    private String answerPL;

    @Column(name = "question_en", nullable = false)
    private String questionEN;

    @Column(name = "answer_en", nullable = false)
    private String answerEN;

    @Column(name = "question_ua", nullable = false)
    private String questionUA;

    @Column(name = "answer_ua", nullable = false)
    private String answerUA;

    @Column(name = "question_ru", nullable = false)
    private String questionRU;

    @Column(name = "answer_ru", nullable = false)
    private String answerRU;

}
