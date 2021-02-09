package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user_review")
public class UserReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @NotEmpty(message = "Заполни поле")
    @Size(min = 2, max = 500, message = "Размер должен быть от 2 до 200 символов")
    @Column(name = "message")
    private String message;
}
