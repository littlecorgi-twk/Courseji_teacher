package com.littlecorgi.attendance.logic;

import com.littlecorgi.attendance.logic.model.AllAttendanceResponse;
import com.littlecorgi.attendance.logic.model.AllCheckOnResponse;
import com.littlecorgi.attendance.logic.net.AttendanceInterface;
import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import retrofit2.Call;

/**
 * @author littlecorgi 2021/5/6
 */
public class AttendanceRepository {

    private static AttendanceInterface getInterface() {
        return TencentServerRetrofitKt.getTencentCloudRetrofit().create(AttendanceInterface.class);
    }

    public static Call<AllCheckOnResponse> getCheckOnResponse(long teacherId) {
        return getInterface().getAllCheckOnFromTeacher(teacherId);
    }

    public static Call<AllAttendanceResponse> getAllAttendanceFromTeacher(long teacherId) {
        return getInterface().getAllAttendanceFromTeacher(teacherId);
    }
}
