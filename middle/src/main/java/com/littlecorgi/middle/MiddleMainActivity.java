package com.littlecorgi.middle;

import android.Manifest;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.logic.dao.AndPermissionHelp;
import com.littlecorgi.middle.ui.teacher.MiddleTeacherFragment;

/**
 * 签到页主页
 */
public class MiddleMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_main);
        MiddleTeacherFragment teacherFragment = new MiddleTeacherFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.middle_fragment, teacherFragment, "teacher");
        fragmentTransaction.commit();
    }
}
