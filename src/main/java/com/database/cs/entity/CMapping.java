package com.database.cs.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 课程对照表，一个课程号courseCode 下对应一个课程的名称
 */
@Data
public class CMapping {

    private Integer id;

    @NotEmpty(message = "课程代号不能为空")
    private String courseCode;

    @NotEmpty(message = "课程名不能为空")
    private String courseName;
}
