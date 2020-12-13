package org.nikita111100.onlinetesting.model.persistent;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table( name = "test")
public class Test {
    @Id
    private Long id;

    private String name;

    private String theme;

    @ManyToMany
    @JoinTable(name="user_tests",
            joinColumns = @JoinColumn(name="test_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "test",cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "test")
    private List<AnswerTest> answerTests;

    public Test(Long id, String name, String theme) {
        this.id = id;
        this.name = name;
        this.theme = theme;
    }
}
