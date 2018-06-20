package com.database.cs.vo;

import lombok.Data;

@Data
public class JxbVo {
    // 课程代号
    private String courseCode;

    // 教学班编号
    private String jxbId;

    // 教学班
    private String jxbName;

    // 周几
    private String day;

    // 例如：第9-10节
    private String lesson;

    // 地点
    private String classRoom;

    // 教师
    private String teacher;

    // 课程类型
    private String type;

    // 例如：1-6周,8-17周
    private String rawWeek;

    private Integer credit;
}
