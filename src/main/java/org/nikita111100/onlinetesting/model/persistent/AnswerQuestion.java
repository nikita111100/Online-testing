package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "answer_question")
public class AnswerQuestion {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "answer_test_id")
    private int answerTestId;
    @Column(name = "possible_answer_id")
    private int possibleAnswerQuestion;

}
