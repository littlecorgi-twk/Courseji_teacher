package com.littlecorgi.corseji

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.littlecorgi.commonlib.BaseActivity
import com.littlecorgi.commonlib.start
import java.lang.ref.WeakReference

class SplashActivity : BaseActivity() {

    companion object {
        private const val MSG_CONTINUE = 101
        private const val MSG_START_INTENT = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        MyTimerHandler(Looper.getMainLooper(), this).apply {
            if (App.isDebug) {
                sendMessage(Message.obtain().apply {
                    what = MSG_START_INTENT
                })
            } else {
                sendMessageDelayed(Message.obtain().apply {
                    what = MSG_CONTINUE
                    this.arg1 = 3
                }, 1 * 1000)
            }
        }
    }

    private class MyTimerHandler(looper: Looper, activity: Activity) : Handler(looper) {
        private val activity = WeakReference(activity)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_CONTINUE -> {
                    if (msg.arg1 == 0) {
                        sendMessage(Message.obtain().apply {
                            what = MSG_START_INTENT
                        })
                    } else {
                        sendMessageDelayed(Message.obtain().apply {
                            what = MSG_CONTINUE
                            this.arg1--
                        }, 1 * 1000)
                    }
                }
                MSG_START_INTENT -> {
                    App.getContext().start<MainActivity>()
                }
            }
        }
    }
}