package com.littlecorgi.leave.logic.net;

import com.littlecorgi.leave.logic.model.AllLeaveResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author littlecorgi 2021/5/5
 */
public interface LeaveInterface {

    /**
     * 获取教师所有审批的请假
     *
     * @param teacherId 教师id
     */
    @POST("/leave/getLeaveFromTeacher")
    Call<AllLeaveResponse> getLeaveFromTeacher(@Query("teacherId") long teacherId);
}
