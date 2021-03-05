package com.littlecorgi.attendance.Tools;

public class Time {
  private String time;
  private String proportion;
  private String class1;

  public Time(String time, String class1, String proportion) {
    this.time = time;
    this.proportion = proportion;
    this.class1 = class1;
  }

  public String getProportion() {
    return proportion;
  }

  public void setProportion(String proportion) {
    this.proportion = proportion;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getClass1() {
    return class1;
  }

  public void setClass1(String class1) {
    this.class1 = class1;
  }
}
