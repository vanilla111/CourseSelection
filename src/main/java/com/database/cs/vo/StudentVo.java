package com.database.cs.vo;

import lombok.Data;

import java.util.Date;

@Data
public class StudentVo {

    private String stuId;

    private String gender;

    private String name;

    private String academy;

    private String major;

    private String classNum;

    private Integer grade;

    private String headUrl;

    private Date birthday;
}
