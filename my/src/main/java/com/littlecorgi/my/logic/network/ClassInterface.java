package com.littlecorgi.my.logic.network;

import com.littlecorgi.my.logic.model.AllClassResponse;
import com.littlecorgi.my.logic.model.CreateClassResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 班级的Retrofit接口
 *
 * @author littlecorgi 2021/5/7
 */
public interface ClassInterface {

    @GET("/class/getAllClassFromTeacher")
    Call<AllClassResponse> getAllClassFromTeacher(@Query("teacherId") long teacherId);

    @GET("/class/createClass")
    Call<CreateClassResponse> createClass(@Query("teacherId") long teacherId,
                                          @Query("className") String className);
}
