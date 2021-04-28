package com.littlecorgi.middle.logic.dao;

import com.baidu.location.Poi;
import java.util.List;

/**
 * 百度地图结果的回调接口
 */
public interface PassedIngLat {

    void location(String lat, String ing, String city);

    void floor(String lat, String ing, String address);

    void polLocation(List<Poi> list);
}
