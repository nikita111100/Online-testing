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
@Table(name = "user")
public class User {
    @Id
    private int id;

    private String name;

    private int role;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    private List<Test> tests ;

    @OneToMany(mappedBy = "userId")
    private List<AnswerTest> answerTests;

}
