package com.littlecorgi.commonlib.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间相关的工具类
 * @author littlecorgi 2020/10/21
 */
object TimeUtil {

    /**
     * 根据int型的周数返回String
     */
    fun getWeekDayStr(weekDay: Int): String = when (weekDay) {
        1 -> "周一"
        2 -> "周二"
        3 -> "周三"
        4 -> "周四"
        5 -> "周五"
        6 -> "周六"
        7 -> "周日"
        else -> "时间好像错误了，麻烦反馈给我们的客服小姐姐哦OvO"
    }

    /**
     * 获取今日日期
     */
    fun getTodayDate(): String = SimpleDateFormat("M月d日", Locale.CHINA).format(Date())

    /**
     * 获取今天周几 - Int
     */
    fun getTodayWeekDayInt(): Int {
        val cal = Calendar.getInstance()
        val weekDay = cal.get(Calendar.DAY_OF_WEEK)
        return if (weekDay == 1) {
            7
        } else {
            weekDay - 1
        }
    }

    /**
     * 获取今天周几 - String
     */
    fun getTodayWeekDay(): String = getWeekDayStr(getTodayWeekDayInt())
}