package com.littlecorgi.middle.logic;

import com.littlecorgi.commonlib.logic.TencentServerRetrofitKt;
import com.littlecorgi.middle.logic.model.AllClassResponse;
import com.littlecorgi.middle.logic.network.ClassInterface;
import retrofit2.Call;

/**
 * @author littlecorgi 2021/5/6
 */
public class ClassRepository {

    private static ClassInterface getInstance() {
        return TencentServerRetrofitKt.getTencentCloudRetrofit().create(ClassInterface.class);
    }

    public static Call<AllClassResponse> getAllClassFromTeacher(long teacherId) {
        return getInstance().getAllClassFromTeacher(teacherId);
    }
}
