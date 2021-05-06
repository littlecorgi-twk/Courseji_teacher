package com.littlecorgi.middle.logic;

import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import com.littlecorgi.middle.logic.model.AttendanceRequest;
import com.littlecorgi.middle.logic.model.AttendanceResponse;
import com.littlecorgi.middle.logic.network.AttendanceInterface;
import retrofit2.Call;

/**
 * @author littlecorgi 2021/5/6
 */
public class AttendanceRepository {

    private static AttendanceInterface getInterface() {
        return TencentServerRetrofitKt.getTencentCloudRetrofit().create(AttendanceInterface.class);
    }

    public static Call<AttendanceResponse> createAttendance(long classId,
                                                            AttendanceRequest attendance) {
        return getInterface().createAttendance(classId, attendance);
    }
}
