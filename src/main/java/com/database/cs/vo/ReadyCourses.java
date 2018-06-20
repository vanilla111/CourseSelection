package com.database.cs.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReadyCourses {

    private String courseCode;

    private String courseName;

    private List<JxbVo> jxbVos;
}
