package com.littlecorgi.courseji.schedule.ui

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * 课程表RecyclerView中每一项Item的信息
 * @author littlecorgi 2021/1/24
 */
data class ScheduleItem(
    val text: String = "",
    val textSize: Float = 16F,
    val textColor: Int = Color.TRANSPARENT,
    @ColorInt val backgroundColor: Int = Color.TRANSPARENT
)