package com.littlecorgi.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.littlecorgi.attendance.Tools.PieChartManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private View absence;
    private View late;
    private View leave;
    private View normal;

    private PieChart pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //设置饼状图
        initPieChartView();


        //设置点击事件

        absence = (View) findViewById(R.id.rl_absence);
        absence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_attendance,new AbsenceFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        late = (View) findViewById(R.id.rl_late);
        late.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_attendance,new LateFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        leave = (View) findViewById(R.id.rl_leave);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_attendance,new LeaveFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        normal = (View) findViewById(R.id.rl_normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_attendance,new NormalFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void initPieChartView(){
        pie = (PieChart) findViewById(R.id.pie_chart);

        List<PieEntry> pieList = new ArrayList<>();
        pieList.add(new PieEntry(2.0f,"缺勤"));
        pieList.add(new PieEntry(1.0f,"请假"));
        pieList.add(new PieEntry(3.0f,"迟到"));
        pieList.add(new PieEntry(7.0f,"正常"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));

        PieChartManager pieChartManager = new PieChartManager(pie);
        pieChartManager.showSolidPieChart(pieList,colors);
    }


}