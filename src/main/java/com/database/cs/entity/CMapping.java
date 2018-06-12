package com.database.cs.entity;

import lombok.Data;

/**
 * 课程对照表，一个课程号courseCode 下对应一个课程的名称
 */
@Data
public class CMapping {

    private Integer id;

    private String courseCode;

    private String courseName;
}
