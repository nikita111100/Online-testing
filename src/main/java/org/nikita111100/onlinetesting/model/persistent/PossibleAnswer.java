package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Table( name = "possible_answer")
public class PossibleAnswer {
    @Id
    private int id;

    private String test;

    @Column(name= "correct_answer")
    private int correctAnswer;

    @ManyToOne
    @JoinColumn(name= "questions_id")
    private Question questions;

    @OneToOne(mappedBy="possibleAnswer")
    private AnswerQuestion answerQuestion;
}
