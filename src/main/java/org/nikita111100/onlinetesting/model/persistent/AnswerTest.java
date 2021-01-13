package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "answer_test")
public class AnswerTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int result;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "answerTest", cascade = CascadeType.REMOVE)
    private List<AnswerQuestion> answerQuestions;

}
