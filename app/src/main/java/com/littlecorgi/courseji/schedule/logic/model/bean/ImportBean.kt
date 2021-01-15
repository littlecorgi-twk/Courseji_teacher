package com.littlecorgi.courseji.schedule.logic.model.bean

data class ImportBean(var name: String,
                      var timeInfo: String,
                      var teacher: String?,
                      var room: String?,
                      var startNode: Int,
                      var cDay: Int = 0)