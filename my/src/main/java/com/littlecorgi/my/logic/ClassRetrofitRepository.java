package com.littlecorgi.my.logic;

import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import com.littlecorgi.my.logic.model.AllClassResponse;
import com.littlecorgi.my.logic.model.CreateClassResponse;
import com.littlecorgi.my.logic.network.ClassInterface;
import retrofit2.Call;

/**
 * 班级 Retrofit封装类
 *
 * @author littlecorgi 2021/5/4
 */
public class ClassRetrofitRepository {

    /**
     * 获取这个教师的所有班级
     *
     * @param teacherId 教师id
     */
    public static Call<AllClassResponse> getAllClassFromTeacher(long teacherId) {
        ClassInterface classInterface =
                TencentServerRetrofitKt.getTencentCloudRetrofit()
                        .create(ClassInterface.class);
        // 执行请求
        return classInterface.getAllClassFromTeacher(teacherId);
    }

    /**
     * 创建班级
     *
     * @param teacherId 教师id
     * @param className 班级名
     */
    public static Call<CreateClassResponse> createClass(long teacherId, String className) {
        ClassInterface classInterface = TencentServerRetrofitKt.getTencentCloudRetrofit()
                .create(ClassInterface.class);
        return classInterface.createClass(teacherId, className);
    }
}
