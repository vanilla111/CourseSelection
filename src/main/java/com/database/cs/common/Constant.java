package com.database.cs.common;

public class Constant {
    public final static int YEAR = 181;
    public final static int JXB_NUM_LIMIT = 300;

    // redis key前缀
    public final static String CS_LOCK = "cslock:";

    // redis 获取锁的超时时间 秒
    public final static long LOCK_TIME_OUT = 10;

    // redis 锁过期时间
    public final static int EXPIRE_TIME = 5;

    public enum CSelectStatus {
        CS(1, "选中"), NO_CS(0, "取消");

        private final int code;
        private final String desc;
        CSelectStatus(int code, String desc) {
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
}
