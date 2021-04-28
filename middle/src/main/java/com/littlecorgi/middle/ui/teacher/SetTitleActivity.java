package com.littlecorgi.middle.ui.teacher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.middle.R;
import com.littlecorgi.middle.logic.dao.PassedDataHelp;
import com.littlecorgi.middle.logic.dao.TextInputHelper;

/**
 * 设置标题的Activity
 */
public class SetTitleActivity extends BaseActivity {

    private AppCompatTextView mTextView;
    private AppCompatEditText mEditText;
    private AppCompatButton mButton;
    private static PassedDataHelp.PassedTitle mPassTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_teacher_settitle);
        // 初始化view
        initView();
        // 初始化数据
        initData();
    }

    private void initView() {
        mTextView = findViewById(R.id.middle_SetTitle_returnButton);
        mEditText = findViewById(R.id.middle_SetTitle_themeEdit);
        mButton = findViewById(R.id.middle_SetTitleSureButton);
        initImage();
    }

    /**
     * 修改背景？
     */
    private void initImage() {
        AppCompatImageView imageView = findViewById(R.id.middle_SetTitle_bg);
    }

    private void initData() {
        setButtonColor();
        setReturnButton();
    }

    private void setReturnButton() {
        mEditText.setText("");
        mTextView.setOnClickListener(v -> finish());
    }

    private void setButtonColor() {
        TextInputHelper helper = new TextInputHelper(
                mButton,
                (view, isEmpty) -> {
                    if (!isEmpty) {
                        view.setAlpha(1);
                        view.setBackgroundColor(Color.parseColor("#4CAF50"));
                        view.setOnClickListener(v -> {
                            Editable editable = mEditText.getText();
                            if (editable != null) {
                                mPassTitle.passed(editable.toString());
                            }
                            finish();
                        });
                    } else {
                        view.setAlpha((float) 0.5);
                        view.setBackgroundColor(Color.parseColor("#C49A9999"));
                    }
                });
        helper.addViews(mEditText);
    }

    /**
     * 设置标题
     *
     * @param activity    目标Activity
     * @param passedTitle 标题
     */
    public static void startSetTitle(Activity activity, PassedDataHelp.PassedTitle passedTitle) {
        Intent intent = new Intent(activity, SetTitleActivity.class);
        mPassTitle = passedTitle;
        activity.startActivity(intent);
    }
}
