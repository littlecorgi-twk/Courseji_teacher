package com.littlecorgi.my.logic;

import android.util.Log;
import com.littlecorgi.my.logic.model.Teacher;
import com.littlecorgi.my.logic.model.TeacherBean;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    /**
     * 登录
     *
     * @param username 用户名/邮箱
     * @param password 密码
     * @return 是否登录成功
     */
    public Result login(String username, String password) {
        Result result;

        Call<Teacher> responseBodyCall =
                UserRetrofitRepository.getUserSignInCall(username, password);

        try {
            Response<Teacher> response = responseBodyCall.execute();
            Log.d("LoginDataSource", "onResponse: " + response);
            if (response.body() != null) {
                Teacher teacher = response.body();
                Log.d("LoginDataSource", "onResponse: " + teacher);
                if (teacher.getStatus() == 800) {
                    Log.d("LoginDataSource", "onResponse: 登录成功");
                    result = new Result.Success<>(teacher);
                } else if (teacher.getStatus() == 1002) {
                    // 用户不存在，转注册
                    teacher.setData(new TeacherBean());
                    teacher.getData().setEmail(username);
                    teacher.getData().setPassword(password);
                    result = new Result.Success<>(teacher);
                } else {
                    result = new Result.Error(
                            new IOException("登录失败" + teacher.getMsg()));
                }
            } else {
                Log.d("LoginDataSource", "onResponse: 响应为空");
                result =
                        new Result.Error(new IOException("登录失败：响应为空"));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.d("LoginDataSource", "onFailure: 登录失败" + exception.getMessage());
            result =
                    new Result.Error(new IOException("登录失败", exception));
        }
        return result;
    }
}