package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@NamedQuery(name = "getAllQuestionsByTest",
        query = "SELECT c FROM Question c WHERE c.test = ?1")
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Заполни поле text ")
    private String text;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "questions",cascade = CascadeType.REMOVE)
    private List<PossibleAnswer> possibleAnswers;

    public Question(Long id, String text, Test test) {
        this.id = id;
        this.text = text;
        this.test = test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
