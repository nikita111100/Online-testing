package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table( name = "answer_test")
public class AnswerTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int result;

    @ManyToOne
    @JoinColumn(name="test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "answerTest",cascade = CascadeType.REMOVE)
    private List<AnswerQuestion> AnswerQuestions;

}
