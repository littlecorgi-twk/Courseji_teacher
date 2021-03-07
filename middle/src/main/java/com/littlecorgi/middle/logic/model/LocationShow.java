package com.littlecorgi.middle.logic.model;

/**
 * 定位展示数据类
 */
public class LocationShow {

    private String placeName; // address
    private String name; // name
    private String distance;
    private boolean isGone;
    private double lat;
    private double ing;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getIng() {
        return ing;
    }

    public void setIng(double ing) {
        this.ing = ing;
    }

    public boolean isGone() {
        return isGone;
    }

    public void setGone(boolean gone) {
        isGone = gone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
