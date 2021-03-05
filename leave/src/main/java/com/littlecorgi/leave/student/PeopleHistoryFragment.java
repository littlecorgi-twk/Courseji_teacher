package com.littlecorgi.leave.student;

import static android.content.Context.VIBRATOR_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.littlecorgi.leave.R;
import java.util.ArrayList;
import java.util.List;

public class PeopleHistoryFragment extends Fragment {
  private Button buttonReturn;
  private Button textViewReturn;

  private TextView nameText;
  private TextView numberText;
  private TextView startTimeText;
  private TextView endTimeText;
  private TextView type1Text;
  private TextView type2Text;
  private TextView placeText;
  private TextView myPhoneText;
  private TextView otherPhoneText;
  private TextView reasonText;

  private String name;
  private String number;
  private String startTime;
  private String endTime;
  private String type1;
  private String type2;
  private String place;
  private String myPhone;
  private String otherPhone;
  private String reason;
  private ImageView locationImage;

  private Button xiaojiaButton;

  private TextView positionText;
  public LocationClient mLocationClient;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mLocationClient = new LocationClient(getActivity().getApplicationContext());
    mLocationClient.registerLocationListener(new MyLocationListener());
    View view = inflater.inflate(R.layout.leave_situation, container, false);
    positionText = (TextView) view.findViewById(R.id.location_text);
    List<String> permissionList = new ArrayList<>();
    if (ContextCompat.checkSelfPermission(
            getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }
    if (ContextCompat.checkSelfPermission(
            getActivity(), android.Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED) {
      permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
    }
    if (ContextCompat.checkSelfPermission(
            getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    if (!permissionList.isEmpty()) {
      String[] permission = permissionList.toArray(new String[permissionList.size()]);
      ActivityCompat.requestPermissions(getActivity(), permission, 1);
    }

    buttonReturn = (Button) view.findViewById(R.id.btn_return);
    textViewReturn = (Button) view.findViewById(R.id.tv_return);
    buttonReturn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
          }
        });
    textViewReturn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
          }
        });
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    nameText = (TextView) getActivity().findViewById(R.id.name_text);
    numberText = (TextView) getActivity().findViewById(R.id.number);
    startTimeText = (TextView) getActivity().findViewById(R.id.start_name_text);
    endTimeText = (TextView) getActivity().findViewById(R.id.end_time_text);
    type1Text = (TextView) getActivity().findViewById(R.id.type1_text);
    type2Text = (TextView) getActivity().findViewById(R.id.type2_text);
    placeText = (TextView) getActivity().findViewById(R.id.place_text);
    myPhoneText = (TextView) getActivity().findViewById(R.id.my_phone_text);
    otherPhoneText = (TextView) getActivity().findViewById(R.id.other_phone_text);
    reasonText = (TextView) getActivity().findViewById(R.id.reason_text);
    xiaojiaButton = (Button) getActivity().findViewById(R.id.xiaojia);
    locationImage = (ImageView) getActivity().findViewById(R.id.location_image);
    locationImage.setVisibility(View.GONE);

    SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
    name = pref.getString("name", "");
    type1 = pref.getString("type1", "");
    type2 = pref.getString("type2", "");
    startTime = pref.getString("startTime", "");
    endTime = pref.getString("endTime", "");
    place = pref.getString("place", "");
    myPhone = pref.getString("myPhone", "");
    otherPhone = pref.getString("otherPhone", "");
    reason = pref.getString("leaveSituation", "");

    nameText.setText(name);
    type1Text.setText(type1);
    type2Text.setText(type2);
    startTimeText.setText(startTime);
    endTimeText.setText(endTime);
    placeText.setText(place);
    myPhoneText.setText(myPhone);
    otherPhoneText.setText(otherPhone);
    reasonText.setText(reason);

    xiaojiaButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            xiaojiaButton.setText("已销假");
            xiaojiaButton.setBackgroundResource(R.drawable.button_shape2);
            Vibrator mVibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
            mVibrator.vibrate(500);
            locationImage.setVisibility(View.VISIBLE);
            requestLocation();
            Toast.makeText(getActivity(), "销假成功", Toast.LENGTH_SHORT).show();
          }
        });
  }

  private void requestLocation() {
    initLocation();
    mLocationClient.start();
  }

  private void initLocation() {
    LocationClientOption option = new LocationClientOption();
    option.setScanSpan(5000);
    option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
    option.setIsNeedAddress(true);
    mLocationClient.setLocOption(option);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mLocationClient.stop();
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0) {
          for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
              Toast.makeText(getActivity(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
              return;
            }
          }
          requestLocation();
        } else {
          Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
        }
        break;
      default:
    }
  }

  public class MyLocationListener implements BDLocationListener {
    @Override
    public void onReceiveLocation(final BDLocation location) {
      getActivity()
          .runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  StringBuilder currentPosition = new StringBuilder();
                  currentPosition.append(location.getCountry()).append(" ");
                  currentPosition.append(location.getProvince()).append(" ");
                  currentPosition.append(location.getCity()).append(" ");
                  currentPosition.append(location.getDistrict()).append("\n");
                  Log.d("location", currentPosition.toString());
                  positionText.setText("定位的地点");
                }
              });
    }
  }
}
