package com.littlecorgi.attendance;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.littlecorgi.attendance.tools.PieChartManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 考勤统计Activity
 */
public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // 设置饼状图
        initPieChartView();

        // 设置点击事件
        RelativeLayout absence = findViewById(R.id.rl_absence);
        absence.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.layout_attendance, new AbsenceFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        View late = findViewById(R.id.rl_late);
        late.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.layout_attendance, new LateFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        View leave = findViewById(R.id.rl_leave);
        leave.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.layout_attendance, new LeaveFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        View normal = findViewById(R.id.rl_normal);
        normal.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.layout_attendance, new NormalFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    /**
     * 加载饼图View
     */
    public void initPieChartView() {
        List<PieEntry> pieList = new ArrayList<>();
        pieList.add(new PieEntry(2.0f, "缺勤"));
        pieList.add(new PieEntry(1.0f, "请假"));
        pieList.add(new PieEntry(3.0f, "迟到"));
        pieList.add(new PieEntry(7.0f, "正常"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));

        PieChart pie = findViewById(R.id.pie_chart);
        PieChartManager pieChartManager = new PieChartManager(pie);
        pieChartManager.showSolidPieChart(pieList, colors);
    }
}
