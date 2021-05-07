package com.littlecorgi.middle.ui.teacher;

import static com.littlecorgi.middle.ui.teacher.LocationActivity.startLocationActivity;
import static com.littlecorgi.middle.ui.teacher.SetTitleActivity.startSetTitle;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.littlecorgi.commonlib.AppViewModel;
import com.littlecorgi.commonlib.util.DialogUtil;
import com.littlecorgi.commonlib.util.UserSPConstant;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.AttendanceRepository;
import com.littlecorgi.middle.logic.ClassRepository;
import com.littlecorgi.middle.logic.dao.BTWHelp;
import com.littlecorgi.middle.logic.dao.PassedDataHelp;
import com.littlecorgi.middle.logic.dao.SetTimeHelp;
import com.littlecorgi.middle.logic.dao.Tool;
import com.littlecorgi.middle.logic.model.AllClassResponse;
import com.littlecorgi.middle.logic.model.AttendanceRequest;
import com.littlecorgi.middle.logic.model.AttendanceResponse;
import com.littlecorgi.middle.logic.model.ClassDetailBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 教师Fragment。 未完成：1.名单选择的数据，需要从我的里面获取 2.传给服务器的是班级的代码 2.发起签到传到服务器
 */
@Route(path = "/middle/fragment_middle_teacher")
public class MiddleTeacherFragment extends Fragment {

    // todo 待完善，当切换到此页面时重新加载数据

    private static final String TAG = "MiddleTeacherFragment";
    private View mView;
    private AppCompatImageView mTeacherBg;

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

    private WheelView mClassWheelView;

    private Dialog mClassDialog;
    private Dialog mThemeDialog;

    private boolean mIsCustom = false; // 判断是自定义时间还是点击按钮获取
    private long mStartTimeMilliseconds;
    private long mEndTimeMilliseconds;
    private int mPosition;
    private String mLat; // 纬度
    private String mIng; // 经度
    private int mSelectedClass;

    private AppViewModel mViewModel;
    private long mTeacherId;

    private final ArrayList<ClassDetailBean> mClassList = new ArrayList<>();
    private ArrayWheelAdapter<String> mClassAdapter;

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

        mViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        Log.d(TAG, "onViewCreated: " + mViewModel);
        mTeacherId = mViewModel.getTeacherId();

