package com.littlecorgi.middle.logic.network;

import com.littlecorgi.middle.logic.model.AllClassResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author littlecorgi 2021/5/6
 */
public interface ClassInterface {

    @GET("/class/getAllClassFromTeacher")
    Call<AllClassResponse> getAllClassFromTeacher(@Query("teacherId") long teacherId);
}
