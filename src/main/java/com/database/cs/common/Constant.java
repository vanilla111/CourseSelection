package com.database.cs.common;

public class Constant {
    public final static int YEAR = 181;
    public final static int JXB_NUM_LIMIT = 300;

    // redis key前缀
    public final static String CS_LOCK = "cslock:";

    // redis 获取锁的超时时间 秒
    public final static long LOCK_TIME_OUT = 10;

    // redis 锁过期时间
    public final static int EXPIRE_TIME = 20;

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

    public enum QType {
        OTHER("其他"),
        COMPUTER("计算机"),
        ECONOMIC("经管"),
        ISL("安法学院"),
        SCIENCE("理学院"),
        BIOLOGY("生物学院"),
        OPTOELECTRONICS("光电学院"),
        AUTOMATION("自动化学院"),
        ADVANCE("先进制造学院"),
        INTERNATIONAL("国际"),
        COMMUNICATION("通信"),
        SOFTWARE("软件"),
        GIRLS("传媒"),
        PE("体育"),
        FOREIGN("外国语");

        private String qName;

        QType(String qName) {
            this.qName = qName;
        }

        public String getQType() {
            return qName;
        }
    }
}
