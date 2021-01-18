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

    val mHeaderImageUrl =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603190176378&di=26e4109c7a9fa324180f9deab9e8e595&imgtype=0&src=http%3A%2F%2Fdik.img.kttpdq.com%2Fpic%2F70%2F48558%2F2ca2385f8d04b4a5.jpg"
    val mBgImageUrl =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603190406700&di=83c762b9884862270c98e3f2b1326286&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201504%2F02%2F140308w5n0nz3ns9b3bbzj.png"

    private val dataBase = AppDatabase.getDatabase(application)
    private val courseDao = dataBase.courseDao()
    private val tableDao = dataBase.tableDao()
    private val widgetDao = dataBase.appWidgetDao()
    private val timeTableDao = dataBase.timeTableDao()
    private val timeDao = dataBase.timeDetailDao()

    // 使用此属性时请务必保证使用顺序位于MainActivity.initWeekToggleGroup()之后
    // 因为这个时候我们才在初始化table
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