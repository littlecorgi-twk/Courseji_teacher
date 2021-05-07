package com.littlecorgi.leave;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.littlecorgi.leave.ui.TeacherLeaveFragment;
import java.util.List;

/**
 * 教师端请假页面
 */
public class TeacherLeaveActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_teacher_leave, new TeacherLeaveFragment())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportFragmentManager().getFragments();
        if (getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment f : fragments) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
