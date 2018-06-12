package com.database.cs.entity;

import lombok.Data;

/**
 * 教学班实体类，记录了一个教学班的上课地点、时间、人数上限等信息
 */

@Data
public class JXB {

    private Integer id;

    // 课程代号
    private String courseCode;

    // 教学班编号
    private String jxbId;

    // 教学班
    private String jxbName;

    // 开课学期
    private Integer year;

    // 周一为 0
    private Integer hashDay;

    // 第几大节课
    private Integer hashLesson;

    // 周几
    private String day;

    // 例如：第9-10节
    private String lesson;

    // 地点
    private String classRoom;

    // 一节课持续时间，如 3 表示三节连上
    private Integer period;

    // 教师
    private String teacher;

    // 课程类型
    private String type;

    // 例如：1-6周,8-17周
    private String rawWeek;

    // 上课周 例如：1,2,3,4,5,6,8,9,10,11,12,13,14,15,16,17
    private String week;

    // 人数限制
    private Integer numLimit;

    // 限选人数
    private Integer currentNum;

    private String status;
}
