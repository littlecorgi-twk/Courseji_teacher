package com.littlecorgi.my.logic.network;

import com.littlecorgi.my.logic.model.SignUpResponse;
import com.littlecorgi.my.logic.model.Teacher;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author littlecorgi 2021/5/4
 */
public interface UserRequestInterface {

    /**
     * 注册
     *
     * @param teacher 教师信息
     * @return 响应结果，包含Sting类型的结果
     */
    @POST("/teacher/signUp")
    Call<SignUpResponse> signUp(@Body Teacher.DataBean teacher);

    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 响应结果，包含{@link com.littlecorgi.my.logic.model.Teacher}类型的结果
     */
    @POST("/teacher/signIn")
    Call<Teacher> signIn(@Query("email") String email, @Body RequestBody password);
}

