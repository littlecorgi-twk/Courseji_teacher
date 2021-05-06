package com.littlecorgi.attendance.logic;

import com.littlecorgi.attendance.logic.model.AllCheckOnResponse;
import com.littlecorgi.attendance.logic.net.CheckOnInterface;
import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import retrofit2.Call;

/**
 * @author littlecorgi 2021/5/6
 */
public class CheckOnRepository {

    private static CheckOnInterface getInterface() {
        return TencentServerRetrofitKt.getTencentCloudRetrofit().create(CheckOnInterface.class);
    }

    public static Call<AllCheckOnResponse> getCheckOnFromAttendance(long attendanceId) {
        return getInterface().getCheckOnFromAttendance(attendanceId);
    }
}
