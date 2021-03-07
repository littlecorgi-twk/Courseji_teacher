package com.littlecorgi.my.logic.dao;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * TextInput的帮助类 这个是TextView，EditText的监听，在text改变时回调到这里
 */
public class TextInputHelper implements TextWatcher {

    private final View mMainView; // 操作按钮的View
    private List<TextView> mViewSet; // TextView集合，子类也可以（EditText、TextView、Button）
    private final boolean mIsAlpha; // 是否设置透明度

    private final TextInputHelperSetting setting;

    /**
     * 默认设置透明度
     */
    public TextInputHelper(View view, TextInputHelperSetting set) {
        this(view, set, true);
    }

    /**
     * 构造方法
     *
     * @param view  TextView或者EditText
     * @param set   setting
     * @param alpha 是否设置透明度
     */
    public TextInputHelper(View view, TextInputHelperSetting set, boolean alpha) {
        if (view == null) {
            throw new IllegalArgumentException("The view is empty");
        }
        mMainView = view;
        mIsAlpha = alpha;
        setting = set;
    }

    /**
     * 添加EditText或者TextView监听
     *
     * @param views 传入单个或者多个EditText或者TextView对象
     */
    public void addViews(TextView... views) {
        if (views == null) {
            return;
        }

        if (mViewSet == null) {
            mViewSet = new ArrayList<>(views.length - 1);
        }

        for (TextView view : views) {
            view.addTextChangedListener(this);
            mViewSet.add(view);
        }
        afterTextChanged(null);
    }

    /**
     * 移除EditText监听，避免内存泄露
     */
    public void removeViews() {
        if (mViewSet == null) {
            return;
        }

        for (TextView view : mViewSet) {
            view.removeTextChangedListener(this);
        }
        mViewSet.clear();
        mViewSet = null;
    }

    //***************************************************
    //*TextWatcher这个类用于监听EditText的输入
    //***************************************************

    /**
     * text修改前
     *
     * @param s     修改之前的文字。
     * @param start 字符串中即将发生修改的位置。
     * @param count 字符串中即将被修改的文字的长度。如果是新增的话则为0。
     * @param after 被修改的文字修改之后的长度。如果是删除的话则为0。
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * text正在被修改
     *
     * @param s      改变后的字符串
     * @param start  有变动的字符串的序号
     * @param before 被改变的字符串长度，如果是新增则为0。
     * @param count  添加的字符串长度，如果是删除则为0。
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    /**
     * text修改后
     *
     * @param s 修改后的文字 主要监听这里，判断输入是否改变
     */
    @Override
    public synchronized void afterTextChanged(Editable s) {
        if (mViewSet == null) {
            return;
        }

        for (TextView view : mViewSet) {
            if ("".equals(view.getText().toString())) {
                setEnabled(false);
                return;
            }
        }
        setEnabled(true);
    }

    /**
     * 设置透明度，以及是否设置点击事件
     *
     * @param enabled 是否透明和点击事件
     */
    public void setEnabled(boolean enabled) {
        if (enabled == mMainView.isEnabled()) {
            return;
        }

        if (enabled) {
            // 启用View的事件
            mMainView.setEnabled(true);
            if (mIsAlpha) {
                // //设置不透明
                // mMainView.setBackgroundColor(Color.parseColor("#1afa29"));
                // mMainView.setAlpha(1f);
                // mMainView.setOnClickListener();
                setting.set(mMainView, false);
            }
        } else {
            // 禁用View的事件
            mMainView.setEnabled(false);
            if (mIsAlpha) {
                // //设置半透明
                // mMainView.setAlpha(0.5f);
                setting.set(mMainView, true);
            }
        }
    }
}
