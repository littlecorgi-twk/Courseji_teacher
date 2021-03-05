package com.littlecorgi.my.logic.network;

import com.littlecorgi.my.logic.model.MyMessage;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelp {
  /*
        建议： 需要修改路径
  */
  public static Call<ResponseBody> adviceRetrofit(Map<String, Object> map) {
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.uomg.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
    return request.SendAdviceData(map);
  }

  /*
         获取个人信息： 需要修改路径
  */

  public static Call<MyMessage> getMyMessage() {
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.uomg.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
    return request.getMyDate();
  }
  /*
         上传个人信息： 需要修改路径
  */

  public static Call<ResponseBody> messageRetrofit(Map<String, Object> map) {
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.uomg.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
    return request.SendAdviceData(map);
  }
}
