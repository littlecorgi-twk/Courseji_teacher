package com.littlecorgi.middle.ui.teacher;

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

import java.util.List;

public class EndTimeActivity extends BaseActivity {
    private AppCompatTextView returnButton;
    private AppCompatTextView sureButton;
    private AppCompatTextView timeShow;
    private RecyclerView recyclerView;
    private WheelView wheelView_day;
    private WheelView wheelView_hour;
    private WheelView wheelView_minute;

    private List<String> timeList;
    private long duration;
    private static passedDataHelp.passedEndTime passEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_teacher_setendtime);
        initView();
    }
    private void initView() {
        initFind();
        initRecyclerView();
        initWheelView();
        initClick();
    }
    private void initFind() {
        returnButton = findViewById(R.id.endTime_returnButton);
        sureButton = findViewById(R.id.endTime_sureButton);
        timeShow = findViewById(R.id.endTime_TimeShow);
        recyclerView = findViewById(R.id.endTime_RecyclerView);
        wheelView_day = findViewById(R.id.wheelView_day);
        wheelView_hour = findViewById(R.id.wheelView_hour);
        wheelView_minute = findViewById(R.id.wheelView_minute);
    }
    private void initRecyclerView() {
        timeList = SetTimeHelp.getDurationList();
        StartTimeAdapt adapt = new StartTimeAdapt(R.layout.middle_teacher_settime_item, timeList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(manager);
        adapt.addChildClickViewIds(R.id.setTime_itemText);
        adapt.setOnItemChildClickListener((adapter, view, position) -> {
            timeShow.setText(timeList.get(position));
            duration = SetTimeHelp.getDurationMillisecondList(position);
        });
    }
    private void initWheelView() {
        initWheelViewDay();
        initWheelViewHour();
        initWheelViewMinute();
    }

    private void initWheelViewDay() {
        List<String> dayList = SetTimeHelp.getTimeDay();
        wheelView_day.setAdapter(new ArrayWheelAdapter<>(dayList));
        wheelView_day.setCurrentItem(0);
        wheelView_day.setCyclic(true);
        wheelView_day.setTextSize(24);
        wheelView_day.setLineSpacingMultiplier(2.0f);
        wheelView_day.setOnItemSelectedListener(index -> setTimeShow());
    }

    private void initWheelViewHour() {
        List<String> hourList = SetTimeHelp.getTimeHour();
        wheelView_hour.setAdapter(new ArrayWheelAdapter<>(hourList));
        wheelView_hour.setCurrentItem(0);                           //设置初始值
        wheelView_hour.setTextSize(24);                              //字体大小
        wheelView_hour.setLineSpacingMultiplier(2.0f);               //间隔宽
        wheelView_hour.setOnItemSelectedListener(index -> setTimeShow());
    }
    private void initWheelViewMinute() {
        List<String> minutesList = SetTimeHelp.getTimeMinutes();
        wheelView_minute.setAdapter(new ArrayWheelAdapter<>(minutesList));
        wheelView_minute.setCurrentItem(0);
        wheelView_minute.setTextSize(24);
        wheelView_minute.setLineSpacingMultiplier(2.0f);
        wheelView_minute.setOnItemSelectedListener(index -> setTimeShow());
    }
    private void setTimeShow() {
        int po_day = wheelView_day.getCurrentItem();
        int po_hour = wheelView_hour.getCurrentItem();
        int po_minute = wheelView_minute.getCurrentItem();
        String title =  "";
        if(po_day!=0){
            title+=po_day+"天";
        }
        if(po_hour!=0){
            title+=po_hour+"小时";
        }
        if(po_minute!=0){
            title+=po_minute+"分钟";
        }
        timeShow.setText(title);
        duration = po_day*1000*60*60*24 + po_hour*1000*60*60+po_minute*1000*60;

    }
    private void initClick() {
        returnButton.setOnClickListener(v -> finish());
        sureButton.setOnClickListener(v -> {
           passEndTime.passed(timeShow.getText().toString(),duration);
           finish();
        });
    }
    public static void StartEndTimeActivity(Context context, passedDataHelp.passedEndTime passedTime) {
        Intent intent = new Intent(context, EndTimeActivity.class);
        passEndTime = passedTime;
        context.startActivity(intent);
    }



}