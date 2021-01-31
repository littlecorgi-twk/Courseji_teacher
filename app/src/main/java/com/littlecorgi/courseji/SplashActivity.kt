package com.littlecorgi.courseji

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.littlecorgi.commonlib.App
import com.littlecorgi.commonlib.BaseActivity
import com.littlecorgi.commonlib.context.start
import com.littlecorgi.courseji.schedule.logic.model.bean.TableBean
import com.littlecorgi.courseji.schedule.logic.model.bean.TimeDetailBean
import com.littlecorgi.courseji.schedule.logic.model.bean.TimeTableBean
import com.littlecorgi.courseji.utils.getPrefer
import kotlinx.coroutines.launch
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
                sendMessage(Message.obtain().apply {
                    what = MSG_CONTINUE
                    this.arg1 = 3
                })
            }
        }
        if (!applicationContext.getPrefer().getBoolean("has_adjust", false)) {
            lifecycleScope.launch {
                val tableData = TableBean(type = 1, id = 1, tableName = "")
                val dataBase = AppDatabase.getDatabase(applicationContext)
                val tableDao = dataBase.tableDao()
                val timeDao = dataBase.timeDetailDao()
                val timeTableDao = dataBase.timeTableDao()
                if (timeTableDao.getTimeTable(1) == null) {
                    timeTableDao.insertTimeTable(TimeTableBean(id = 1, name = "默认"))
                }
                val timeList = ArrayList<TimeDetailBean>().apply {
                    add(TimeDetailBean(1, "08:00", "08:50", 1))
                    add(TimeDetailBean(2, "08:55", "09:45", 1))
                    add(TimeDetailBean(3, "10:15", "11:05", 1))
                    add(TimeDetailBean(4, "11:10", "12:00", 1))
                    add(TimeDetailBean(5, "14:30", "15:20", 1))
                    add(TimeDetailBean(6, "15:25", "16:15", 1))
                    add(TimeDetailBean(7, "16:30", "17:20", 1))
                    add(TimeDetailBean(8, "17:25", "18:15", 1))
                    add(TimeDetailBean(9, "19:00", "19:50", 1))
                    add(TimeDetailBean(10, "19:55", "20:45", 1))
                }
                try {
                    timeDao.insertTimeList(timeList)
                    tableDao.insertTable(tableData)
                } catch (e: Exception) {

                }
                applicationContext.getPrefer().edit()
                    .putBoolean("has_adjust", true)
                    .putInt("", 1)
                    .apply()
            }
        }
    }

    private class MyTimerHandler(looper: Looper, activity: Activity) : Handler(looper) {
        private val activity = WeakReference(activity)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_CONTINUE -> {
                    if (msg.arg1 == -1) {
                        // 当为 -1 了，则证明倒计时结束，就可以进行跳转了
                        sendMessage(Message.obtain().apply {
                            what = MSG_START_INTENT
                        })
                    } else {
                        activity.get()?.let {
                            val text = "${msg.arg1}s"
                            (it.findViewById(R.id.tv_time) as TextView).text = text
                        }
                        // 延迟1秒发送一条消息，以达到倒计时的效果
                        sendMessageDelayed(Message.obtain().apply {
                            what = MSG_CONTINUE
                            arg1 = msg.arg1 - 1
                        }, 1 * 1000)
                        Log.d("MyTimerHandler", "onCreate: ${msg.arg1}")
                    }
                }
                MSG_START_INTENT -> {
                    //TODO 这块的错误判断有点逻辑混乱，activity都为null了，那么肯定弹不了Toast，但是一时没想好该怎么改
                    activity.get()?.start<MainActivity>() ?: Toast.makeText(
                        activity.get(),
                        "获取到的Activity为null",
                        Toast.LENGTH_SHORT
                    ).show()
                    // 销毁此Activity，以便在MainActivity返回时不再会显示此Activity
                    activity.get()?.finish()
                }
            }
        }
    }
}