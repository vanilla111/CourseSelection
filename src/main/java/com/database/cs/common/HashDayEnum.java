package com.database.cs.common;

public enum  HashDayEnum {
    Monday(0, "星期一"), Tuesday(1, "星期二"), Wednesday(2, "星期三"),
    Thursday(3, "星期四"), Friday(4, "星期五"), Saturday(5, "星期六"), Sunday(6, "星期天");

    private final int code;
    private final String desc;

    HashDayEnum(int code, String desc) {
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
