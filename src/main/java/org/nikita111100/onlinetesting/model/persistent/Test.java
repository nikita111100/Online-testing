package org.nikita111100.onlinetesting.model.persistent;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "test")
public class Test {
    @Id
    @Column(name = "id")
    private int id;
    @Column (name = "name")
    private String name;
    @Column (name = "theme")
    private String theme;
}
