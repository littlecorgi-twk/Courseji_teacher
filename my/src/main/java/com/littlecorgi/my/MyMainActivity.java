package com.littlecorgi.my;

import android.os.Bundle;
import com.littlecorgi.commonlib.BaseActivity;

/**
 * 主页Activity
 */
public class MyMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        // CrashReport.initCrashReport(getApplicationContext(), "28d25075a1", true);
        // CrashReport.testJavaCrash();
    }
}
