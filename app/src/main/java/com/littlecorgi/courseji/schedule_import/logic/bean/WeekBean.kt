package com.littlecorgi.courseji.schedule_import.logic.bean

/**
 *
 * @author littlecorgi 2021/1/5
 */
data class WeekBean(var start: Int, var end: Int, var type: Int) {
    override fun toString(): String {
        val typeString = when (type) {
            1 -> " 单周"
            2 -> " 双周"
            else -> ""
        }
        return "第$start - ${end}周$typeString"
    }
}
