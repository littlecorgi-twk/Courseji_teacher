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
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.AndPermissionHelp;
import com.littlecorgi.middle.logic.dao.BaiDuBugService;
import com.littlecorgi.middle.logic.dao.BaiDuMapService;
import com.littlecorgi.middle.logic.dao.LocationService;
import com.littlecorgi.middle.logic.dao.PassedDataHelp;
import com.littlecorgi.middle.logic.dao.PassedIngLat;
import com.littlecorgi.middle.logic.model.LocationShow;
import com.littlecorgi.middle.logic.network.MyLocationListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 定位Activity
 */
public class LocationActivity extends BaseActivity {

    private AppCompatTextView mReturnButton;
    private AppCompatEditText mSearchPlaceEdit;

    private RecyclerView mRecyclerView;
    private AppCompatTextView mSureButton;
    private LocationService mLocationService;
    private MapView mMapView = null;

    private BaiDuMapService mBaiDuMapService;
    private static PassedDataHelp.PassedLocation mPassLocation;

    private int mLastPosition = 0; // 记录上次有对号的下标

    private final List<LocationShow> mLocationShows = new ArrayList<>(); // Adapt对应的集合
    private LocationAdapt mLocationAdapt;
    private String ing;
    private String lat;
    private String mLat;
    private String mIng;
    private String mAddress;
    private String mCity;
    private boolean isSearch = false;

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
        initBaiDuApi();
        initClick();
        initRecyclerView();
    }

    private void initFind() {
        mReturnButton = findViewById(R.id.location_returnButton);
        mMapView = findViewById(R.id.bMapView);
        mSureButton = findViewById(R.id.location_sureButton);
        mSearchPlaceEdit = findViewById(R.id.location_searchPlaceEdit);
        mRecyclerView = findViewById(R.id.location_recyclerView);
    }

    private void initBaiDuApi() {
        mBaiDuMapService = new BaiDuMapService(mMapView.getMap());
        mLocationService = new LocationService(this);
        // 获取室内定位
        MyLocationListener myListener = new MyLocationListener(
                mBaiDuMapService,
                new PassedIngLat() {

                    @Override
                    public void location(String lat1, String ing1, String cty) {
                        lat = lat1;
                        ing = ing1;
                        mLat = lat1;
                        mIng = ing1;
                        mCity = cty;
                    }

                    @Override
                    public void floor(String lat, String ing, String address) {
                        // 获取室内定位
                    }

                    @Override
                    public void polLocation(List<Poi> list) {
                        mLocationShows.clear();
                        mAddress = list.get(0).getName();
                        for (int i = 0; i < list.size(); i++) {
                            LocationShow locationShow = new LocationShow();
                            locationShow.setDistance(list.get(i).getRank() + "");
                            locationShow.setName(list.get(i).getName());
                            locationShow.setPlaceName(list.get(i).getAddr());
                            locationShow.setGone(i != 0);
                            mLocationShows.add(locationShow);
                        }
                        mLocationAdapt.notifyDataSetChanged();
                    }
                });
        mLocationService.registerListener(myListener);
        mLocationService.start();
    }

    private void initClick() {
        mReturnButton.setOnClickListener(v -> finish());
        mSureButton.setOnClickListener(v -> {
            mPassLocation.passed(mAddress, lat, ing);
            finish();
        });
    }

    private void initEdit() {
        // 检索：

        // mPoiSearch = PoiSearch.newInstance();

        mSearchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // poiService = new BaiDuPoiService(baiDuMapService);
                // poiService.startSearch(s.toString(), city.substring(0, city.length() - 1), 10);

                if (!s.toString().equals("")) {
                    isSearch = true;
                    BaiDuBugService baiDuBugService = new BaiDuBugService(list -> {
                        mLocationShows.clear();

                        switchLatIng(
                                list.get(0).getKey(),
                                list.get(0).getPt().latitude,
                                list.get(0).getPt().longitude);

                        for (int i = 0; i < list.size(); i++) {
                            if (!list.get(i).getAddress().equals("")) {
                                LocationShow locationShow = new LocationShow();
                                // 计算距离
                                int distance = (int) DistanceUtil.getDistance(
                                        list.get(i).getPt(),
                                        new LatLng(
                                                Double.parseDouble(mLat),
                                                Double.parseDouble(mIng)
                                        ));
                                locationShow.setDistance(distance + "");
                                locationShow.setPlaceName(list.get(i).getAddress());
                                locationShow.setName(list.get(i).key);
                                locationShow.setGone(i != 0);
                                locationShow.setLat(list.get(i).getPt().latitude);
                                locationShow.setIng(list.get(i).getPt().longitude);
                                mLocationShows.add(locationShow);
                            }
                        }
                        mLocationAdapt.notifyDataSetChanged();
                    });
                    baiDuBugService
                            .startBugSearch(mCity.substring(0, mCity.length() - 1), s.toString());
                }
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        mLocationAdapt = new LocationAdapt(R.layout.middle_teacher_location_item, mLocationShows);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mLocationAdapt);

        mLocationAdapt.setOnItemClickListener((adapter, view, position) -> {
            mLocationShows.get(mLastPosition).setGone(true);
            mLastPosition = position;
            mLocationShows.get(position).setGone(false);
            mLocationAdapt.notifyDataSetChanged();
            mAddress = mLocationShows.get(position).getName();
            if (isSearch) {
                switchLatIng(
                        mLocationShows.get(position).getName(),
                        mLocationShows.get(position).getLat(),
                        mLocationShows.get(position).getIng());
            }
        });
    }

    private void switchLatIng(String address1, double lat1, double ing1) {
        mAddress = address1;
        lat = lat1 + "";
        ing = ing1 + "";

        // 切换中心点
        // 添加点
        mBaiDuMapService.addMarker(new LatLng(lat1, ing1));
        // 设置中心点
        mBaiDuMapService.setMapStatus(lat1, ing1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBaiDuMapService.setUNLocationEnabled();
        mLocationService.stop();
        mMapView.onDestroy();
    }

    /**
     * 跳转到LocationActivity
     *
     * @param activity       Activity
     * @param passedLocation 定位信息
     */
    public static void startLocationActivity(Activity activity,
                                             PassedDataHelp.PassedLocation passedLocation) {
        Intent intent = new Intent(activity, LocationActivity.class);
        mPassLocation = passedLocation;
        activity.startActivity(intent);
    }
}
