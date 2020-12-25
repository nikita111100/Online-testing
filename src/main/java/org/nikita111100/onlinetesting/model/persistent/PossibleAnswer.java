package org.nikita111100.onlinetesting.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "possible_answer")
public class PossibleAnswer {
    @Id
    private Long id;

    private String text;

    @Column(name= "correct_answer")
    private int correctAnswer;

    @ManyToOne
    @JoinColumn(name= "questions_id")
    private Question questions;

    @OneToOne(mappedBy="possibleAnswer")
    private AnswerQuestion answerQuestion;

}
