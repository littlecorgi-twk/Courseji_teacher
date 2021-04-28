package com.littlecorgi.attendance.tools;

/**
 * 缺勤的数据类
 */
public class Leave {

    private String lesson;
    private String teacher;
    private String time;
    private String reason;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 构造方法
     */
    public Leave(String lesson, String teacher, String time, String reason, String type) {
        this.lesson = lesson;
        this.teacher = teacher;
        this.time = time;
        this.reason = reason;
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