        initView();
        if (mTeacherId == -1) {
            Toast.makeText(requireContext(), "未登录或者数据错误", Toast.LENGTH_SHORT).show();
        } else {
            initData();
        }
    }

    private void initView() {
        initFind();
        initTheme();
        initTitle();
        initLabel();
        initLocation();
        initStartTime();
        initEndTime();
        initClick();
        if (mTeacherId != -1) {
            initClass();
        }
    }

    private void initFind() {
        mTeacherBg = mView.findViewById(R.id.middle_Teacher_bg);

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
        mTeacherTheme.setOnClickListener(v -> {
            if (mThemeDialog != null) {
                mThemeDialog.show();
            } else {
                mThemeDialog = BTWHelp.dialogBtw(themeBtw, getContext());
            }
        });
        AppCompatButton cancel = themeBtw.findViewById(R.id.theme_blw_cancelButton);
        AppCompatButton sure = themeBtw.findViewById(R.id.theme_blw_sureButton);
        cancel.setOnClickListener(v -> mThemeDialog.dismiss());
        sure.setOnClickListener(v -> {
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
        mClassWheelView = classBtw.findViewById(R.id.class_blw_wheelView);
        mClassAdapter = new ArrayWheelAdapter<>(new ArrayList<>());
        mClassWheelView.setAdapter(mClassAdapter);
        mClassWheelView.setCurrentItem(0);
        mTeacherClass.setOnClickListener(v -> {
            if (mClassDialog != null) {
                mClassDialog.show();
            } else {
                mClassDialog = BTWHelp.dialogBtw(classBtw, getContext());
            }
        });
        AppCompatButton cancel = classBtw.findViewById(R.id.class_blw_cancelButton);
        AppCompatButton sure = classBtw.findViewById(R.id.class_blw_sureButton);
        cancel.setOnClickListener(v -> mClassDialog.dismiss());
        sure.setOnClickListener(v -> {
            mSelectedClass = mClassWheelView.getCurrentItem();
            mTeacherClassText.setText(mClassList.get(mSelectedClass).getName());
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
                startLocationActivity(getActivity(), (placeName, lat, ing) -> {
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
                getContext(), new PassedDataHelp.PassedStartTime() {
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
                EndTimeActivity.startEndTimeActivity(getContext(), (endTime1, duration) -> {
                    mTeacherEndTime.setText(endTime1);
                    mEndTimeMilliseconds = duration;
                }));
    }

    private void initClick() {
        if (mTeacherId != -1) {
            mTeacherStartSignButton.setOnClickListener(v -> sendSign());
        } else {
            Toast.makeText(requireContext(), "用户未登录或者数据错误", Toast.LENGTH_SHORT).show();
        }
        // todo 跳转到签到历史记录
        // mHistory.setOnClickListener(v -> {
        // });
    }

    private void initData() {
        // 初始化开始时间为现在
        mTeacherStartTime.setText("现在");
        mPosition = 0;
        // 初始化持续时间为5分钟：
        mTeacherEndTime.setText("五分钟");
        mEndTimeMilliseconds = 1000 * 60 * 5;

        mTeacherId = requireContext()
                .getSharedPreferences(UserSPConstant.FILE_NAME, Context.MODE_PRIVATE)
                .getLong(UserSPConstant.TEACHER_USER_ID, 5L);

        if (mTeacherId != -1) {
            getClassFromInternet();
        }
    }

    private void getClassFromInternet() {
        Dialog dialog = DialogUtil.writeLoadingDialog(requireContext(), false, "获取数据中");
        dialog.show();
        dialog.setCancelable(false);
        ClassRepository.getAllClassFromTeacher(mTeacherId).enqueue(
                new Callback<AllClassResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AllClassResponse> call,
                                           @NonNull Response<AllClassResponse> response) {
                        dialog.cancel();
                        AllClassResponse allClassResponse = response.body();
                        Log.d(TAG, "onResponse: " + response.toString());
                        assert allClassResponse != null;
                        if (allClassResponse.getStatus() == 800) {
                            mClassList.clear();
                            mClassList.addAll(allClassResponse.getData());
                            ArrayList<String> classList = new ArrayList<>();
                            for (ClassDetailBean theClass : mClassList) {
                                classList.add(theClass.getName());
                            }
                            mClassAdapter = new ArrayWheelAdapter<>(classList);
                            mClassWheelView.setAdapter(mClassAdapter);
                        } else {
                            Log.d(TAG, "onResponse: 请求错误" + allClassResponse.getMsg());
                            Toast.makeText(requireContext(), "错误，" + allClassResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllClassResponse> call,
                                          @NonNull Throwable t) {
                        dialog.cancel();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
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
            dialog.setNegativeButton("从当前时间开始", (dialogInterface, i) -> {
                mStartTimeMilliseconds = new Date().getTime();
                response();
            });
            dialog.show();
        } else {
            response();
        }
    }

    private void response() {
        AttendanceRequest request = new AttendanceRequest();
        if (mIsCustom) {
            request.setStartTime(mStartTimeMilliseconds);
            request.setEndTime(mEndTimeMilliseconds);
        } else {
            request.setStartTime(SetTimeHelp.getTimeMillisecondList(mPosition));
            request.setEndTime(
                    SetTimeHelp.getTimeMillisecondList(mPosition) + mEndTimeMilliseconds);
        }
        request.setTitle(mTeacherThemeText.getText().toString());
        request.setDescription(mTeacherTitleText.getText().toString());
        request.setLatitude(Double.parseDouble(mLat));
        request.setLongitude(Double.parseDouble(mIng));
        request.setRadius(50);
        Log.d(TAG, "response: 发起的请求 AttendanceRequest= " + request.toString());

        Dialog dialog = DialogUtil.writeLoadingDialog(requireContext(), false, "获取数据中");
        dialog.show();
        dialog.setCancelable(false);
        AttendanceRepository.createAttendance(mClassList.get(mSelectedClass).getId(), request)
                .enqueue(new Callback<AttendanceResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AttendanceResponse> call,
                                           @NonNull Response<AttendanceResponse> response) {
                        dialog.cancel();
                        AttendanceResponse attendance = response.body();
                        Log.d(TAG, "onResponse: " + response.toString());
                        assert attendance != null;
                        if (attendance.getStatus() == 800) {
                            Toast.makeText(requireContext(), "创建成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onResponse: 请求错误" + attendance.getMsg());
                            Toast.makeText(requireContext(), "错误，" + attendance.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AttendanceResponse> call,
                                          @NonNull Throwable t) {
                        dialog.cancel();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
