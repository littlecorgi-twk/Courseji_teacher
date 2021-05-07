package com.littlecorgi.attendance;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 教师端签到情况查询Activity
 */
public class TeacherAttendanceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_attendance_activity);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.teacher_attendance, new TeacherAttendanceFragment())
                .commit();
    }
}
