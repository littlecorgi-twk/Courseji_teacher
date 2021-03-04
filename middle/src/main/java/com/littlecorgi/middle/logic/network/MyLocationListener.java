package com.littlecorgi.middle.logic.network;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.littlecorgi.middle.logic.dao.BaiDuMapService;
import com.littlecorgi.middle.logic.dao.PassedIngLat;


public class MyLocationListener extends BDAbstractLocationListener {

    private final PassedIngLat passedLocation;
    private final BaiDuMapService baiDuMapService;

    public MyLocationListener(BaiDuMapService baiDuMapService, PassedIngLat passedLocation) {
        this.passedLocation = passedLocation;
        this.baiDuMapService = baiDuMapService;
    }
    @Override
    public void onReceiveLocation(BDLocation location){
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
        double latitude = location.getLatitude();    //获取纬度信息
        double longitude = location.getLongitude();    //获取经度信息

        String city = location.getCity();
        passedLocation.polLocation(location.getPoiList());
        passedLocation.location(String.valueOf(latitude), String.valueOf(longitude),city);

        if (location.getFloor() != null) {
            // 当前支持高精度室内定位
            String buildingName = location.getBuildingName();// 百度内部建筑物缩写
            String floor = location.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
            passedLocation.floor(String.valueOf(latitude), String.valueOf(longitude),buildingName+floor);
            //mLocationClient.startIndoorMode();//开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
        }
        baiDuMapService.setLocationData(location);
        //设置当前位置为中心点
        baiDuMapService.setMapStatus(latitude,longitude);
    }

}
