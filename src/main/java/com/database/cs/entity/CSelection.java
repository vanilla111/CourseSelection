package com.database.cs.entity;

import lombok.Data;

import java.util.Date;

/**
 * 选课表
 */
@Data
public class CSelection {

    private Integer id;

    private String stuId;

    private String courseCode;

    private String jxbId;

    // 选课类型：正常，先修，补修，重修
    private String type;

    private Date createdAt;

    private Date updatedAt;

    private int year;

    private int status;
}
