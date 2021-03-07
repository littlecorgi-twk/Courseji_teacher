package com.littlecorgi.middle.ui.teacher;

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
import java.util.List;

/**
 * 设置结束时间Activity
 */
public class EndTimeActivity extends BaseActivity {

    private AppCompatTextView mReturnButton;
    private AppCompatTextView mSureButton;
    private AppCompatTextView mTimeShow;
    private RecyclerView mRecyclerView;
    private WheelView mWheelViewDay;
    private WheelView mWheelViewHour;
    private WheelView mWheelViewMinute;

    private List<String> mTimeList;
    private long mDuration;
    private static PassedDataHelp.PassedEndTime mPassEndTime;

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
        mReturnButton = findViewById(R.id.endTime_returnButton);
        mSureButton = findViewById(R.id.endTime_sureButton);
        mTimeShow = findViewById(R.id.endTime_TimeShow);
        mRecyclerView = findViewById(R.id.endTime_RecyclerView);
        mWheelViewDay = findViewById(R.id.wheelView_day);
        mWheelViewHour = findViewById(R.id.wheelView_hour);
        mWheelViewMinute = findViewById(R.id.wheelView_minute);
    }

    private void initRecyclerView() {
        mTimeList = SetTimeHelp.getDurationList();
        StartTimeAdapt adapt = new StartTimeAdapt(R.layout.middle_teacher_settime_item, mTimeList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecyclerView.setAdapter(adapt);
        mRecyclerView.setLayoutManager(manager);
        adapt.addChildClickViewIds(R.id.setTime_itemText);
        adapt.setOnItemChildClickListener((adapter, view, position) -> {
                    mTimeShow.setText(mTimeList.get(position));
                    mDuration = SetTimeHelp.getDurationMillisecondList(position);
                }
        );
    }

    private void initWheelView() {
        initWheelViewDay();
        initWheelViewHour();
        initWheelViewMinute();
    }

    private void initWheelViewDay() {
        List<String> dayList = SetTimeHelp.getTimeDay();
        mWheelViewDay.setAdapter(new ArrayWheelAdapter<>(dayList));
        mWheelViewDay.setCurrentItem(0);
        mWheelViewDay.setCyclic(true);
        mWheelViewDay.setTextSize(24);
        mWheelViewDay.setLineSpacingMultiplier(2.0f);
        mWheelViewDay.setOnItemSelectedListener(index -> setTimeShow());
    }

    private void initWheelViewHour() {
        List<String> hourList = SetTimeHelp.getTimeHour();
        mWheelViewHour.setAdapter(new ArrayWheelAdapter<>(hourList));
        mWheelViewHour.setCurrentItem(0); // 设置初始值
        mWheelViewHour.setTextSize(24); // 字体大小
        mWheelViewHour.setLineSpacingMultiplier(2.0f); // 间隔宽
        mWheelViewHour.setOnItemSelectedListener(index -> setTimeShow());
    }

    private void initWheelViewMinute() {
        List<String> minutesList = SetTimeHelp.getTimeMinutes();
        mWheelViewMinute.setAdapter(new ArrayWheelAdapter<>(minutesList));
        mWheelViewMinute.setCurrentItem(0);
        mWheelViewMinute.setTextSize(24);
        mWheelViewMinute.setLineSpacingMultiplier(2.0f);
        mWheelViewMinute.setOnItemSelectedListener(index -> setTimeShow());
    }

    private void setTimeShow() {
        int poDay = mWheelViewDay.getCurrentItem();
        int poHour = mWheelViewHour.getCurrentItem();
        int poMinute = mWheelViewMinute.getCurrentItem();
        String title = "";
        if (poDay != 0) {
            title += poDay + "天";
        }
        if (poHour != 0) {
            title += poHour + "小时";
        }
        if (poMinute != 0) {
            title += poMinute + "分钟";
        }
        mTimeShow.setText(title);
        mDuration = poDay * 1000 * 60 * 60 * 24 + poHour * 1000 * 60 * 60 + poMinute * 1000 * 60;
    }

    private void initClick() {
        mReturnButton.setOnClickListener(v -> finish());
        mSureButton.setOnClickListener(v -> {
            mPassEndTime.passed(mTimeShow.getText().toString(), mDuration);
            finish();
        });
    }

    /**
     * 跳转到EndTimeActivity
     *
     * @param context    上下文
     * @param passedTime 结束时间
     */
    public static void startEndTimeActivity(
            Context context, PassedDataHelp.PassedEndTime passedTime) {
        Intent intent = new Intent(context, EndTimeActivity.class);
        mPassEndTime = passedTime;
        context.startActivity(intent);
    }
}
