package com.database.cs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Student {

    private Integer sid;

    private String stuId;

    private String gender;

    private String name;

    private String academy;

    private String major;

    private String classNum;

    private Integer grade;

    private String password;

    private String headUrl;

    @JsonIgnore
    private Date birthday;
}
