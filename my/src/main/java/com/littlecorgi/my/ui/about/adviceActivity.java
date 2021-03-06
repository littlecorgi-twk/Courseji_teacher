package com.littlecorgi.my.ui.about;

import static com.littlecorgi.my.logic.dao.AndPermissionHelp.andPermission;
import static com.littlecorgi.my.logic.dao.BTWHelp.dialogBtw;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.choicePhoto;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.choiceVideo;
import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;
import static com.littlecorgi.my.logic.network.RetrofitHelp.adviceRetrofit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.my.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adviceActivity extends BaseActivity implements View.OnClickListener {
  /*
     未完成：sendAdvice()把建议上传到服务器，需要修改路径
  */

    private static final int PHOTO = 1;
    private AppCompatEditText editText;

    private RecyclerView recyclerView;
    private ArrayList<String> allSelectList; // 所有的图片集合
    private final List<LocalMedia> selectList = new ArrayList<>();
    private MyAdapt adapt;
    private Dialog dialog = null;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_about_advice);
        initView();
    }

    private void initView() {
        AppCompatTextView returnButton = findViewById(R.id.my_advice_returnButton);
        editText = findViewById(R.id.my_advice_editView);
        recyclerView = findViewById(R.id.my_advice_RecyclerView);
        AppCompatTextView getPicture = findViewById(R.id.my_advice_getPicture);
        AppCompatButton sureButton = findViewById(R.id.my_advice_SureButton);
        returnButton.setOnClickListener(this);
        getPicture.setOnClickListener(this);
        sureButton.setOnClickListener(this);
        initBarColor();
        initPermission();
        initRecyclerView();
        initBtw();
    }

    private void initBarColor() {
        setWindowStatusBarColor(this, R.color.blue);
    }

    private void initBtw() {

        view = View.inflate(this, R.layout.my_advice_btw, null);
        // 点击事件：
        AppCompatTextView photo = view.findViewById(R.id.advice_btw_photo);
        AppCompatTextView video = view.findViewById(R.id.advice_btw_video);
        AppCompatTextView cancel = view.findViewById(R.id.picture_btw_cancel);
        photo.setOnClickListener(v -> UploadPhoto());
        video.setOnClickListener(v -> UploadVideo());
        cancel.setOnClickListener(v -> dialog.dismiss());
    }

    /*
       使用AndPermission获取动态权限
    */
    private void initPermission() {
        andPermission(this, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initRecyclerView() {

        allSelectList = new ArrayList<>();
        adapt = new MyAdapt(R.layout.my_advice_item_gallery, allSelectList, adviceActivity.this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapt);

        adapt.addChildClickViewIds(R.id.my_im_show_gallery, R.id.my_im_del);

        adapt.setOnItemChildClickListener(
                (adapter, view, position) -> {
                    if (allSelectList.size() > 0) {
                        if (view.getId() == R.id.my_im_show_gallery) {

                            PictureSelector.create(adviceActivity.this)
                                    .externalPicturePreview(position, selectList, 0);

                        } else if (view.getId() == R.id.my_im_del) {
                            allSelectList.remove(position);
                            selectList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        // 此处不能使用switch，因为作为library，他的id并不是常量，所以Android并不建议在switch中直接使用id，
        // 可以使用view.setOnClickListener替换，或者使用if-else
        int id = v.getId();
        if (id == R.id.my_advice_returnButton) {
            finish();
        } else if (id == R.id.my_advice_getPicture) {
            showBtw();
        } else if (id == R.id.my_advice_SureButton) {
            sendAdvice();
        }
    }

    private void sendAdvice() {

        String Title = Objects.requireNonNull(editText.getText()).toString();
        if (Title.equals("")) {
            Toast.makeText(adviceActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("advice_title", Title);
            map.put("advice_imagePath", allSelectList);
            Call<ResponseBody> call = adviceRetrofit(map);
            call.enqueue(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(
                                @NotNull Call<ResponseBody> call,
                                @NotNull Response<ResponseBody> response) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(
                                    adviceActivity.this);
                            dialog.setMessage("您的建议我们已经收到了！"); // 设置内容
                            dialog.setCancelable(true); // 设置不可用Back键关闭对话框
                            // 设置确定按钮的点击事件
                            dialog.setPositiveButton("退出", (dialogInterface, i) -> finish());
                            // 设置取消按钮的点击事件
                            dialog.setNegativeButton(
                                    "再写一个",
                                    (dialogInterface, i) -> {
                                        // 清除缓存文件
                                        PictureFileUtils.deleteAllCacheDirFile(adviceActivity.this);
                                        allSelectList.clear();
                                        editText.setText("");
                                        adapt.notifyDataSetChanged();
                                    });
                            dialog.show();
                        }

                        @Override
                        public void onFailure(@NotNull Call<ResponseBody> call,
                                @NotNull Throwable t) {
                            Toast.makeText(adviceActivity.this, "", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void showBtw() {
        // 使用Dialog弹出
        if (dialog == null) {
            dialog = dialogBtw(view, this);
        } else {
            dialog.show();
        }
    }

    private void UploadPhoto() {
        choicePhoto(adviceActivity.this, PHOTO);
        dialog.dismiss(); // 关闭下方的弹窗
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO) {
                List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(localMedia);
                showSelectPic(localMedia);
            }
        }
    }

    private void showSelectPic(List<LocalMedia> localMedia) {
        for (int i = 0; i < localMedia.size(); i++) {
            String path = null;
            // 判断是视频还是图片：视频的获取路径不需要判断是否是10.0以上，否则报错
            int type = localMedia.get(i).getChooseModel();
            if (type == PictureConfig.TYPE_IMAGE) {
                // 判断是否10.0以上
                if (Build.VERSION.SDK_INT >= 29) {
                    path = localMedia.get(i).getAndroidQToPath();
                } else {
                    path = localMedia.get(i).getPath();
                }
            } else if (type == PictureConfig.TYPE_VIDEO) {
                path = localMedia.get(i).getPath();
            }
            if (path != null) {
                allSelectList.add(path);
            }
        }
        adapt.notifyDataSetChanged();
    }

    private void UploadVideo() {
        choiceVideo(adviceActivity.this, PHOTO);
        dialog.dismiss(); // 关闭下方的弹窗
    }

    public static void StartAdviceActivity(Context context) {
        Intent intent = new Intent(context, adviceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清除缓存文件
        PictureFileUtils.deleteAllCacheDirFile(adviceActivity.this);
    }
}
