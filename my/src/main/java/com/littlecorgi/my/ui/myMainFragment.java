package com.littlecorgi.my.ui;

import static android.content.Context.MODE_PRIVATE;
import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;
import static com.littlecorgi.my.logic.network.RetrofitHelp.getMyMessage;
import static com.littlecorgi.my.ui.about.AboutActivity.startAboutActivity;
import static com.littlecorgi.my.ui.message.MessageActivity.startMessageActivity;

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
import java.io.File;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * My 主页
 */
public class MyMainFragment extends Fragment {

    /*
      未完成的：在这里要完成学生个人信息的获取吧信息填充到myMessage中
    */
    private View mView;
    private MyMessage mMyMessage;
    public static final String SHARED_PREFERENCES_FILE = "myMessageData";

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initClick();
    }

    private void initView() {
        initBarColor();
        RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(requireContext()));
        refreshLayout.setOnRefreshListener(layout -> {
            boolean refreshData = getData();
            layout.finishRefresh(refreshData);
        });
        initImage();
    }

    private void initBarColor() {
        setWindowStatusBarColor(getActivity(), R.color.blue);
    }

    private void initImage() {
        /*
        设置背景？
         */
        AppCompatImageView myAboutBg = mView.findViewById(R.id.my_about_bg);
    }

    private void initClick() {
        ConstraintLayout messageLayout = mView.findViewById(R.id.my_message);
        ConstraintLayout aboutLayout = mView.findViewById(R.id.my_about);
        messageLayout.setOnClickListener(v -> startMessageActivity(getContext(), mMyMessage));
        aboutLayout.setOnClickListener(v -> startAboutActivity(getContext(), mMyMessage));
    }

    private void initData() {
        mMyMessage = new MyMessage();
        // 先从本地获取本地没有从服务器获取
        getLocalData();
        if (!getLocalData()) {
            getData();
        }
        // 图像
        AppCompatImageView imageView = mView.findViewById(R.id.my_picture);
        imageView.setImageResource(mMyMessage.getMyImage());
        // 名字
        AppCompatTextView name = mView.findViewById(R.id.my_name);
        name.setText(mMyMessage.getName());
        // 专业
        AppCompatTextView professional = mView.findViewById(R.id.my_professional);
        professional.setText(mMyMessage.getProfessional());
    }

    private boolean getData() {
        final Boolean[] isSuccess = {false};
        Call<MyMessage> call = getMyMessage();
        call.enqueue(
            new Callback<MyMessage>() {
                @Override
                public void onResponse(
                    @NotNull Call<MyMessage> call, @NotNull Response<MyMessage> response) {
                    // 在这设置数据吧得到的数据先用SharedPreferences数据库保存
                    SharedPreferences sharedPreferences = requireActivity()
                        .getSharedPreferences(SHARED_PREFERENCES_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // //设置数据
                    // editor.putInt("image", response.body().getMyImage());

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

    /**
     * 从本地获取数据 SharedPreferences保存路径
     * /data/data/com.littlecorgi.courseji/shared_prefs/myMessageData.xml
     */
    private boolean getLocalData() {
        @SuppressLint("SdCardPath")
        String sharedPreferencesPath = "/data/data/com.littlecorgi.courseji/shared_prefs/"
            + SHARED_PREFERENCES_FILE + ".xml";
        File file = new File(sharedPreferencesPath);
        if (file.exists()) {
            String emptyData = "";
            int emptyImage = 0;
            SharedPreferences sharedPreferences =
                requireActivity().getSharedPreferences(SHARED_PREFERENCES_FILE, MODE_PRIVATE);
            mMyMessage.setMyImage(sharedPreferences.getInt("image", emptyImage));
            mMyMessage.setName(sharedPreferences.getString("name", emptyData));
            mMyMessage.setId(sharedPreferences.getString("id", emptyData));
            mMyMessage.setGender(sharedPreferences.getString("gender", emptyData));
            mMyMessage.setProfessional(sharedPreferences.getString("professional", emptyData));
            mMyMessage.setDescribe(sharedPreferences.getString("describe", emptyData));
            mMyMessage.setVersion(sharedPreferences.getString("version", emptyData));
            mMyMessage.setUpdate(sharedPreferences.getString("update", emptyData));
            return true;
        } else {
            return false;
        }
    }
}
