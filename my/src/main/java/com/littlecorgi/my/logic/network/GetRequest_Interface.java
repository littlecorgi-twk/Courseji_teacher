package com.littlecorgi.my.logic.network;

import com.littlecorgi.my.logic.model.MyMessage;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetRequest_Interface {

    // 发送建议：

    // @FieldMap与@Field的作用一致，可以用于添加多个不确定的参数，类似@QueryMap，Map的key作为表单的键，Map的value作为表单的值
    @FormUrlEncoded
    @POST("user/emails")
    Call<ResponseBody> SendAdviceData(@FieldMap Map<String, Object> map);

    // 得到个人信息的：
    @GET("user")
    Call<MyMessage> getMyDate();

    // 上传修改的个人信息
    @FormUrlEncoded
    @POST("user/emails")
    Call<ResponseBody> SendMyMessage(@FieldMap Map<String, Object> map);
}
