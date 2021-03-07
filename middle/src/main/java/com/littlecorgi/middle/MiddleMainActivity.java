package com.littlecorgi.middle;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.ui.student.MiddleStudentFragment;
import com.littlecorgi.middle.ui.teacher.MiddleTeacherFragment;

/**
 * 签到页主页
 */
public class MiddleMainActivity extends BaseActivity {

    /*
       true为学生端，false为教师端
    */
    public final boolean mISStudent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_main);
        MiddleStudentFragment studentFragment = new MiddleStudentFragment();
        MiddleTeacherFragment teacherFragment = new MiddleTeacherFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if (mISStudent) {
            fragmentTransaction.replace(R.id.middle_fragment, studentFragment, "student");
        } else {
            fragmentTransaction.replace(R.id.middle_fragment, teacherFragment, "teacher");
        }
        fragmentTransaction.commit();
    }
}
