package com.littlecorgi.middle.logic.dao;

import android.view.View;

/**
 * TextInputHelper类的设置类，用来设置View
 */
public interface TextInputHelperSetting {

    /**
     * 设置
     *
     * @param view    TextView或者EditText
     * @param isEmpty 是否透明和点击事件
     */
    void set(View view, boolean isEmpty);
}
