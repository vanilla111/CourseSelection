package com.database.cs.util;

import com.database.cs.entity.JXB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JxbUtil {

    /**
     * 生成一个新的教学班id，要求唯一
     * @param courseCode
     * @return
     */
    public static String getNewJxbId(String courseCode) {
        return courseCode + UUIDUtil.create();
    }

    /**
     * 格式化jxb的时间，方便展示
     * @param jxb
     */
    public static void timeFormat(JXB jxb) {
        jxb.setDay(getDay(jxb.getHashDay()));
        int beginLesson = jxb.getHashLesson() * 2 + 1;
        int endLesson = beginLesson + jxb.getPeriod() - 1;
        jxb.setLesson("第" + beginLesson + "-" + endLesson + "节");
        jxb.setRawWeek(getRawWeek(jxb.getWeek()));
    }

    /**
     * 检查教学班在教室，时间（小节、周数）等方面的冲突
     * @param jxbList
     * @param target
     * @return 冲突的教学班集合
     */
    public static List<JXB> checkJxbConflict(List<JXB> jxbList, JXB target) {
        List<JXB> res = new ArrayList<>();
        for (JXB jxb : jxbList) {
            // 1. 判断周数冲突
            boolean weekFlag = checkWeek(jxb.getWeek(), target.getWeek());
            // 2. 判断小节冲突，注意3，4节连上的
            boolean lessonFlag = checkLesson(jxb, target);
            // 两个都冲突，该课程安排必定有冲突
            if (weekFlag && lessonFlag) res.add(jxb);
        }

        return res;
    }

    /**
     * 例如以下两个例子，在13-16周冲突
     * week1 = 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
     * week2 = 13,14,15,16
     * @param week1
     * @param week2
     * @return
     */
    private static boolean checkWeek(String week1, String week2) {
        String[] c1 = week1.split(",");
        String[] c2 = week2.split(",");
        for (String s : c1) {
            for (String s1 : c2) {
                if (s.equals(s1)) return true;
            }
        }
        return false;
    }

    /**
     * 检查小节是否冲突，如1，2，3节的课与3，4节课冲突
     * @param jxb
     * @param target
     * @return
     */
    private static boolean checkLesson(JXB jxb, JXB target) {
        boolean lessonFlag = false;
        if (jxb.getHashLesson() == target.getHashLesson())
            lessonFlag = true;
        else {
            int t1 = jxb.getHashLesson() * 2 + jxb.getPeriod();
            int t2 = target.getHashLesson() * 2 + target.getPeriod();
            if (t1 >= (target.getHashLesson() * 2 + 1) && t1 <= t2)
                lessonFlag = true;
            else if (t2 >= (jxb.getHashLesson() * 2 + 1) && t2 <= t1)
                lessonFlag = true;
        }

        return lessonFlag;
    }

    /**
     * 数值换对应星期几
     * @param hashDay
     * @return
     */
    private static String getDay(int hashDay) {
        switch (hashDay) {
            case 0: return "星期一";
            case 1: return "星期二";
            case 2: return "星期三";
            case 3: return "星期四";
            case 4: return "星期五";
            case 5: return "星期六";
            case 6: return "星期天";
            default: return "星期0";
        }
    }

    /**
     * 将week转变为易读的字符，如
     * week = 1,2,3,5,6,8,9,10,11,12,13,14,15
     * => rawWeek = 1-3周,5-6周,8-15周
     * week = 1,3,5,9,11,13,15
     * => rawWeek = 1-5周单周,9-15周单周
     * week = 无安排
     * => rawWeek = 无安排
     * @param week
     * @return
     */
    private static String getRawWeek(String week) {
        if ("无安排".equals(week)) return week;
        // 首先将字符串转变为数字数组
        String[] chars = week.split(",");

        int[] weeks = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            weeks[i] = Integer.valueOf(chars[i]);
        }
        Arrays.sort(weeks);

        int start = 0, end = -1;
        StringBuilder rawWeek = new StringBuilder();
        for (int i = 1; i < weeks.length; i++) {
            start = end + 1;
            if (weeks[i] - weeks[i - 1] > 2) {
                end = i - 1;
                rawWeek.append(subWeek(weeks, start, end) + ",");
                if (i + 1 == weeks.length) start = end + 1;
                continue;
            }
            if (weeks[i] - weeks[i - 1] == 2) {
                if (i + 1 != weeks.length) {
                    if (weeks[i + 1] - weeks[i] == 1) {
                        end = i - 1;
                        rawWeek.append(subWeek(weeks, start, end) + ",");
                        continue;
                    }
                }
            }
        }

        rawWeek.append(subWeek(weeks, start, weeks.length - 1));

        return rawWeek.toString();
    }

    /**
     * 将一个区段变成第几-第几周（单/双周）
     * @param weeks
     * @param start
     * @param end
     * @return
     */
    private static String subWeek(int[] weeks, int start, int end) {
        if (end - start == 0) return weeks[start] + "周";
        int i;
        for (i = start + 1; i <= end; i++) {
            if (weeks[i] - weeks[i - 1] == 2) continue;
            else break;
        }

        String temp = weeks[start] + "-" + weeks[end] + "周";
        if (i == (end + 1)) {
            if (weeks[start] % 2 == 0) return  temp + "双周";
            else  return temp + "单周";
        }

        return temp;
    }

    public static void main(String[] args) {
//        JXB j1 = new JXB();
//        j1.setHashLesson(0); j1.setPeriod(3);
//        JXB j2 = new JXB();
//        j2.setHashLesson(1); j2.setPeriod(3);
//        System.out.println(checkLesson(j2, j1));

//        String week1 = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
//        String week2 = "13,14,15,16";
//        System.out.println(checkWeek(week1, week2));

        String t1 = "无安排";
        String t2 = "1,3,4,5,6,8,9,10,11,12,13,14,15,16,17";
        String t3 = "1,2,3,4,5";
        String t4 = "4,8,12,16";
        System.out.println(getNewJxbId(t1));
    }

}
