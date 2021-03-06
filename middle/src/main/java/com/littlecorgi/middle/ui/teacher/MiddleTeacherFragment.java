package com.littlecorgi.middle.ui.teacher;

import static com.littlecorgi.middle.ui.teacher.LocationActivity.startLocationActivity;
import static com.littlecorgi.middle.ui.teacher.SetTitleActivity.startSetTitle;

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
import com.littlecorgi.middle.logic.dao.PassedDataHelp;
import com.littlecorgi.middle.logic.dao.PickerViewHelp;
import com.littlecorgi.middle.logic.dao.SetTimeHelp;
import com.littlecorgi.middle.logic.dao.Tool;
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

/**
 * 教师Fragment。 未完成：1.名单选择的数据，需要从我的里面获取 2.传给服务器的是班级的代码 2.发起签到传到服务器
 */
public class MiddleTeacherFragment extends Fragment {

    private View mView;
    private AppCompatTextView mReturnButton;
    private AppCompatImageView mTeacherBg;
    private AppCompatTextView mHistory;

    private ConstraintLayout mTeacherTheme;
    private AppCompatTextView mTeacherThemeText;

    private ConstraintLayout mTeacherTitle;
    private AppCompatTextView mTeacherTitleText;

    private ConstraintLayout mTeacherClass;
    private AppCompatTextView mTeacherClassText;

    private ConstraintLayout mTeacherLabel;
    private AppCompatTextView mTeacherLabelText;

    private ConstraintLayout mTeacherLocation;
    private AppCompatTextView mTeacherLocationText;

    private ConstraintLayout mTeacherEndTimeLineaLayout;
    private AppCompatTextView mTeacherEndTime;

    private ConstraintLayout mTeacherStartTimeLineaLayout;
    private AppCompatTextView mTeacherStartTime;

    private AppCompatButton mTeacherStartSignButton;

    private Dialog mClassDialog;
    private Dialog mThemeDialog;

    private boolean mIsCustom = false; // 判断是自定义时间还是点击按钮获取
    private long mStartTimeMilliseconds;
    private long mEndTimeMilliseconds;
    private int mPosition;
    private String mLat; // 纬度
    private String mIng; // 经度

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.middle_teacharfragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        mReturnButton = mView.findViewById(R.id.middle_Teacher_returnButton);
        mTeacherBg = mView.findViewById(R.id.middle_Teacher_bg);
        mHistory = mView.findViewById(R.id.middle_Teacher_History);

        mTeacherTheme = mView.findViewById(R.id.middle_TeacherTheme);
        mTeacherThemeText = mView.findViewById(R.id.middle_TeacherThemeText);

        mTeacherTitle = mView.findViewById(R.id.middle_TeacherTitle);
        mTeacherTitleText = mView.findViewById(R.id.middle_TeacherTitleText);

        mTeacherClass = mView.findViewById(R.id.middle_TeacherClass);
        mTeacherClassText = mView.findViewById(R.id.middle_TeacherClassText);

        mTeacherLabel = mView.findViewById(R.id.middle_TeacherLabel);
        mTeacherLabelText = mView.findViewById(R.id.middle_TeacherLabelText);

        mTeacherLocation = mView.findViewById(R.id.middle_TeacherLocation);
        mTeacherLocationText = mView.findViewById(R.id.middle_TeacherLocationText);

        mTeacherEndTimeLineaLayout = mView.findViewById(R.id.middle_TeacherEndTimeLineaLayout);
        mTeacherEndTime = mView.findViewById(R.id.middle_endTime);

        mTeacherStartTimeLineaLayout = mView.findViewById(R.id.middle_TeacherStartTimeLineaLayout);
        mTeacherStartTime = mView.findViewById(R.id.middle_TeacherStartTime);

