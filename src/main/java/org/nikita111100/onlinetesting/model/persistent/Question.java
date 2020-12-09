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
@Table(name = "questions")
public class Question{
    @Id

    private int id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test testId;

    @OneToMany(mappedBy = "questionsId")
    private List<PossibleAnswer> possibleAnswers;

    public int getId() {
        return id;
    }
}
