package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "answer_question")
public class AnswerQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_test_id")
    private AnswerTest answerTest;

    @OneToOne
    @JoinColumn(name = "possible_answer_id")
    private PossibleAnswer possibleAnswer;

}
