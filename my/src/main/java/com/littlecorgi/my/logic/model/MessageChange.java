package com.littlecorgi.my.logic.model;

public class MessageChange {

    private String MyImagePath; // 图像

    private String Gender; // 性别

    private String Professional; // 专业

    private String Describe; // 描述

    private String national; // 民族

    public String getMyImagePath() {
        return MyImagePath;
    }

    public void setMyImagePath(String myImagePath) {
        MyImagePath = myImagePath;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getProfessional() {
        return Professional;
    }

    public void setProfessional(String professional) {
        Professional = professional;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }
}
