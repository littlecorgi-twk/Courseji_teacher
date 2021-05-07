package com.littlecorgi.courseji;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.umeng.message.UmengNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

/**
 * 小米消息通知activity
 *
 * @author littlecorgi 2021/05/07
 */
public class MipushTestActivity extends UmengNotifyClickActivity {
    private static final String TAG = MipushTestActivity.class.getSimpleName();
    private TextView miPushTextView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mipush_test);
        miPushTextView = (TextView) findViewById(R.id.mipushTextView);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.d(TAG, body);
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(() -> miPushTextView.setText(body));
        }
    }
}