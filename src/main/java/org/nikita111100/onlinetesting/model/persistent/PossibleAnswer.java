package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "possible_answer")
public class PossibleAnswer {
    @Id
    @Column(name= "id")
    private int id;
    @Column(name= "text")
    private String test;
    @Column(name= "correct_answer")
    private int correctAnswer;
    @Column(name= "questions_id")
    private int questionsId;
}
