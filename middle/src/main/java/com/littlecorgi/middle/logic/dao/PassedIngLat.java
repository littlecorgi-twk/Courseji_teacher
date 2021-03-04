package com.littlecorgi.middle.logic.dao;

import com.baidu.location.Poi;

import java.util.List;

public interface PassedIngLat {
     /*
     百度地图结果的回调接口
      */
     void location(String Lat, String Ing,String city);
     void floor(String Lat,String Ing,String address);
     void polLocation(List<Poi> list);
}
