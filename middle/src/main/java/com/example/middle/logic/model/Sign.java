package com.example.middle.logic.model;

import java.io.Serializable;

public class Sign implements Serializable {

    private int State;
    private int Label;
    private int TakePhoto;
    private String FinishTime;
    private String EndTime;
    private String Lat;
    private String Lng;

    public int getState() { return State; }

    public void setState(int state) { State = state; }


    public int getLabel() {
        return Label;
    }

    public void setLabel(int label) {
        Label = label;
    }
    public int getTakePhoto() {
        return TakePhoto;
    }

    public void setTakePhoto(int takePhoto) {
        TakePhoto = takePhoto;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(String finishTime) {
        FinishTime = finishTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }
}
