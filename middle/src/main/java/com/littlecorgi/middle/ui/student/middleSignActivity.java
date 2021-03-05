package com.littlecorgi.middle.ui.student;

import static com.littlecorgi.middle.logic.dao.AndPermissionHelp.andPermission;
import static com.littlecorgi.middle.logic.dao.Tool.FaceRecognition;
import static com.littlecorgi.middle.logic.dao.Tool.SBlueTooth;
import static com.littlecorgi.middle.logic.dao.Tool.SFaceLocation;
import static com.littlecorgi.middle.logic.dao.Tool.SFinish;
import static com.littlecorgi.middle.logic.dao.Tool.SLeave;
import static com.littlecorgi.middle.logic.dao.Tool.SLocation;
import static com.littlecorgi.middle.logic.dao.Tool.SNormal;
import static com.littlecorgi.middle.logic.dao.Tool.SOG;
import static com.littlecorgi.middle.logic.dao.Tool.STookPhoto;
import static com.littlecorgi.middle.logic.dao.Tool.SUnFinish;
import static com.littlecorgi.middle.logic.dao.WindowHelp.setWindowStatusBarColor;
import static com.littlecorgi.middle.logic.network.RetrofitHelp.postStudentSign;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.BaiDuMapService;
import com.littlecorgi.middle.logic.dao.GlideEngine;
import com.littlecorgi.middle.logic.dao.LocationService;
import com.littlecorgi.middle.logic.dao.PassedIngLat;
import com.littlecorgi.middle.logic.model.Sign;
import com.littlecorgi.middle.logic.model.SignResult;
import com.littlecorgi.middle.logic.network.MyLocationListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class middleSignActivity extends BaseActivity {

  /*
  这里本来是要根据签到方式的不同展示不同的签页面，把签到方式的页面分为四种，目前使用的是用地图做展示
  未完成的：
      人脸识别完成签到
   */
  private AppCompatTextView returnButton;

  private BaiDuMapService baiDuMapService;
  private LocationService locationService;
  private MapView mapView;
  private Sign sign;
  private int Label;
  private double mLat;
  private double mIng;
  private boolean isHaveMapView = false;
  private boolean isIn = false;
  private View lastView; // 被删除前的view

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.middle_sign);
    initView();
    initPermission();
    initData();
  }

  private void initView() {
    changeBarColor();
    initFind();
    initClick();
  }

  private void changeBarColor() {
    setWindowStatusBarColor(this, R.color.blue);
  }

  private void initData() {
    Intent intent = getIntent();
    sign = (Sign) intent.getSerializableExtra("sign");
    assert sign != null;
    int State = sign.getState();
    Label = sign.getLabel();
    /*
    根据签到状态的不同显示不同的页面
     */
    switch (State) {
      case SOG: // 进行中
        initOnGoing();
        break;
      case SUnFinish: // 未完成
        initUnFinish();
        break;
      case SLeave: // 请假的
        initSLeave();
        break;
      case SFinish: // 已完成
        initSFinish();
        break;
    }
  }

  private void initPermission() {
    andPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE);
  }

  private View addView(int layout) {
    View view = View.inflate(this, layout, null);
    ConstraintLayout.LayoutParams lp =
        new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
    this.addContentView(view, lp);
    return view;
  }

  private void initClick() {
    returnButton.setOnClickListener(v -> finish());
  }

  private void initFind() {
    returnButton = findViewById(R.id.middle_sign_returnButton);
  }

  private void initOnGoing() {
    /*
    目前仅考虑定位+人脸识别
     */
    switch (Label) {
      case SNormal:
        onGoingNormal();
        break;
      case FaceRecognition:
      case SBlueTooth:
      case STookPhoto:
      case SLocation:
        break;
      case SFaceLocation:
        onGoingFaceLocation();
    }
  }

  private void onGoingNormal() {}

  private void onGoingFaceLocation() {

    View view = addView(R.layout.middle_signongoing_face_location);
    lastView = view;
    AppCompatTextView Countdown = view.findViewById(R.id.OGFL_Countdown);
    mapView = view.findViewById(R.id.OGFL_MapView);
    AppCompatTextView Text = view.findViewById(R.id.OGFL_mapText);
    AppCompatButton sureButton = view.findViewById(R.id.OGFL_Button);

    // 倒计时：
    try {
      setCountdown(Countdown);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    // 设置地图
    setMapView(Text);

    sureButton.setOnClickListener(
        v -> {
          if (isIn) {
            setImage();
          } else {
            Toast.makeText(middleSignActivity.this, "请移步到指定范围内签到", Toast.LENGTH_LONG).show();
          }
        });
  }

  private void setImage() {
    /*
    打开相机
     */
    PictureSelector.create(this)
        .openCamera(PictureMimeType.ofImage())
        .imageEngine(GlideEngine.createGlideEngine())
        .isCompress(true) // 是否压缩 true or false
        .forResult(PictureConfig.REQUEST_CAMERA);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == PictureConfig.REQUEST_CAMERA) { // 结果回调
        String path;
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        if (Build.VERSION.SDK_INT >= 29) {
          path = selectList.get(0).getAndroidQToPath();
        } else {
          path = selectList.get(0).getPath();
        }
        response(path);
      }
    }
  }

  private void response(String path) {
    Call<SignResult> call = postStudentSign(new File(path));
    call.enqueue(
        new Callback<SignResult>() {
          @Override
          public void onResponse(
              @NotNull Call<SignResult> call, @NotNull Response<SignResult> response) {
            /*
            if (response.body().isState()) {
                Toast.makeText(middleSignActivity.this, "签到成功", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(middleSignActivity.this, "人脸识别失败", Toast.LENGTH_LONG).show();
            }
              */
            ((ViewGroup) lastView.getParent()).removeView(lastView); // 删除上个视图
            initSFinish();
          }

          @Override
          public void onFailure(@NotNull Call<SignResult> call, @NotNull Throwable t) {
            Toast.makeText(middleSignActivity.this, "请检查网络后重试", Toast.LENGTH_LONG).show();
          }
        });
  }

  private void setMapView(AppCompatTextView Text) {
    isHaveMapView = true;
    baiDuMapService = new BaiDuMapService(mapView.getMap());
    locationService = new LocationService(this);

    MyLocationListener myListener =
        new MyLocationListener(
            baiDuMapService,
            new PassedIngLat() {
              @Override
              public void location(String Lat, String Ing, String cty) {
                mLat = Double.parseDouble(Lat);
                mIng = Double.parseDouble(Ing);
                LatLng center =
                    new LatLng(
                        Double.parseDouble(sign.getLat()), Double.parseDouble(sign.getLng()));
                baiDuMapService.setCircle(center);
                LatLng mPoint = new LatLng(mLat, mIng);
                isIn = SpatialRelationUtil.isCircleContainsPoint(center, 50, mPoint);
                if (isIn) {
                  Text.setText("已在指定范围内");
                  Text.setTextColor(getResources().getColor(R.color.finish));
                } else {
                  Text.setText("未在指定范围内，请走到指定范围");
                  Text.setTextColor(getResources().getColor(R.color.warning));
                }
              }

              @Override
              public void floor(String Lat, String Ing, String address) {
                /*
                   获取室内定位
                */
              }

              @Override
              public void polLocation(List<Poi> list) {}
            });
    locationService.registerListener(myListener);
    locationService.start();
  }
  // 设置倒计时
  private void setCountdown(AppCompatTextView view) throws ParseException {

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    long endTime = Objects.requireNonNull(simpleDateFormat.parse(sign.getEndTime())).getTime();
    long nowTime = new Date().getTime();
    long millisUntilFinished = endTime - nowTime;
    // CountDownTimer 类实现倒计时功能
    CountDownTimer countDownTimer =
        new CountDownTimer(millisUntilFinished, 1000) {
          @SuppressLint("SetTextI18n")
          @Override
          public void onTick(long millisUntilFinished) {
            long days = millisUntilFinished / (1000 * 60 * 60 * 24);
            long hours = (millisUntilFinished - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes =
                (millisUntilFinished - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60))
                    / (1000 * 60);
            long second =
                (millisUntilFinished
                        - days * (1000 * 60 * 60 * 24)
                        - hours * (1000 * 60 * 60)
                        - minutes * 1000 * 60)
                    / (1000);
            String time = hours + ":" + minutes + ":" + second;
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = null;
            try {
              date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
              e.printStackTrace();
            }
            assert date != null;
            String Countdown = simpleDateFormat.format(date);
            if (days != 0) {
              view.setText(days + "天 " + Countdown);
            } else view.setText(Countdown);
          }

          @Override
          public void onFinish() {
            ((ViewGroup) lastView.getParent()).removeView(lastView); // 删除上个视图
            initUnFinish();
          }
        };
    countDownTimer.start();
  }

  private void initSLeave() {

    addView(R.layout.middle_sign_leave);
  }

  private void initUnFinish() {

    addView(R.layout.middle_signunfinish);
  }

  private void initSFinish() {
    /*
    根据签到标签的不同显示不同的签到完成页面
     */
    switch (Label) {
        // 最简单的额，只显示签到完成
      case SNormal:
      case FaceRecognition:
      case SBlueTooth:
      case SFaceLocation:
        finish_Normal();
        break;
        // 显示照片
      case STookPhoto:
        finish_Photo();
        break;
        // 显示地图
      case SLocation:
        finish_MapView();
        break;
    }
  }

  private void finish_MapView() {
    View view = addView(R.layout.middle_signfinish_location);
    AppCompatTextView SFLTime = view.findViewById(R.id.middle_details_SFLTime);
    // MapView SFLMap = view.findViewById(R.id.middle_details_SFLMap);
    SFLTime.setText(sign.getFinishTime());
    // 设置地图
    // setMap(SFLMap, sign.getLng(), sign.getLat());
  }

  private void finish_Photo() {
    View view = addView(R.layout.middle_signfinish_photo);
    AppCompatTextView SFTPTime = view.findViewById(R.id.middle_details_SFTPTime);
    AppCompatImageView signFinishImage = view.findViewById(R.id.middle_details_signFinishImage);
    SFTPTime.setText(sign.getFinishTime());
    signFinishImage.setImageResource(sign.getTakePhoto());
  }

  private void finish_Normal() {
    View view = addView(R.layout.middle_signfinish_bluetooth);
    AppCompatTextView SFBTime = view.findViewById(R.id.middle_details_SFBTime);
    SFBTime.setText(sign.getFinishTime());
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (isHaveMapView)
      // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
      mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (isHaveMapView)
      // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
      mapView.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (isHaveMapView) {
      // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
      baiDuMapService.setUNLocationEnabled();
      locationService.stop();
      mapView.onDestroy();
    }
  }

  public static void StartSign(Context context, Sign sign) {
    Intent intent = new Intent(context, middleSignActivity.class);
    intent.putExtra("sign", sign);
    context.startActivity(intent);
  }
}