        mTeacherStartSignButton = mView.findViewById(R.id.middle_TeacherStartSignButton);
    }

    private void initTheme() {
        View themeBtw = View.inflate(getContext(), R.layout.middle_teacher_settheme_btw, null);
        WheelView wheelView = themeBtw.findViewById(R.id.theme_blw_wheelView);
        List<String> list = Tool.getThemeList();
        wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        wheelView.setCurrentItem(0);
        mTeacherTheme.setOnClickListener(
                v -> {
                    if (mThemeDialog != null) {
                        mThemeDialog.show();
                    } else {
                        mThemeDialog = BTWHelp.dialogBtw(themeBtw, getContext());
                    }
                });
        AppCompatButton cancel = themeBtw.findViewById(R.id.theme_blw_cancelButton);
        AppCompatButton sure = themeBtw.findViewById(R.id.theme_blw_sureButton);
        cancel.setOnClickListener(v -> mThemeDialog.dismiss());
        sure.setOnClickListener(
                v -> {
                    mTeacherThemeText.setText(list.get(wheelView.getCurrentItem()));
                    mThemeDialog.dismiss();
                });
    }

    private void initTitle() {
        mTeacherTitle.setOnClickListener(
                v -> startSetTitle(getActivity(), title -> mTeacherTitleText.setText(title)));
    }

    private void initClass() {

        View classBtw = View.inflate(getContext(), R.layout.middle_teacher_showclass_btw, null);
        WheelView wheelView = classBtw.findViewById(R.id.class_blw_wheelView);
        List<String> list = PickerViewHelp.getClassList();
        wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        wheelView.setCurrentItem(0);
        mTeacherClass.setOnClickListener(
                v -> {
                    if (mClassDialog != null) {
                        mClassDialog.show();
                    } else {
                        mClassDialog = BTWHelp.dialogBtw(classBtw, getContext());
                    }
                });
        AppCompatButton cancel = classBtw.findViewById(R.id.class_blw_cancelButton);
        AppCompatButton sure = classBtw.findViewById(R.id.class_blw_sureButton);
        cancel.setOnClickListener(v -> mClassDialog.dismiss());
        sure.setOnClickListener(
                v -> {
                    mTeacherClassText.setText(list.get(wheelView.getCurrentItem()));
                    mClassDialog.dismiss();
                });
    }

    private void initLabel() {
        // 目前只支持一种签到类型，定位+人脸识别
        mTeacherLabel.setOnClickListener(
                v -> Toast.makeText(getContext(), "目前仅支持这种签到形式", Toast.LENGTH_LONG).show());
        mTeacherLabelText.setText("人脸识别 定位签到");
    }

    private void initLocation() {
        mTeacherLocation.setOnClickListener(v ->
                startLocationActivity(
                        getActivity(),
                        (placeName, lat, ing) -> {
                            mTeacherLocationText.setText(placeName);
                            mLat = lat;
                            mIng = ing;
                        }));
    }

    private void initStartTime() {
        mTeacherStartTimeLineaLayout.setOnClickListener(v -> startTimeActivity());
    }

    private void startTimeActivity() {
        StartTimeActivity.startStartTimeActivity(
                getContext(),
                new PassedDataHelp.PassedStartTime() {
                    @Override
                    public void noCustomPassed(String startTime, int position) {
                        mTeacherStartTime.setText(startTime);
                        mPosition = position;
                        mIsCustom = false;
                    }

                    @Override
                    public void customPassed(String startTime, long milliseconds) {
                        mTeacherStartTime.setText(startTime);
                        mIsCustom = true;
                        mStartTimeMilliseconds = milliseconds;
                    }
                });
    }

    private void initEndTime() {
        mTeacherEndTimeLineaLayout.setOnClickListener(v ->
                EndTimeActivity.startEndTimeActivity(
                        getContext(),
                        (endTime1, duration) -> {
                            mTeacherEndTime.setText(endTime1);
                            mEndTimeMilliseconds = duration;
                        }));
    }

    private void initData() {
        initImage();

        // 初始化开始时间为现在
        mTeacherStartTime.setText("现在");
        mPosition = 0;
        // 初始化持续时间为5分钟：
        mTeacherEndTime.setText("五分钟");
        mEndTimeMilliseconds = 1000 * 60 * 5;
    }

    private void initImage() {
    /*
       设置背景？TeacherBg
    */
    }

    private void initClick() {
        mTeacherStartSignButton.setOnClickListener(v -> sendSign());
        mReturnButton.setOnClickListener(v -> requireActivity().finish());
        mHistory.setOnClickListener(
                v -> {
                    // 跳转到另一组件
                });
    }

    private void sendSign() {
        // 发送网络请求，发起签到
        if (mTeacherThemeText.getText() == "") {
            Toast.makeText(getContext(), "签到主题不能为空", Toast.LENGTH_LONG).show();
        } else if (mTeacherClassText.getText() == "") {
            Toast.makeText(getContext(), "签到名单不能为空", Toast.LENGTH_LONG).show();
        } else if (mTeacherLocationText.getText() == "") {
            Toast.makeText(getContext(), "请设置位置", Toast.LENGTH_LONG).show();
        } else if (mIsCustom && mStartTimeMilliseconds < new Date().getTime()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
            dialog.setMessage("您刚才设置的开始时间已经过期了"); // 设置内容
            dialog.setCancelable(false); // 设置不可用Back键关闭对话框
            // 设置确定按钮的点击事件
            dialog.setPositiveButton("重新设置", (dialogInterface, i) -> startTimeActivity());
            dialog.setNegativeButton(
                    "从当前时间开始",
                    (dialogInterface, i) -> {
                        mStartTimeMilliseconds = new Date().getTime();
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
        map.put("Theme", mTeacherThemeText.getText().toString());
        map.put("Title", mTeacherClassText.getText().toString());
        map.put("Label", Tool.SFaceLocation);
        map.put("ClassName", "111"); // 需要修改
        if (mIsCustom) {
            map.put("StartTime", simpleDateFormat.format(mStartTimeMilliseconds));
            map.put("EndTime",
                    simpleDateFormat.format(mStartTimeMilliseconds + mEndTimeMilliseconds));
        } else {
            map.put("StartTime",
                    simpleDateFormat.format(SetTimeHelp.getTimeMillisecondList(mPosition)));
            map.put(
                    "EndTime",
                    simpleDateFormat.format(
                            SetTimeHelp.getTimeMillisecondList(mPosition) + mEndTimeMilliseconds));
        }
        map.put("Lat", mLat);
        map.put("Ing", mIng);
        Call<ResponseBody> call = RetrofitHelp.signInRetrofit(map);
        call.enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(
                            @NotNull Call<ResponseBody> call,
                            @NotNull Response<ResponseBody> response) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                        dialog.setMessage("发送成功"); // 设置内容
                        dialog.setCancelable(true); // 设置不可用Back键关闭对话框
                        // 设置确定按钮的点击事件
                        dialog.setPositiveButton("退出",
                                (dialogInterface, i) -> requireActivity().finish());
                        // 设置取消按钮的点击事件
                        dialog.setNegativeButton(
                                "查看签到结果",
                                (dialogInterface, i) -> {
                                    // 跳转到签到统计界面
                                    requireActivity().finish();
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
