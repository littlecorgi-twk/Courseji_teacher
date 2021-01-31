package com.littlecorgi.courseji.schedule_setting.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.littlecorgi.courseji.AppDatabase
import com.littlecorgi.courseji.schedule.logic.model.bean.TableBean
import com.littlecorgi.courseji.utils.CourseUtils
import java.util.*

class ScheduleSettingsViewModel(application: Application) : AndroidViewModel(application) {

    var mYear = 2021
    var mMonth = 1
    var mDay = 1
    lateinit var table: TableBean
    lateinit var termStartList: List<String>

    private val dataBase = AppDatabase.getDatabase(application)
    private val tableDao = dataBase.tableDao()

    suspend fun saveSettings() {
        tableDao.updateTable(table)
    }

    fun getCurrentWeek(): Int {
        return CourseUtils.countWeek(table.startDate, table.sundayFirst)
    }

    fun setCurrentWeek(week: Int) {
        val cal = Calendar.getInstance()
        if (table.sundayFirst) {
            cal.firstDayOfWeek = Calendar.SUNDAY
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        } else {
            cal.firstDayOfWeek = Calendar.MONDAY
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        cal.add(Calendar.WEEK_OF_YEAR, -week + 1)
        mYear = cal.get(Calendar.YEAR)
        mMonth = cal.get(Calendar.MONTH) + 1
        mDay = cal.get(Calendar.DATE)
        table.startDate = "${mYear}-${mMonth}-${mDay}"
    }

}