package com.littlecorgi.my.logic.model;

import java.io.Serializable;
import java.util.Map;

public class MyMessage implements Serializable {

    private String Picture;

    private int MyImage;            //图片

    private String imagePath;       //图像路径

    private String Name;            //名字

    private String Id;              //身份证

    private String Gender;          //性别

    private String Professional;    //专业

    private String Describe;        //描述

    private String national;        //民族

    private Map<Integer,String> MyClass;   //班级

    private String Version;         //版本号
    private String update;          //下载地址
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }



    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public int getMyImage() {
        return MyImage;
    }

    public void setMyImage(int myImage) {
        MyImage = myImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
