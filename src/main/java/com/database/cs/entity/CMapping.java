package com.database.cs.entity;

import javax.persistence.*;

/**
 * 课程对照表，一个课程号courseCode 下对应一个课程的名称
 */
@Entity
@Table(name = "course_mapping")
public class CMapping {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(targetEntity = com.database.cs.entity.JXB.class)
    private String courseCode;

    private String courseName;
}
