package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class AttendanceActivity extends AppCompatActivity {

    private View absence;
    private View late;
    private View leave;
    private View normal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

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


}