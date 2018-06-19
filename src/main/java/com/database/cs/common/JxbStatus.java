package com.database.cs.common;

public enum JxbStatus {
    CLOSE(1, "关闭选课"),
    OPEN(0, "开放选课");

    private final int code;
    private final String desc;

    JxbStatus(int code, String desc) {
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
