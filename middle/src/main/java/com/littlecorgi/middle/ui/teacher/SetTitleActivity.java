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
import com.littlecorgi.middle.logic.dao.TextInputHelper;
import com.littlecorgi.middle.logic.dao.PassedDataHelp;

/**
 * 设置标题的Activity
 */
public class SetTitleActivity extends BaseActivity {

    private AppCompatTextView textView;
    private AppCompatEditText editText;
    private AppCompatButton button;
    private static PassedDataHelp.PassedTitle passTitle;

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

        textView = findViewById(R.id.middle_SetTitle_returnButton);
        editText = findViewById(R.id.middle_SetTitle_themeEdit);
        button = findViewById(R.id.middle_SetTitleSureButton);
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
        editText.setText("");
        textView.setOnClickListener(v -> finish());
    }

    private void setButtonColor() {
        TextInputHelper helper =
                new TextInputHelper(
                        button,
                        (view, isEmpty) -> {
                            if (!isEmpty) {
                                view.setAlpha(1);
                                view.setBackgroundColor(Color.parseColor("#4CAF50"));
                                view.setOnClickListener(
                                        v -> {
                                            Editable editable = editText.getText();
                                            if (editable != null) {
                                                passTitle.passed(editable.toString());
                                            }
                                            finish();
                                        });
                            } else {
                                view.setAlpha((float) 0.5);
                                view.setBackgroundColor(Color.parseColor("#C49A9999"));
                            }
                        });
        helper.addViews(editText);
    }

    /**
     * 设置标题
     *
     * @param activity    目标Activity
     * @param passedTitle 标题
     */
    public static void startSetTitle(Activity activity, PassedDataHelp.PassedTitle passedTitle) {
        Intent intent = new Intent(activity, SetTitleActivity.class);
        passTitle = passedTitle;
        activity.startActivity(intent);
    }
}
