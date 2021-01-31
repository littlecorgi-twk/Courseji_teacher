package com.example.middle;

import android.os.Bundle;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.middle.ui.student.middleStudentFragment;
import com.example.middle.ui.teacher.middleTeacherFragment;
import com.littlecorgi.commonlib.BaseActivity;

import static com.example.middle.logic.dao.WindowHelp.setWindowStatusBarColor;

public class middleMainActivity extends BaseActivity {
    /*
        true为学生端，false为教师端
     */
    public final boolean ISStudent = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_main);
        middleStudentFragment studentFragment = new middleStudentFragment();
        middleTeacherFragment teacherFragment = new middleTeacherFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if(ISStudent){
            fragmentTransaction.replace(R.id.middle_fragment,studentFragment,"student");
        }
        else{
            fragmentTransaction.replace(R.id.middle_fragment,teacherFragment,"teacher");
        }
        fragmentTransaction.commit();
    }
}