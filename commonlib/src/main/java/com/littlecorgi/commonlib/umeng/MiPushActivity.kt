package com.littlecorgi.commonlib.umeng

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.littlecorgi.commonlib.R
import com.umeng.message.UmengNotifyClickActivity
import org.android.agoo.common.AgooConstants


class MiPushActivity : UmengNotifyClickActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mipush)
    }

    override fun onMessage(p0: Intent?) {
        super.onMessage(p0) //此方法必须调用，否则无法统计打开数
        val body = intent.getStringExtra(AgooConstants.MESSAGE_BODY) ?: "null"
        Log.i(TAG, body)
    }

    companion object {
        private const val TAG = "MiPushActivity"
    }
}