package com.littlecorgi.middle.logic.model;


public class teacherSendData {
  /*
  老师发起签到要传输的数据
   */
  private String Theme; // 签到主题
  private String Title; // 签到说明
  private int Label; // 签到类型
  private String ClassName; // 签到的班级
  private String StartTime; // 开始时间
  private String EndTime; // 结束时间
  private String Lat; // 纬度  (签到的中心点)
  private String Ing; // 经度

  public String getLat() {
    return Lat;
  }

  public void setLat(String lat) {
    Lat = lat;
  }

  public String getIng() {
    return Ing;
  }

  public void setIng(String ing) {
    Ing = ing;
  }

  public String getTheme() {
    return Theme;
  }

  public void setTheme(String theme) {
    Theme = theme;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public int getLabel() {
    return Label;
  }

  public void setLabel(int label) {
    Label = label;
  }

  public String getClassName() {
    return ClassName;
  }

  public void setClassName(String className) {
    ClassName = className;
  }

  public String getStartTime() {
    return StartTime;
  }

  public void setStartTime(String startTime) {
    StartTime = startTime;
  }

  public String getEndTime() {
    return EndTime;
  }

  public void setEndTime(String endTime) {
    EndTime = endTime;
  }
}
