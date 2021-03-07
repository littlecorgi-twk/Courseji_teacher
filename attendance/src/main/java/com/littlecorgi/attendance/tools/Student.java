package com.littlecorgi.attendance.tools;

/**
 * 学生数据类
 */
public class Student {

    private String name;
    private int imageId;

    public Student(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
