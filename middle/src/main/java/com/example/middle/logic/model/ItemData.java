package com.example.middle.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.List;

public class ItemData {
    /*
    这个类即是接收网络请求返回学生数据的类，也是RecyclerView对应的数据类
     */


    @SerializedName("allSignData")
    private List<AllSignData> allSignData;

    public List<AllSignData> getAllSignData() {
        return allSignData;
    }

    public void setAllSignData(List<AllSignData> allSignData) {
        this.allSignData = allSignData;
    }

    public static class AllSignData{

        private String StateTitle;      //文字状态
        private String LabelTitle;
        private int LeftColor;          //左边框的颜色
        private int myLabel;            //应该是myState写错了

        @SerializedName("Theme")
        private String Theme;           //主题
        @SerializedName("StartTime")
        private String StartTime;       //开始时间
        @SerializedName("EndTime")
        private String EndTime;         //结束时间
        @SerializedName("State")
        private int State;              //签到状态
        @SerializedName("Label")
        private int Label;              //标签
        @SerializedName("Name")
        private String Name;            //老师的名字
        @SerializedName("Occupational")
        private String Occupational;    //老师的职业
        @SerializedName("image")
        private File image;             //老师的图像
        @SerializedName("Title")
        private String Title;           //签到的内容
        @SerializedName("FinishTime")
        private String FinishTime;      //完成签到的时间
        @SerializedName("Lat")
        private String Lat;             //纬度
        @SerializedName("Ing")
        private String Ing;             //经度
        @SerializedName("signPhoto")
        private File signPhoto;         //如果是拍照签到需要返回图片

        public void setTheme(String theme) {
            Theme = theme;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public void setState(int state) {
            State = state;
        }

        public void setLabel(int label) {
            Label = label;
        }

        public void setName(String name) {
            Name = name;
        }

        public void setOccupational(String occupational) {
            Occupational = occupational;
        }

        public void setImage(File image) {
            this.image = image;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setFinishTime(String finishTime) {
            FinishTime = finishTime;
        }

        public void setLat(String lat) {
            Lat = lat;
        }

        public void setIng(String ing) {
            Ing = ing;
        }

        public void setSignPhoto(File signPhoto) {
            this.signPhoto = signPhoto;
        }

        public File getSignPhoto() {
            return signPhoto;
        }

        public String getTheme() {
            return Theme;
        }

        public String getStartTime() {
            return StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }
        public int getState() {
            return State;
        }
        public int getLabel() {
            return Label;
        }

        public String getName() {
            return Name;
        }

        public String getOccupational() {
            return Occupational;
        }

        public File getImage() {
            return image;
        }

        public String getTitle() {
            return Title;
        }

        public String getFinishTime() {
            return FinishTime;
        }

        public String getLat() {
            return Lat;
        }

        public String getIng() {
            return Ing;
        }
        public String getStateTitle() {
            return StateTitle;
        }

        public void setStateTitle(String stateTitle) {
            StateTitle = stateTitle;
        }

        public int getLeftColor() {
            return LeftColor;
        }

        public void setLeftColor(int leftColor) {
            LeftColor = leftColor;
        }

        public int getMyLabel() {
            return myLabel;
        }

        public void setMyLabel(int myLabel) {
            this.myLabel = myLabel;
        }
        public String getLabelTitle() {
            return LabelTitle;
        }

        public void setLabelTitle(String labelTitle) {
            LabelTitle = labelTitle;
        }
    }
}
