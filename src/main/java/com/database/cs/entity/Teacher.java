package com.database.cs.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Data
public class Teacher {

    @Id
    @GeneratedValue
    private Integer tid;

    @Column(unique = true, nullable = true, length = 50)
    private String teaId;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String aid;

    @Column(length = 50)
    private String major;

    @Column(length = 50)
    private String position;

    private String password;
}
