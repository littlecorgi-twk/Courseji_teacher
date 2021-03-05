package com.littlecorgi.middle.ui.teacher;

import static com.littlecorgi.middle.ui.teacher.LocationActivity.StartLocationActivity;
import static com.littlecorgi.middle.ui.teacher.SetTitleActivity.StartSetTitle;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.BTWHelp;
import com.littlecorgi.middle.logic.dao.PickerViewHelp;
import com.littlecorgi.middle.logic.dao.SetTimeHelp;
import com.littlecorgi.middle.logic.dao.Tool;
import com.littlecorgi.middle.logic.dao.passedDataHelp;
import com.littlecorgi.middle.logic.network.RetrofitHelp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    未完成：1.名单选择的数据，需要从我的里面获取
           2.传给服务器的是班级的代码
           2.发起签到传到服务器
*/

public class middleTeacherFragment extends Fragment {
  private View view;
  private AppCompatTextView returnButton;
  private AppCompatImageView TeacherBg;
  private AppCompatTextView History;

  private ConstraintLayout TeacherTheme;
  private AppCompatTextView TeacherThemeText;

  private ConstraintLayout TeacherTitle;
  private AppCompatTextView TeacherTitleText;

  private ConstraintLayout TeacherClass;
  private AppCompatTextView TeacherClassText;

  private ConstraintLayout TeacherLabel;
  private AppCompatTextView TeacherLabelText;

  private ConstraintLayout TeacherLocation;
  private AppCompatTextView TeacherLocationText;

  private ConstraintLayout TeacherEndTimeLineaLayout;
  private AppCompatTextView TeacherEndTime;

  private ConstraintLayout TeacherStartTimeLineaLayout;
  private AppCompatTextView TeacherStartTime;

  private AppCompatButton TeacherStartSignButton;

  private Dialog classDialog;
  private Dialog themeDialog;

