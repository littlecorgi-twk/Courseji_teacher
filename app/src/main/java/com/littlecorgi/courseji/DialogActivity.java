package com.littlecorgi.courseji;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import com.littlecorgi.commonlib.BaseActivity;

/**
 * Dialog的Activity，透明，显示Dialog
 *
 * @author littlecorgi 2021/05/10
 */
public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");

        // 收到自定义消息时，显示个应用内通知
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("了解", (v, which) -> {
                })
                .show();
    }
}