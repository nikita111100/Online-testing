package org.nikita111100.onlinetesting.model.persistent;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;

    private String name;

    private int role;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    private List<Test> tests ;

    @OneToMany(mappedBy = "user")
    private List<AnswerTest> answerTests;

}
