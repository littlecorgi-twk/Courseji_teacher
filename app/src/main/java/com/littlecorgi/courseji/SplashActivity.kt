package com.littlecorgi.courseji

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.littlecorgi.commonlib.App
import com.littlecorgi.commonlib.BaseActivity
import com.littlecorgi.commonlib.context.start
import com.littlecorgi.courseji.schedule.logic.model.bean.TableBean
import com.littlecorgi.courseji.schedule.logic.model.bean.TimeDetailBean
import com.littlecorgi.courseji.schedule.logic.model.bean.TimeTableBean
import com.littlecorgi.courseji.utils.getPrefer
import es.dmoral.toasty.Toasty
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
                    add(TimeDetailBean(2, "09:00", "09:50", 1))
                    add(TimeDetailBean(3, "10:10", "11:00", 1))
                    add(TimeDetailBean(4, "11:10", "12:00", 1))
                    add(TimeDetailBean(5, "13:30", "14:20", 1))
                    add(TimeDetailBean(6, "14:30", "15:20", 1))
                    add(TimeDetailBean(7, "15:40", "16:30", 1))
                    add(TimeDetailBean(8, "16:40", "17:30", 1))
                    add(TimeDetailBean(9, "18:30", "19:20", 1))
                    add(TimeDetailBean(10, "19:30", "20:20", 1))
                    add(TimeDetailBean(11, "20:30", "21:20", 1))
                    add(TimeDetailBean(12, "00:00", "00:00", 1))
                    add(TimeDetailBean(13, "00:00", "00:00", 1))
                    add(TimeDetailBean(14, "00:00", "00:00", 1))
                    add(TimeDetailBean(15, "00:00", "00:00", 1))
                    add(TimeDetailBean(16, "00:00", "00:00", 1))
                    add(TimeDetailBean(17, "00:00", "00:00", 1))
                    add(TimeDetailBean(18, "00:00", "00:00", 1))
                    add(TimeDetailBean(19, "00:00", "00:00", 1))
                    add(TimeDetailBean(20, "00:00", "00:00", 1))
                    add(TimeDetailBean(21, "00:00", "00:00", 1))
                    add(TimeDetailBean(22, "00:00", "00:00", 1))
                    add(TimeDetailBean(23, "00:00", "00:00", 1))
                    add(TimeDetailBean(24, "00:00", "00:00", 1))
                    add(TimeDetailBean(25, "00:00", "00:00", 1))
                    add(TimeDetailBean(26, "00:00", "00:00", 1))
                    add(TimeDetailBean(27, "00:00", "00:00", 1))
                    add(TimeDetailBean(28, "00:00", "00:00", 1))
                    add(TimeDetailBean(29, "00:00", "00:00", 1))
                    add(TimeDetailBean(30, "00:00", "00:00", 1))
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
                    activity.get()?.start<MainActivity>() ?: Toasty.error(
                        activity.get()!!,
                        "获取到的Activity为null"
                    )
                    // 销毁此Activity，以便在MainActivity返回时不再会显示此Activity
                    activity.get()?.finish()
                }
            }
        }
    }
}