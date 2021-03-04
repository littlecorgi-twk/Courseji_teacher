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
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.passedDataHelp;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.logic.dao.SetTimeHelp;
import com.littlecorgi.middle.logic.dao.Tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StartTimeActivity extends BaseActivity {
    private AppCompatTextView returnButton;
    private AppCompatTextView sureButton;
    private AppCompatTextView timeShow;
    private RecyclerView recyclerView;
    private WheelView dd;
    private WheelView HH;
    private WheelView mm;
    private boolean ISCustom = false;            //判断是自定义时间还是点击按钮获取
    private List<String> minutesList;
    private List<String> hourList;
    private  List<String> dayList;
    private List<String> timeList;
    private long STartTime;
    private int Position;
    private static passedDataHelp.passedStartTime passedTime;
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
        returnButton.setOnClickListener(v -> finish());
        sureButton.setOnClickListener(v -> {
            if(ISCustom){
                passedTime.customPassed(timeShow.getText().toString(),STartTime);
            }else{
                passedTime.noCustomPassed(timeShow.getText().toString(),Position);
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
        minutesList = SetTimeHelp.getTimeMinutes();
        mm.setAdapter(new ArrayWheelAdapter<>(minutesList));
        mm.setCurrentItem(SetTimeHelp.getMinutesIndex());
        mm.setTextSize(24);
        mm.setLineSpacingMultiplier(2.0f);
        mm.setOnItemSelectedListener(index -> {
            ISCustom = true;
            setTimeShow();
        });
    }
    private void initHH() {
        hourList = SetTimeHelp.getTimeHalfHour();
        HH.setAdapter(new ArrayWheelAdapter<>(hourList));
        HH.setCurrentItem(SetTimeHelp.getHalfHourIndex()-1);          //设置初始值
        HH.setTextSize(24);                              //字体大小
        HH.setLineSpacingMultiplier(2.0f);               //间隔宽
        HH.setOnItemSelectedListener(index -> {
            ISCustom = true;
            setTimeShow();
        });
    }
    private void initDd() {
        dayList = SetTimeHelp.getTimePM();
        dd.setAdapter(new ArrayWheelAdapter<>(dayList));
        dd.setCurrentItem(SetTimeHelp.getTimePMIndex());
        dd.setCyclic(false);
        dd.setTextSize(30);
        dd.setLineSpacingMultiplier(2.0f);
        dd.setOnItemSelectedListener(index -> {
            ISCustom = true;
            setTimeShow();
        });
    }
    @SuppressLint("SetTextI18n")
    private void setTimeShow() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a-hh-mm");
        long nowTime = new Date().getTime();                    //当前时间毫秒值
        long startTime = Tool.getZeroMilliseconds(nowTime);          //当前时间00点的毫秒值
        try {
            startTime += Objects.requireNonNull(simpleDateFormat.parse
                    (dayList.get(dd.getCurrentItem()) + "-" +
                            hourList.get(HH.getCurrentItem()) + "-" +
                            minutesList.get(mm.getCurrentItem()))).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nowTime > startTime) {
            startTime += 1000 * 60 * 60 * 24;
        } else if (nowTime == startTime) {
            startTime += 1000 * 60 * 60 * 24 - 1000 * 60;
        }
        STartTime = startTime;
        long hour = (startTime - nowTime) / (1000 * 60 * 60);
        long minutes = (startTime - nowTime - hour * (1000 * 60 * 60)) / (1000 * 60);

        if (hour == 0 && minutes != 0) {
            timeShow.setText(minutes + "分钟后开始");
        } else if (hour == 0) {
            timeShow.setText("不到一分钟开始");
        } else {
            timeShow.setText(hour + "小时" + minutes + "分钟后开始");
        }
    }
    private void initFind() {
        returnButton = findViewById(R.id.startTime_returnButton);
        sureButton = findViewById(R.id.startTime_sureButton);
        timeShow = findViewById(R.id.startTime_TimeShow);
        recyclerView = findViewById(R.id.startTime_RecyclerView);
        dd = findViewById(R.id.wheelView_MM);
        HH = findViewById(R.id.wheelView_HH);
        mm = findViewById(R.id.wheelView_ss);
    }
    private void initRecyclerView() {
        timeList = SetTimeHelp.getTimeList();
        StartTimeAdapt adapt = new StartTimeAdapt(R.layout.middle_teacher_settime_item, timeList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(manager);
        adapt.addChildClickViewIds(R.id.setTime_itemText);
        adapt.setOnItemChildClickListener((adapter, view, position) -> {
            timeShow.setText(timeList.get(position));
            ISCustom = false;
            Position  = position;
        });
    }
    public static void StartStartTimeActivity(Context context, passedDataHelp.passedStartTime passedStartTime) {
        Intent intent = new Intent(context, StartTimeActivity.class);
        passedTime = passedStartTime;
        context.startActivity(intent);
    }
}