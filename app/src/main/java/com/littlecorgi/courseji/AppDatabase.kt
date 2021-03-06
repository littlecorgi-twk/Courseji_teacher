package com.littlecorgi.courseji

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.littlecorgi.courseji.schedule.logic.model.bean.*
import com.littlecorgi.courseji.schedule.logic.model.dao.CourseDao
import com.littlecorgi.courseji.schedule.logic.model.dao.TableDao
import com.littlecorgi.courseji.schedule.logic.model.dao.TimeDetailDao
import com.littlecorgi.courseji.schedule.logic.model.dao.TimeTableDao

/**
 *
 * @author littlecorgi 2021/1/5
 */
@Database(entities = [CourseBaseBean::class, CourseDetailBean::class, TimeDetailBean::class,
    TimeTableBean::class, TableBean::class],
        version = 1, exportSchema = false)


abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "course")
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun courseDao(): CourseDao

    abstract fun timeTableDao(): TimeTableDao

    abstract fun timeDetailDao(): TimeDetailDao

    abstract fun tableDao(): TableDao
}