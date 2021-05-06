package com.littlecorgi.middle.logic.network;

import com.littlecorgi.middle.logic.model.AttendanceRequest;
import com.littlecorgi.middle.logic.model.AttendanceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author littlecorgi 2021/5/6
 */
public interface AttendanceInterface {

    @POST("/attendance/createAttendance")
    Call<AttendanceResponse> createAttendance(@Query("classId") long classId,
                                              @Body AttendanceRequest attendance);
}
