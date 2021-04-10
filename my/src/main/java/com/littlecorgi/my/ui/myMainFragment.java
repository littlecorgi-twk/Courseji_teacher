package com.littlecorgi.my.ui;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.littlecorgi.my.R;
import com.littlecorgi.my.logic.model.MyMessage;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;


import org.jetbrains.annotations.NotNull;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;
import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;
import static com.littlecorgi.my.logic.network.RetrofitHelp.getMyMessage;
import static com.littlecorgi.my.ui.about.aboutActivity.StartAboutActivity;
import static com.littlecorgi.my.ui.message.messageActivity.StartMessageActivity;

public class myMainFragment extends Fragment {
    /*
       未完成的：在这里要完成学生个人信息的获取吧信息填充到myMessage中
     */
    private View view;
    private MyMessage myMessage;
    public final static String sharedPreferencesFile = "myMessageData";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initClick();
    }
    private void initView() {
        initBarColor();
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            boolean refreshData = getData();
            refreshlayout.finishRefresh(refreshData);
        });
        initImage();
    }
    private void initBarColor() {
        setWindowStatusBarColor(getActivity(),R.color.blue);
    }
    private void initImage() {
         /*
        设置背景？
         */
        AppCompatImageView myAboutBg = view.findViewById(R.id.my_about_bg);

    }
    private void initClick() {
        ConstraintLayout messageLayout = view.findViewById(R.id.my_message);
        ConstraintLayout aboutLayout = view.findViewById(R.id.my_about);
        messageLayout.setOnClickListener(v -> StartMessageActivity(getContext(),myMessage));
        aboutLayout.setOnClickListener(v -> StartAboutActivity(getContext(),myMessage));
    }
    private void initData() {
        myMessage = new MyMessage();
        //先从本地获取本地没有从服务器获取
        getLocalData();
        if(!getLocalData()){
            getData();
        }
        //图像
        AppCompatImageView imageView = view.findViewById(R.id.my_picture);
        imageView.setImageResource(myMessage.getMyImage());
        //名字
        AppCompatTextView name = view.findViewById(R.id.my_name);
        name.setText(myMessage.getName());
        //专业
        AppCompatTextView professional = view.findViewById(R.id.my_professional);
        professional.setText(myMessage.getProfessional());
    }
    private boolean getData() {
        final Boolean[] isSuccess = {false};
        Call<MyMessage> call = getMyMessage();
        call.enqueue(new Callback<MyMessage>() {
            @Override
            public void onResponse(@NotNull Call<MyMessage> call, @NotNull Response<MyMessage> response) {
                //在这设置数据吧得到的数据先用SharedPreferences数据库保存
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesFile,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                 /*
                //设置数据
                editor.putInt("image",response.body().getMyImage());
                 */
                editor.apply();
                getLocalData();
                isSuccess[0] = true;
            }
            @Override
            public void onFailure(@NotNull Call<MyMessage> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "加载数据失败，请检查网络", Toast.LENGTH_LONG).show();
            }
        });
        return isSuccess[0];
    }
    private boolean getLocalData(){
        /*
        从本地获取数据
        SharedPreferences保存路径
        /data/data/com.littlecorgi.courseji/shared_prefs/myMessageData.xml
         */
        @SuppressLint("SdCardPath") String sharedPreferencesPath = "/data/data/com.littlecorgi.courseji/shared_prefs/" +
                sharedPreferencesFile+".xml";
        File File = new File(sharedPreferencesPath);
        if(File.exists()){
            String emptyData = "";
            int emptyImage = 0;
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE);
            myMessage.setMyImage(sharedPreferences.getInt("image",emptyImage));
            myMessage.setName(sharedPreferences.getString("name",emptyData));
            myMessage.setId(sharedPreferences.getString("id",emptyData));
            myMessage.setGender(sharedPreferences.getString("gender",emptyData));
            myMessage.setProfessional(sharedPreferences.getString("professional",emptyData));
            myMessage.setDescribe(sharedPreferences.getString("describe",emptyData));
            myMessage.setVersion(sharedPreferences.getString("version",emptyData));
            myMessage.setUpdate(sharedPreferences.getString("update",emptyData));
            return true;
        }
        else{
            return false;
        }
    }
}
