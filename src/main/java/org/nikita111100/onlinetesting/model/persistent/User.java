package org.nikita111100.onlinetesting.model.persistent;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Заполните поле name ")
    @Size(min = 2,max=20,message = "Размер поля name должен быть от 2 до 10")
    private String name;

    @NotEmpty(message = "заполните поле password")
    @Size(min = 2,max=10,message = "Размер поля password должен быть от 2 до 10")
    private String password;

    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    private List<Test> tests ;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.REMOVE)
    private List<AnswerTest> answerTests;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
