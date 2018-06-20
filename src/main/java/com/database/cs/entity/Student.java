package com.database.cs.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Student {

    private Integer sid;

    @NotEmpty(message = "学号不能为空")
    private String stuId;

    @Pattern(regexp = "[女|男]", message = "请输入正确的性别,女/男")
    private String gender;

    @NotEmpty(message = "学生姓名不能为空")
    private String name;

    @NotEmpty(message = "所属院系不能为空")
    private String academy;

    private String major;

    @NotEmpty(message = "所属班级不能为空")
    private String classNum;

    private Integer grade;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String headUrl;

    private Date birthday;

    private int status;
}
