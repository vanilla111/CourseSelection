package com.database.cs.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Academy {

    private int id;

    @NotEmpty(message = "学院名不能为空")
    private String acadName;
}
