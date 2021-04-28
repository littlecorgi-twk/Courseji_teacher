package com.littlecorgi.middle.ui.teacher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.PassedDataHelp;
import com.littlecorgi.middle.logic.dao.SetTimeHelp;
import com.littlecorgi.middle.logic.dao.Tool;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 设置开始时间Activity
 */
public class StartTimeActivity extends BaseActivity {

    private AppCompatTextView mReturnButton;
    private AppCompatTextView mSureButton;
    private AppCompatTextView mTimeShow;
    private RecyclerView mRecyclerView;
    private WheelView mDd;
    private WheelView mHh;
    private WheelView mMm;
    private boolean mISCustom = false; // 判断是自定义时间还是点击按钮获取
    private List<String> mMinutesList;
    private List<String> mHourList;
    private List<String> mDayList;
    private List<String> mTimeList;
    private long mSTartTime;
    private int mPosition;
    private static PassedDataHelp.PassedStartTime mPassedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_teacher_setstarttime);
        initView();
    }

    private void initView() {
        initFind();
        initRecyclerView();
        initWheelView();
        initClick();
    }

    private void initClick() {
        mReturnButton.setOnClickListener(v -> finish());
        mSureButton.setOnClickListener(
                v -> {
                    if (mISCustom) {
                        mPassedTime.customPassed(mTimeShow.getText().toString(), mSTartTime);
                    } else {
                        mPassedTime.noCustomPassed(mTimeShow.getText().toString(), mPosition);
                    }
                    finish();
                });
    }

    private void initWheelView() {
        initDd();
        initHH();
        intiMm();
    }

    private void intiMm() {
        mMinutesList = SetTimeHelp.getTimeMinutes();
        mMm.setAdapter(new ArrayWheelAdapter<>(mMinutesList));
        mMm.setCurrentItem(SetTimeHelp.getMinutesIndex());
        mMm.setTextSize(24);
        mMm.setLineSpacingMultiplier(2.0f);
        mMm.setOnItemSelectedListener(
                index -> {
                    mISCustom = true;
                    setTimeShow();
                });
    }

    private void initHH() {
        mHourList = SetTimeHelp.getTimeHalfHour();
        mHh.setAdapter(new ArrayWheelAdapter<>(mHourList));
        mHh.setCurrentItem(SetTimeHelp.getHalfHourIndex() - 1); // 设置初始值
        mHh.setTextSize(24); // 字体大小
        mHh.setLineSpacingMultiplier(2.0f); // 间隔宽
        mHh.setOnItemSelectedListener(
                index -> {
                    mISCustom = true;
                    setTimeShow();
                });
    }

    private void initDd() {
        mDayList = SetTimeHelp.getTimePM();
        mDd.setAdapter(new ArrayWheelAdapter<>(mDayList));
        mDd.setCurrentItem(SetTimeHelp.getTimePMIndex());
        mDd.setCyclic(false);
        mDd.setTextSize(30);
        mDd.setLineSpacingMultiplier(2.0f);
        mDd.setOnItemSelectedListener(
                index -> {
                    mISCustom = true;
                    setTimeShow();
                });
    }

    @SuppressLint("SetTextI18n")
    private void setTimeShow() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a-hh-mm");
        long nowTime = new Date().getTime(); // 当前时间毫秒值
        long startTime = Tool.getZeroMilliseconds(nowTime); // 当前时间00点的毫秒值
        try {
            startTime += Objects.requireNonNull(
                    simpleDateFormat.parse(
                            mDayList.get(mDd.getCurrentItem())
                                    + "-"
                                    + mHourList.get(mHh.getCurrentItem())
                                    + "-"
                                    + mMinutesList.get(mMm.getCurrentItem()))
            ).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nowTime > startTime) {
            startTime += 1000 * 60 * 60 * 24;
        } else if (nowTime == startTime) {
            startTime += 1000 * 60 * 60 * 24 - 1000 * 60;
        }
        mSTartTime = startTime;
        long hour = (startTime - nowTime) / (1000 * 60 * 60);
        long minutes = (startTime - nowTime - hour * (1000 * 60 * 60)) / (1000 * 60);

        if (hour == 0 && minutes != 0) {
            mTimeShow.setText(minutes + "分钟后开始");
        } else if (hour == 0) {
            mTimeShow.setText("不到一分钟开始");
        } else {
            mTimeShow.setText(hour + "小时" + minutes + "分钟后开始");
        }
    }

    private void initFind() {
        mReturnButton = findViewById(R.id.startTime_returnButton);
        mSureButton = findViewById(R.id.startTime_sureButton);
        mTimeShow = findViewById(R.id.startTime_TimeShow);
        mRecyclerView = findViewById(R.id.startTime_RecyclerView);
        mDd = findViewById(R.id.wheelView_MM);
        mHh = findViewById(R.id.wheelView_HH);
        mMm = findViewById(R.id.wheelView_ss);
    }

    private void initRecyclerView() {
        mTimeList = SetTimeHelp.getTimeList();
        StartTimeAdapt adapt = new StartTimeAdapt(R.layout.middle_teacher_settime_item, mTimeList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecyclerView.setAdapter(adapt);
        mRecyclerView.setLayoutManager(manager);
        adapt.addChildClickViewIds(R.id.setTime_itemText);
        adapt.setOnItemChildClickListener(
                (adapter, view, position) -> {
                    mTimeShow.setText(mTimeList.get(position));
                    mISCustom = false;
                    mPosition = position;
                });
    }

    /**
     * 跳转到StartTimeActivity
     *
     * @param context         上下文
     * @param passedStartTime 开始时间
     */
    public static void startStartTimeActivity(
            Context context, PassedDataHelp.PassedStartTime passedStartTime) {
        Intent intent = new Intent(context, StartTimeActivity.class);
        mPassedTime = passedStartTime;
        context.startActivity(intent);
    }
}
