package com.littlecorgi.courseji.schedule.logic.model

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * @author littlecorgi 2020/10/21
 */
data class CourseBean(
    // 课程ID
    var id: Int,
    // 课程名称
    var courseName: String,
    // 周几
    var day: Int,
    // 教室
    var room: String?,
    // 教师
    var teacher: String?,
    // 第几节开始
    var startNode: Int,
    // 需要几节
    var step: Int,
    // 第几周开始
    var startWeek: Int,
    // 第几周结束
    var endWeek: Int,
    // 0-每周，1-单周，2-双周
    var type: Int,
    // 颜色，请输入十六进制颜色信息值 eg.#000000
    var color: String,
    // 数据库id
    var tableId: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    /**
     * 返回从第几节到第几节
     */
    fun getNodeString(): String {
        return "第$startNode - ${startNode + step - 1}节"
    }

    /**
     * 判断当前课程是否在此周
     */
    fun inWeek(week: Int): Boolean {
        return when (type) {
            // 每周，并判断是否在此周
            0 -> (startWeek <= week) && (week <= endWeek)
            // 单周，并且判断是否在此周，以及此周是否是单周
            1 -> (startWeek <= week) && (week <= endWeek) && (week % 2 == 1)
            // 双周，并且判断是否在此周，以及此周是否是双周
            2 -> (startWeek <= week) && (week <= endWeek) && (week % 2 == 0)
            else -> false
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(courseName)
        parcel.writeInt(day)
        parcel.writeString(room)
        parcel.writeString(teacher)
        parcel.writeInt(startNode)
        parcel.writeInt(step)
        parcel.writeInt(startWeek)
        parcel.writeInt(endWeek)
        parcel.writeInt(type)
        parcel.writeString(color)
        parcel.writeInt(tableId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseBean> {
        override fun createFromParcel(parcel: Parcel): CourseBean {
            return CourseBean(parcel)
        }

        override fun newArray(size: Int): Array<CourseBean?> {
            return arrayOfNulls(size)
        }
    }
}