  private boolean ISCustom = false; // 判断是自定义时间还是点击按钮获取
  private long startTimeMilliseconds;
  private long endTimeMilliseconds;
  private int Position;
  private String Lat; // 纬度
  private String Ing; // 经度

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.middle_teacharfragment, container, false);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    super.onActivityCreated(savedInstanceState);
    initView();
    initData();
  }

  private void initView() {
    initFind();
    initTheme();
    initTitle();
    initClass();
    initLabel();
    initLocation();
    initStartTime();
    initEndTime();
    initClick();
  }

  private void initFind() {
    returnButton = view.findViewById(R.id.middle_Teacher_returnButton);
    TeacherBg = view.findViewById(R.id.middle_Teacher_bg);
    History = view.findViewById(R.id.middle_Teacher_History);

    TeacherTheme = view.findViewById(R.id.middle_TeacherTheme);
    TeacherThemeText = view.findViewById(R.id.middle_TeacherThemeText);

    TeacherTitle = view.findViewById(R.id.middle_TeacherTitle);
    TeacherTitleText = view.findViewById(R.id.middle_TeacherTitleText);

    TeacherClass = view.findViewById(R.id.middle_TeacherClass);
    TeacherClassText = view.findViewById(R.id.middle_TeacherClassText);

    TeacherLabel = view.findViewById(R.id.middle_TeacherLabel);
    TeacherLabelText = view.findViewById(R.id.middle_TeacherLabelText);

    TeacherLocation = view.findViewById(R.id.middle_TeacherLocation);
    TeacherLocationText = view.findViewById(R.id.middle_TeacherLocationText);

    TeacherEndTimeLineaLayout = view.findViewById(R.id.middle_TeacherEndTimeLineaLayout);
    TeacherEndTime = view.findViewById(R.id.middle_endTime);

    TeacherStartTimeLineaLayout = view.findViewById(R.id.middle_TeacherStartTimeLineaLayout);
    TeacherStartTime = view.findViewById(R.id.middle_TeacherStartTime);

    TeacherStartSignButton = view.findViewById(R.id.middle_TeacherStartSignButton);
  }

  private void initTheme() {
    View themeBtw = View.inflate(getContext(), R.layout.middle_teacher_settheme_btw, null);
    WheelView wheelView = themeBtw.findViewById(R.id.theme_blw_wheelView);
    List<String> list = Tool.getThemeList();
    wheelView.setAdapter(new ArrayWheelAdapter<>(list));
    wheelView.setCurrentItem(0);
    TeacherTheme.setOnClickListener(
        v -> {
          if (themeDialog != null) {
            themeDialog.show();
          } else {
            themeDialog = BTWHelp.dialogBtw(themeBtw, getContext());
          }
        });
    AppCompatButton cancel = themeBtw.findViewById(R.id.theme_blw_cancelButton);
    AppCompatButton sure = themeBtw.findViewById(R.id.theme_blw_sureButton);
    cancel.setOnClickListener(v -> themeDialog.dismiss());
    sure.setOnClickListener(
        v -> {
          TeacherThemeText.setText(list.get(wheelView.getCurrentItem()));
          themeDialog.dismiss();
        });
  }

  private void initTitle() {
    TeacherTitle.setOnClickListener(
        v -> StartSetTitle(getActivity(), title -> TeacherTitleText.setText(title)));
  }

  private void initClass() {

    View classBtw = View.inflate(getContext(), R.layout.middle_teacher_showclass_btw, null);
    WheelView wheelView = classBtw.findViewById(R.id.class_blw_wheelView);
    List<String> list = PickerViewHelp.getClassList();
    wheelView.setAdapter(new ArrayWheelAdapter<>(list));
    wheelView.setCurrentItem(0);
    TeacherClass.setOnClickListener(
        v -> {
          if (classDialog != null) {
            classDialog.show();
          } else {
            classDialog = BTWHelp.dialogBtw(classBtw, getContext());
          }
        });
    AppCompatButton cancel = classBtw.findViewById(R.id.class_blw_cancelButton);
    AppCompatButton sure = classBtw.findViewById(R.id.class_blw_sureButton);
    cancel.setOnClickListener(v -> classDialog.dismiss());
    sure.setOnClickListener(
        v -> {
          TeacherClassText.setText(list.get(wheelView.getCurrentItem()));
          classDialog.dismiss();
        });
  }

  private void initLabel() {
    /*
    目前只支持一种签到类型，定位+人脸识别
     */
    TeacherLabel.setOnClickListener(
        v -> Toast.makeText(getContext(), "目前仅支持这种签到形式", Toast.LENGTH_LONG).show());
    TeacherLabelText.setText("人脸识别 定位签到");
  }

  private void initLocation() {
    TeacherLocation.setOnClickListener(
        v ->
            StartLocationActivity(
                getActivity(),
                (placeName, lat, ing) -> {
                  TeacherLocationText.setText(placeName);
                  Lat = lat;
                  Ing = ing;
                }));
  }

  private void initStartTime() {
    TeacherStartTimeLineaLayout.setOnClickListener(v -> startTimeActivity());
  }

  private void startTimeActivity() {
    StartTimeActivity.StartStartTimeActivity(
        getContext(),
        new passedDataHelp.passedStartTime() {
          @Override
          public void noCustomPassed(String startTime, int position) {
            TeacherStartTime.setText(startTime);
            Position = position;
            ISCustom = false;
          }

          @Override
          public void customPassed(String startTime, long milliseconds) {
            TeacherStartTime.setText(startTime);
            ISCustom = true;
            startTimeMilliseconds = milliseconds;
          }
        });
  }

  private void initEndTime() {
    TeacherEndTimeLineaLayout.setOnClickListener(
        v ->
            EndTimeActivity.StartEndTimeActivity(
                getContext(),
                (endTime1, duration) -> {
                  TeacherEndTime.setText(endTime1);
                  endTimeMilliseconds = duration;
                }));
  }

  private void initData() {
    initImage();

    // 初始化开始时间为现在
    TeacherStartTime.setText("现在");
    Position = 0;
    // 初始化持续时间为5分钟：
    TeacherEndTime.setText("五分钟");
    endTimeMilliseconds = 1000 * 60 * 5;
  }

  private void initImage() {
    /*
       设置背景？TeacherBg
    */
  }

  private void initClick() {
    TeacherStartSignButton.setOnClickListener(v -> sendSign());
    returnButton.setOnClickListener(v -> getActivity().finish());
    History.setOnClickListener(
        v -> {
          // 跳转到另一组件
        });
  }

  private void sendSign() {
    /*
    发送网络请求，发起签到
     */
    if (TeacherThemeText.getText() == "") {
      Toast.makeText(getContext(), "签到主题不能为空", Toast.LENGTH_LONG).show();
    } else if (TeacherClassText.getText() == "") {
      Toast.makeText(getContext(), "签到名单不能为空", Toast.LENGTH_LONG).show();
    } else if (TeacherLocationText.getText() == "") {
      Toast.makeText(getContext(), "请设置位置", Toast.LENGTH_LONG).show();
    } else if (ISCustom && startTimeMilliseconds < new Date().getTime()) {
      AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
      dialog.setMessage("您刚才设置的开始时间已经过期了"); // 设置内容
      dialog.setCancelable(false); // 设置不可用Back键关闭对话框
      // 设置确定按钮的点击事件
      dialog.setPositiveButton("重新设置", (dialogInterface, i) -> startTimeActivity());
      dialog.setNegativeButton(
          "从当前时间开始",
          (dialogInterface, i) -> {
            startTimeMilliseconds = new Date().getTime();
            response();
          });
      dialog.show();
    } else {
      response();
    }
  }

  private void response() {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Map<String, Object> map = new HashMap<>();
    map.put("Theme", TeacherThemeText.getText().toString());
    map.put("Title", TeacherTitleText.getText().toString());
    map.put("Label", Tool.SFaceLocation);
    map.put("ClassName", "111"); // 需要修改
    if (ISCustom) {
      map.put("StartTime", simpleDateFormat.format(startTimeMilliseconds));
      map.put("EndTime", simpleDateFormat.format(startTimeMilliseconds + endTimeMilliseconds));
    } else {
      map.put("StartTime", simpleDateFormat.format(SetTimeHelp.getTimeMillisecondList(Position)));
      map.put(
          "EndTime",
          simpleDateFormat.format(
              SetTimeHelp.getTimeMillisecondList(Position) + endTimeMilliseconds));
    }
    map.put("Lat", Lat);
    map.put("Ing", Ing);
    Call<ResponseBody> call = RetrofitHelp.aSignInRetrofit(map);
    call.enqueue(
        new Callback<ResponseBody>() {
          @Override
          public void onResponse(
              @NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("发送成功"); // 设置内容
            dialog.setCancelable(true); // 设置不可用Back键关闭对话框
            // 设置确定按钮的点击事件
            dialog.setPositiveButton("退出", (dialogInterface, i) -> getActivity().finish());
            // 设置取消按钮的点击事件
            dialog.setNegativeButton(
                "查看签到结果",
                (dialogInterface, i) -> {
                  // 跳转到签到统计界面
                  getActivity().finish();
                });
            dialog.show();
          }

          @Override
          public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
            Toast.makeText(getContext(), "发送失败，过会在试吧", Toast.LENGTH_LONG).show();
          }
        });
  }
}
