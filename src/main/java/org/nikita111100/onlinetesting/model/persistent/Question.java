package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "questions")
public class Question{
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "text")
    private String text;
    @Column(name = "test_id")
    private int testId;

}
