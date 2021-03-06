package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@SuppressWarnings("checkstyle:MagicNumber")
@Data
@NoArgsConstructor
@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @NotEmpty(message = "Заполни поле name ")
    @Size(min = 2, max = 100, message = "Размер должен быть от 2 до 100 символов")
    private String name;

    @NotEmpty(message = "Заполни поле theme ")
    @Size(min = 2, max = 500, message = "Размер должен быть от 2 до 500 символов")
    private  String theme;
    @ManyToMany
    @JoinTable(name = "user_tests",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<AnswerTest> answerTests;

    public Test(Long id, String name, String theme) {
        this.id = id;
        this.name = name;
        this.theme = theme;
    }
}
