package com.littlecorgi.my.ui.addGroup;

import android.os.Bundle;

import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.my.R;

import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;

public class addGroupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_group);
        initView();
    }

    private void initView() {
        initBarColor();

    }
    private void initBarColor() {
        setWindowStatusBarColor(this,R.color.blue);
    }


}