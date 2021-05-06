package com.littlecorgi.leave.logic;

import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import com.littlecorgi.leave.logic.model.AllLeaveResponse;
import com.littlecorgi.leave.logic.net.LeaveInterface;
import retrofit2.Call;

/**
 * @author littlecorgi 2021/5/5
 */
public class LeaveRepository {

    private static LeaveInterface getInterface() {
        return TencentServerRetrofitKt.getTencentCloudRetrofit().create(LeaveInterface.class);
    }

    public static Call<AllLeaveResponse> getLeaveFromTeacher(long teacherId) {
        return getInterface().getLeaveFromTeacher(teacherId);
    }
}
