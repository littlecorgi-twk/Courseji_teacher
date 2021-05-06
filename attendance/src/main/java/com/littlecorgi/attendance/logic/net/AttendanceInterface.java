package com.littlecorgi.attendance.logic.net;

import com.littlecorgi.attendance.logic.model.AllAttendanceResponse;
import com.littlecorgi.attendance.logic.model.AllCheckOnResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author littlecorgi 2021/5/6
 */
public interface AttendanceInterface {

    /**
     * 根据教室获取所有考勤记录
     *
     * @param teacherId 教师id
     */
    @POST("/checkOn/getAllCheckOnFromTeacher")
    public Call<AllCheckOnResponse> getAllCheckOnFromTeacher(@Query("teacherId") long teacherId);

    /**
     * 根据教师获取所有签到纪录
     *
     * @param teacherId 教师id
     */
    @POST("/attendance/getAllAttendanceFromTeacher")
    Call<AllAttendanceResponse> getAllAttendanceFromTeacher(
            @Query("teacherId") long teacherId);
}
