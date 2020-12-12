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

    @OneToMany(mappedBy = "test")
    private List<Question> questions;

    @OneToMany(mappedBy = "test")
    private List<AnswerTest> answerTests;

    public Test(Long id, String name, String theme) {
        this.id = id;
        this.name = name;
        this.theme = theme;
    }

    public Test() {
    }
}
