package com.littlecorgi.corseji

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import com.littlecorgi.commonlib.App
import com.littlecorgi.commonlib.BaseActivity
import com.littlecorgi.commonlib.context.start
import es.dmoral.toasty.Toasty
import java.lang.ref.WeakReference

class SplashActivity : BaseActivity() {

    companion object {
        private const val MSG_CONTINUE = 101
        private const val MSG_START_INTENT = 102
    }

    // private val myAppContextPresenter: MyAppContextPresenter by inject()

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
                        // 当为0了，则证明倒计时结束，就可以进行跳转了
                        sendMessage(Message.obtain().apply {
                            what = MSG_START_INTENT
                        })
                    } else {
                        activity.get()?.let {
                            val text = "${msg.arg1}s"
                            (it.findViewById(R.id.tv_time) as TextView).text = text
                        }
                        // 延迟1秒发送消息，达到倒计时的效果
                        sendMessageDelayed(Message.obtain().apply {
                            what = MSG_CONTINUE
                            arg1 = msg.arg1 - 1
                        }, 1 * 1000)
                        Log.d("MyTimerHandler", "onCreate: ${msg.arg1}")
                    }
                }
                MSG_START_INTENT -> {
                    // appContext.get()?.start<MainActivity>() ?: Toasty.error(
                    //     appContext.get()!!,
                    //     "获取到的AppContext为null"
                    // )
                    //TODO 这块的错误判断有点逻辑混乱，activity都为null了，那么肯定弹不了Toast，但是一时没想好该怎么改
                    activity.get()?.start<MainActivity>() ?: Toasty.error(
                        activity.get()!!,
                        "获取到的Activity为null"
                    )
                }
            }
        }
    }
}