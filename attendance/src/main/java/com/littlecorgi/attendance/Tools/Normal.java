package com.littlecorgi.attendance.Tools;

public class Normal {
  private String lesson;
  private String teacher;
  private String time;

  public Normal(String lesson, String teacher, String time) {
    this.lesson = lesson;
    this.teacher = teacher;
    this.time = time;
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
}
