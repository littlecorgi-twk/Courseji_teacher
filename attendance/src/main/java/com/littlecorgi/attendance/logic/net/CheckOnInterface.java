package com.littlecorgi.attendance.logic.net;

import com.littlecorgi.attendance.logic.model.AllCheckOnResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author littlecorgi 2021/5/6
 */
public interface CheckOnInterface {

    /**
     * 根据考勤获取所有的签到
     *
     * @param attendanceId 考勤id
     */
    @POST("/checkOn/getCheckOnFromAttendance")
    Call<AllCheckOnResponse> getCheckOnFromAttendance(@Query("attendanceId") long attendanceId);
}
