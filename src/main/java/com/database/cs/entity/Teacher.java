package com.database.cs.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Teacher {

    private Integer tid;

    @NotEmpty(message = "工号不能为空")
    private String teaId;

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "院系代号不能为空")
    private String aid;

    private String major;

    @NotEmpty(message = "职位不能为空")
    private String position;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
