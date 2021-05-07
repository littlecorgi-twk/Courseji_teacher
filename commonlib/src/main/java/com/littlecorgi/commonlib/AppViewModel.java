package com.littlecorgi.commonlib;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.ViewModel;
import com.littlecorgi.commonlib.util.UserSPConstant;

/**
 * 用于存放几个模块共有的数据
 *
 * @author littlecorgi 2021/5/7
 */
public class AppViewModel extends ViewModel {

    // -2代表完全没有初始化过
    private long mTeacherId = -2;

    private final SharedPreferences sp = App.mApplicationContext
            .getSharedPreferences(UserSPConstant.FILE_NAME, Context.MODE_PRIVATE);

    /**
     * 获取mTeacherId的值
     */
    public void setTeacherId(long teacherId) {
        this.mTeacherId = teacherId;
    }

    /**
     * 获取teacherId
     * 会去判断是否为-2，如果是则代表没有初始化过，然后去进行初始化。
     * 初始化会先从sp里面去读，有值则返回，没有值则返回-1，代表当前没有登录
     */
    public long getTeacherId() {
        if (mTeacherId == -2) {
            mTeacherId = sp.getLong(UserSPConstant.TEACHER_USER_ID, -1L);
        }
        return mTeacherId;
    }
}
