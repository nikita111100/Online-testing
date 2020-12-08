package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@NamedQuery(name = "getAllQuestionsByTest",
        query = "SELECT c FROM Question c WHERE c.testId = ?1")
@Table(name = "questions")
public class Question{
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "test_id")
    private int testId;
    @Column(name = "text")
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
