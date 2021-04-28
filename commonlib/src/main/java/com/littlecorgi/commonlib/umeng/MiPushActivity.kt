package com.littlecorgi.commonlib.umeng

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.littlecorgi.commonlib.R
import com.umeng.message.UmengNotifyClickActivity
import org.android.agoo.common.AgooConstants

/**
 * MiPush和HUAWEIPush都是这个，详见
 *  1. https://developer.umeng.com/docs/67966/detail/98589#h2-u4F7Fu7528u5C0Fu7C73u5F39u7A97u529Fu80FD5
 *  2. https://developer.umeng.com/docs/67966/detail/98589#h2-u4F7Fu7528u534Eu4E3Au5F39u7A97u529Fu80FD15
 */
class MiPushActivity : UmengNotifyClickActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mipush)
    }

    override fun onMessage(p0: Intent?) {
        super.onMessage(p0) // 此方法必须调用，否则无法统计打开数
        val body = intent.getStringExtra(AgooConstants.MESSAGE_BODY) ?: "null"
        Log.i(TAG, body)
    }

    companion object {
        private const val TAG = "MiPushActivity"
    }
}
