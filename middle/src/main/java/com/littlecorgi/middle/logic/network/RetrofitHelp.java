package com.littlecorgi.middle.logic.network;

import com.littlecorgi.middle.logic.model.ItemData;
import com.littlecorgi.middle.logic.model.SignResult;
import java.io.File;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit请求工具类.
 */
public class RetrofitHelp {

    /**
     * 建议： 需要修改路径.
     */
    public static Call<ResponseBody> signInRetrofit(Map<String, Object> map) {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://api.uomg.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        return request.signIn(map);
    }

    /**
     * 发送签到情况,只在签到成功后发送.
     */
    public static Call<SignResult> postStudentSign(File file) {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://api.uomg.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        return request.getStudentSign(file);
    }

    /**
     * 接收所以的签到.
     */
    public static Call<ItemData> getAllSign() {

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://api.uomg.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        return request.getAllSign();
    }
}
