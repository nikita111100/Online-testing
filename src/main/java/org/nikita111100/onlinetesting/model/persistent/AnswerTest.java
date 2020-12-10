package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table( name = "answer_test")
public class AnswerTest {
    @Id
    private int id;

    private int result;

    @ManyToOne
    @JoinColumn(name="test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "answerTest")
    private List<AnswerQuestion> AnswerQuestions;

}
