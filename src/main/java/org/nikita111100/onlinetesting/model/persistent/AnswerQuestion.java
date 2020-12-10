package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Table( name = "answer_question")
public class AnswerQuestion {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "answer_test_id")
    private AnswerTest answerTest;

    @OneToOne
    @JoinColumn(name = "possible_answer_id")
    private PossibleAnswer possibleAnswer;



}
