package com.littlecorgi.my.logic;

import android.content.SharedPreferences;
import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import com.littlecorgi.commonlib.util.UserSPConstant;
import com.littlecorgi.my.logic.model.SignUpResponse;
import com.littlecorgi.my.logic.model.Teacher;
import com.littlecorgi.my.logic.network.UserRequestInterface;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author littlecorgi 2021/5/4
 */
public class UserRetrofitRepository {

    /**
     * 获取用户注册的Call
     *
     * @param teacher 注册所需的用户信息
     */
    public static Call<SignUpResponse> getUserSignUpCall(Teacher.DataBean teacher) {
        UserRequestInterface userRequestInterface =
                TencentServerRetrofitKt.getTencentCloudRetrofit()
                        .create(UserRequestInterface.class);
        // 执行请求
        return userRequestInterface.signUp(teacher);
    }

    /**
     * 获取用户登录Call
     *
     * @param email    账号
     * @param password 密码
     */
    public static Call<Teacher> getUserSignInCall(String email, String password) {
        UserRequestInterface userRequestInterface =
                TencentServerRetrofitKt.getTencentCloudRetrofit()
                        .create(UserRequestInterface.class);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody stringBody = RequestBody.Companion.create(password, mediaType);
        // 执行请求
        return userRequestInterface.signIn(email, stringBody);
    }

    /**
     * 从SharedPreferences中获取Student数据
     *
     * @param sp SharedPreferences
     */
    public static Teacher getTeacherFromSP(SharedPreferences sp) {
        Teacher teacher = new Teacher();
        teacher.setStatus(800);
        Teacher.DataBean data = new Teacher.DataBean();
        data.setId(sp.getLong(UserSPConstant.TEACHER_USER_ID, -1));
        data.setName(sp.getString(UserSPConstant.TEACHER_NAME, ""));
        data.setPassword(sp.getString(UserSPConstant.TEACHER_PASSWORD, ""));
        data.setPhone(sp.getString(UserSPConstant.TEACHER_PHONE, ""));
        data.setAvatar(sp.getString(UserSPConstant.TEACHER_AVATAR, ""));
        teacher.setData(data);
        return teacher;
    }
}
