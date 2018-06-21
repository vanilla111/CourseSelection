package com.database.cs.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 教学班实体类，记录了一个教学班的上课地点、时间、人数上限等信息
 */

@Data
public class JXB {

    private Integer id;

    // 课程代号
    @NotEmpty(message = "课程代号不能为空")
    private String courseCode;

    // 教学班编号
    private String jxbId;

    // 教学班
    private String jxbName;

    // 开课学期
    private Integer year;

    // 周一为 0
    @NotNull(message = "周几信息不能为空")
    @Min(value = 0, message = "取值范围0-6")
    @Max(value = 6, message = "取值范围0-6")
    private Integer hashDay;

    // 第几大节课
    @NotNull(message = "第几大节信息不能为空")
    @Min(value = 0, message = "取值范围0-5")
    @Max(value = 5, message = "取值范围0-5")
    private Integer hashLesson;

    // 周几
    private String day;

    // 例如：第9-10节
    private String lesson;

    // 地点
    @NotEmpty(message = "教室不能为空")
    private String classRoom;

    // 一节课持续时间，如 3 表示三节连上
    @NotNull(message = "课程持续时间不能为空")
    @Min(value = 0, message = "取值范围0-11")
    @Max(value = 12, message = "取值范围0-11")
    private Integer period;

    // 教师id
    @NotEmpty(message = "教师信息不能为空")
    private String teacherId;

    // 教师
    private String teacher;

    // 课程类型
    @NotEmpty(message = "课程类型不能为空")
    private String type;

    // 例如：1-6周,8-17周
    private String rawWeek;

    // 上课周 例如：1,2,3,4,5,6,8,9,10,11,12,13,14,15,16,17
    @NotEmpty(message = "哪几周上课信息不能为空")
    private String week;

    // 人数限制
    private Integer numLimit;

    // 现选人数
    private Integer currentNum;

    // 学分
    @NotNull(message = "学分不能为空")
    private Integer credit;

    private Integer status;
}
