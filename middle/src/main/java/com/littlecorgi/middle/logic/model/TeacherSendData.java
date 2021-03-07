package com.littlecorgi.middle.logic.model;

/**
 * 老师发起签到要传输的数据
 */
public class TeacherSendData {

    private String theme; // 签到主题
    private String title; // 签到说明
    private int label; // 签到类型
    private String className; // 签到的班级
    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private String lat; // 纬度  (签到的中心点)
    private String ing; // 经度

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }
}
