package org.nikita111100.onlinetesting.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "possible_answer")
public class PossibleAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Заполните поле text")
    @Size(min = 2, max = 500, message = "Размер должен быть от 2 до 500 символов")
    private String text;

    @Column(name = "correct_answer")
    private int correctAnswer;

    @ManyToOne
    @JoinColumn(name = "questions_id")
    private Question questions;

    @OneToMany(mappedBy = "possibleAnswer", cascade = CascadeType.REMOVE)
    private List<AnswerQuestion> answerQuestion;
}
