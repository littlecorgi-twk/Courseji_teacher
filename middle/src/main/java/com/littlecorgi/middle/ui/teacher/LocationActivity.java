package com.littlecorgi.middle.ui.teacher;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.BaiDuBugService;
import com.littlecorgi.middle.logic.dao.BaiDuMapService;
import com.littlecorgi.middle.logic.dao.LocationService;
import com.littlecorgi.middle.logic.dao.PassedIngLat;
import com.littlecorgi.middle.logic.dao.passedDataHelp;
import com.littlecorgi.middle.logic.model.LocationShow;
import com.littlecorgi.middle.logic.network.MyLocationListener;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.logic.dao.AndPermissionHelp;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BaseActivity {

    private AppCompatTextView returnButton;
    private AppCompatEditText searchPlaceEdit;

    private RecyclerView recyclerView;
    private AppCompatTextView sureButton;
    private LocationService locationService;
    private MapView mMapView = null;

    private BaiDuMapService baiDuMapService;
    private static passedDataHelp.passedLocation passLocation;

    private int lastPosition = 0;           //记录上次有对号的下标

    private final List<LocationShow> locationShows = new ArrayList<>();     //Adapt对应的集合
    private LocationAdapt locationAdapt;
    private String ing;
    private String lat;
    private String mLat;
    private String mIng;
    private String address;
    private String city;
    private boolean isSearch = false;

    public LocationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_teacher_location_btw);
        initView();
        initData();
    }

    private void initData() {

        initEdit();
    }

    private void initView() {
        initFind();
        initPermission();
        initBaiDuApi();
        initClick();
        initRecyclerView();
    }
    private void initFind() {
        returnButton = findViewById(R.id.location_returnButton);
        mMapView = findViewById(R.id.bMapView);
        sureButton = findViewById(R.id.location_sureButton);
        searchPlaceEdit = findViewById(R.id.location_searchPlaceEdit);
        recyclerView = findViewById(R.id.location_recyclerView);

    }
    private void initPermission() {
        AndPermissionHelp.andPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
    }
    private void initBaiDuApi() {

        baiDuMapService = new BaiDuMapService(mMapView.getMap());
        locationService = new LocationService(this);
        /*
             获取室内定位
          */
        MyLocationListener myListener = new MyLocationListener(baiDuMapService, new PassedIngLat() {

            @Override
            public void location(String Lat, String Ing, String cty) {
                lat = Lat;
                ing = Ing;
                mLat = Lat;
                mIng = Ing;
                city = cty;
            }

            @Override
            public void floor(String Lat, String Ing, String address) {
                /*
                    获取室内定位
                 */
            }
            @Override
            public void polLocation(List<Poi> list) {
                locationShows.clear();
                address = list.get(0).getName();
                for (int i = 0; i < list.size(); i++) {
                    LocationShow locationShow = new LocationShow();
                    locationShow.setDistance(list.get(i).getRank() + "");
                    locationShow.setName(list.get(i).getName());
                    locationShow.setPlaceName(list.get(i).getAddr());
                    locationShow.setGone(i != 0);
                    locationShows.add(locationShow);
                }
                locationAdapt.notifyDataSetChanged();
            }
        });
        locationService.registerListener(myListener);
        locationService.start();
    }
    private void initClick() {
        returnButton.setOnClickListener(v -> finish());
        sureButton.setOnClickListener(v -> {
            passLocation.passed(address,lat,ing);
            finish();
        });
    }

    private void initEdit() {
        /*
        检索：
         */
        //mPoiSearch = PoiSearch.newInstance();

        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                /*
                poiService = new BaiDuPoiService(baiDuMapService);
                poiService.startSearch(s.toString(),city.substring(0,city.length()-1),10);
                 */
                if(!s.toString().equals("")){
                    isSearch = true;
                    BaiDuBugService baiDuBugService = new BaiDuBugService(list -> {
                        locationShows.clear();

                        switchLatIng(list.get(0).getKey(),list.get(0).getPt().latitude,list.get(0).getPt().longitude);

                        for(int i = 0; i < list.size(); i++){
                            if(!list.get(i).getAddress().equals("")){
                                LocationShow locationShow = new LocationShow();
                                //计算距离
                                int distance = (int)DistanceUtil. getDistance(list.get(i).getPt(),
                                        new LatLng(Double.parseDouble(mLat),Double.parseDouble(mIng)));
                                locationShow.setDistance(distance+"");
                                locationShow.setPlaceName(list.get(i).getAddress());
                                locationShow.setName(list.get(i).key);
                                locationShow.setGone(i != 0);
                                locationShow.setLat(list.get(i).getPt().latitude);
                                locationShow.setIng(list.get(i).getPt().longitude);
                                locationShows.add(locationShow);
                            }
                        }
                        locationAdapt.notifyDataSetChanged();
                    });
                    baiDuBugService.startBugSearch(city.substring(0,city.length()-1),s.toString());
                }
            }
        });
    }
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        locationAdapt = new LocationAdapt(R.layout.middle_teacher_location_item,locationShows);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(locationAdapt);

        locationAdapt.setOnItemClickListener((adapter, view, position) -> {
            locationShows.get(lastPosition).setGone(true);
            lastPosition = position;
            locationShows.get(position).setGone(false);
            locationAdapt.notifyDataSetChanged();
            address = locationShows.get(position).getName();
            if(isSearch){
                switchLatIng(locationShows.get(position).getName(),locationShows.get(position).getLat(),
                        locationShows.get(position).getIng());
            }
        });
    }

    private void switchLatIng(String address1,double lat1,double ing1) {

        address = address1;
        lat = lat1+"";
        ing = ing1+"";
        /*
          切换中心点
         */
        //添加点
        baiDuMapService.addMarker(new LatLng(lat1,ing1));
        //设置中心点
        baiDuMapService.setMapStatus(lat1,ing1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        baiDuMapService.setUNLocationEnabled();
        locationService.stop();
        mMapView.onDestroy();
    }

    public static void StartLocationActivity(Activity activity, passedDataHelp.passedLocation passedLocation){
        Intent intent = new Intent(activity, LocationActivity.class);
        passLocation = passedLocation;
        activity.startActivity(intent);
    }

}