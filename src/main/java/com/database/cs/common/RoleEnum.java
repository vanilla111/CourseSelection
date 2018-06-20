package com.database.cs.common;

public enum RoleEnum {
    ADMIN(0, "管理员"),
    TEACHER(1, "教师"),
    STUDENT(2, "学生");

    private final int code;
    private final String desc;

    RoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
