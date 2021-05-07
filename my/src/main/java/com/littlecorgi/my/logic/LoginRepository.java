package com.littlecorgi.my.logic;

import android.content.Context;
import android.content.SharedPreferences;
import com.littlecorgi.commonlib.util.UserSPConstant;
import com.littlecorgi.my.logic.model.LoggedInUser;
import com.littlecorgi.my.logic.model.Teacher;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取单例
     *
     * @param dataSource 数据源
     * @return 单例
     */
    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    private void setLoggedInUser(Context context, Teacher user) {
        SharedPreferences sp = context.getSharedPreferences(
                UserSPConstant.FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (user.getData().getId() != 0) {
            editor.putLong(UserSPConstant.TEACHER_USER_ID, user.getData().getId());
        }
        if (user.getData().getEmail() != null) {
            editor.putString(UserSPConstant.TEACHER_EMAIL, user.getData().getEmail());
        }
        if (user.getData().getName() != null) {
            editor.putString(UserSPConstant.TEACHER_NAME, user.getData().getName());
        }
        if (user.getData().getPhone() != null) {
            editor.putString(UserSPConstant.TEACHER_PHONE, user.getData().getPhone());
        }
        if (user.getData().getAvatar() != null) {
            editor.putString(UserSPConstant.TEACHER_AVATAR, user.getData().getAvatar());
        }
        if (user.getData().getPassword() != null) {
            editor.putString(UserSPConstant.TEACHER_PASSWORD, user.getData().getPassword());
        }
        editor.apply();

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    /**
     * 登录
     *
     * @param context  上下文
     * @param username 用户名
     * @param password 密码
     * @return 是否正确
     */
    public Result<LoggedInUser> login(Context context, String username, String password) {
        // handle login
        Result result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            Teacher student = ((Result.Success<Teacher>) result).getData();
            LoggedInUser loggedInUser = null;
            if (student.getStatus() == 800) {
                loggedInUser = new LoggedInUser("" + student.getData().getId(),
                        student.getData().getName());
                setLoggedInUser(context, student);
            } else if (student.getStatus() == 1002) {
                loggedInUser = new LoggedInUser("" + student.getData().getId(),
                        "用户不存在！");
            }
            this.user = loggedInUser;
            return new Result.Success<LoggedInUser>(loggedInUser);
        }

        return (Result.Error) result;
    }
}