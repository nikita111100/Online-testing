package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "answer_test")
public class AnswerTest {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="result")
    private int result;
    @Column(name="test_id")
    private int testId;
    @Column(name="user_id")
    private int userId;

}
