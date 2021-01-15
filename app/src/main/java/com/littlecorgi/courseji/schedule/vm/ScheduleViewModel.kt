package com.littlecorgi.courseji.schedule.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.littlecorgi.courseji.AppDatabase
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.schedule.logic.model.bean.*
import com.littlecorgi.courseji.utils.CourseUtils

/**
 *
 * @author littlecorgi 2020/12/26
 */
class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val dataBase = AppDatabase.getDatabase(application)
    private val courseDao = dataBase.courseDao()
    private val tableDao = dataBase.tableDao()
    private val widgetDao = dataBase.appWidgetDao()
    private val timeTableDao = dataBase.timeTableDao()
    private val timeDao = dataBase.timeDetailDao()

    lateinit var table: TableBean
    lateinit var timeList: List<TimeDetailBean>
    var selectedWeek = 1
    val marTop = application.resources.getDimensionPixelSize(R.dimen.weekItemMarTop)
    var itemHeight = 0
    var alphaInt = 225
    val tableSelectList = arrayListOf<TableSelectBean>()
    val allCourseList = Array(7) { MutableLiveData<List<CourseBean>>() }
    val daysArray = arrayOf("日", "一", "二", "三", "四", "五", "六", "日")
    var currentWeek = 1

    fun initTableSelectList(): LiveData<List<TableSelectBean>> {
        return tableDao.getTableSelectListLiveData()
    }

    fun getMultiCourse(week: Int, day: Int, startNode: Int): List<CourseBean> {
        return allCourseList[day - 1].value!!.filter {
            it.inWeek(week) && it.startNode == startNode
        }
    }

    suspend fun getDefaultTable(): TableBean {
        return tableDao.getDefaultTable()
    }

    suspend fun getTimeList(timeTableId: Int): List<TimeDetailBean> {
        return timeDao.getTimeList(timeTableId)
    }

    suspend fun addBlankTable(tableName: String) {
        tableDao.insertTable(TableBean(id = 0, tableName = tableName))
    }

    suspend fun changeDefaultTable(id: Int) {
        tableDao.changeDefaultTable(table.id, id)
    }

    suspend fun getScheduleWidgetIds(): List<AppWidgetBean> {
        return widgetDao.getWidgetsByBaseType(0)
    }

    fun getRawCourseByDay(day: Int, tableId: Int): LiveData<List<CourseBean>> {
        return courseDao.getCourseByDayOfTableLiveData(day, tableId)
    }

    fun getShowCourseNumber(week: Int): LiveData<Int> {
        return if (table.showOtherWeekCourse) {
            courseDao.getShowCourseNumberWithOtherWeek(table.id, week)
        } else {
            courseDao.getShowCourseNumber(table.id, week)
        }
    }

    suspend fun deleteCourseBean(courseBean: CourseBean) {
        courseDao.deleteCourseDetail(CourseUtils.courseBean2DetailBean(courseBean))
    }

    suspend fun deleteCourseBaseBean(id: Int, tableId: Int) {
        courseDao.deleteCourseBaseBeanOfTable(id, tableId)
    }
}