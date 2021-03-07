package com.littlecorgi.my.logic.model;

/**
 * 修改信息的数据Bean
 */
public class MessageChange {

    private String myImagePath; // 图像

    private String gender; // 性别

    private String professional; // 专业

    private String describe; // 描述

    private String national; // 民族

    public String getMyImagePath() {
        return myImagePath;
    }

    public void setMyImagePath(String myImagePath) {
        this.myImagePath = myImagePath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }
}
