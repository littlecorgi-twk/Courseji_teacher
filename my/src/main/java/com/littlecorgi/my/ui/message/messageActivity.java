package com.littlecorgi.my.ui.message;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bumptech.glide.Glide;
import com.contrarywind.view.WheelView;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.my.R;
import com.littlecorgi.my.logic.model.MessageChange;
import com.littlecorgi.my.logic.model.MyMessage;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.littlecorgi.my.logic.dao.BTWHelp.dialogBtw;
import static com.littlecorgi.my.logic.dao.PickerViewHelp.getNationalList;

import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.OPEN_ALBUM;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.OPEN_CAMERA;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.openAlbum;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.openCamera;
import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;
import static com.littlecorgi.my.logic.network.RetrofitHelp.messageRetrofit;
import static com.littlecorgi.my.ui.message.describeActivity.REQUEST_CODE;
import static com.littlecorgi.my.ui.message.describeActivity.REQUEST_DATA;
import static com.littlecorgi.my.ui.message.describeActivity.StartDescribeActivity;
import static com.littlecorgi.my.ui.message.originalActivity.StartOriginalActivity;

public class messageActivity extends BaseActivity {


    private AppCompatTextView returnButton;
    private AppCompatButton sureButton;
    private ConstraintLayout pictureLayout;
    private AppCompatImageView pictureView;
    private ConstraintLayout nameLayout;
    private AppCompatTextView nameTitle;

    private ConstraintLayout idLayout;
    private AppCompatTextView idTitle;

    private ConstraintLayout genderLayout;
    private AppCompatTextView genderTitle;
    private AppCompatEditText professionalTitle;
    private ConstraintLayout describeLayout;
    private AppCompatTextView describeTitle;

    private ConstraintLayout nationalLayout;
    private AppCompatTextView nationalTitle;

    private MyMessage myMessage;
    private MessageChange messageChange;


    private Dialog pictureDialog;
    private Dialog genderDialog;
    private Dialog nationalDialog;
    private boolean isChanged = false;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode == REQUEST_CODE){
                assert data != null;
                String describeData = data.getStringExtra(REQUEST_DATA);
                describeTitle.setText(describeData);
                messageChange.setDescribe(describeData);
                isChanged = true;
            }
            if(requestCode == OPEN_CAMERA ||requestCode == OPEN_ALBUM){
                String path = getImagePath(data);
                Glide.with(this)
                        .load(path).into(pictureView);
                messageChange.setMyImagePath(path);
            }
        }
    }

    private String getImagePath(Intent data) {

        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        if (Build.VERSION.SDK_INT >= 29) {
            return  localMedia.get(0).getAndroidQToPath();
        } else {
             return localMedia.get(0).getPath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        //初始化view
        initView();
        //初始化data
        initData();
    }
    private void initView() {
        initBarColor();
        initFind();
        initImageView();
        initNameView();
        initIdVIew();
        initGenderView();
        initDescribeView();
        initNationalView();
        initClick();
    }
    private void initBarColor() {
        setWindowStatusBarColor(this,R.color.blue);
    }
    private void initClick() {
        returnButton.setOnClickListener(v -> finish());
        sureButton.setOnClickListener(v -> saveMessage());
    }
    private void saveMessage() {
        if(!Objects.requireNonNull(professionalTitle.getText()).toString().equals(myMessage.getProfessional()))
            isChanged = true;
        if(isChanged){
            messageChange.setProfessional(professionalTitle.getText().toString());
            Map<String, Object> map = new HashMap<>();
            map.put("image",messageChange.getMyImagePath());
            map.put("gender",messageChange.getGender());
            map.put("professional",messageChange.getProfessional());
            map.put("describe",messageChange.getDescribe());
            map.put("national",messageChange.getNational());
            Call<ResponseBody> call = messageRetrofit(map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {

                    Toast.makeText(messageActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                    isChanged = false;
                }
                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

                    Toast.makeText(messageActivity.this, "保存失败，过会在试吧", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void initNationalView() {

        View nationalBtw = View.inflate(this,R.layout.my_national_btw,null);
        WheelView wheelView = nationalBtw.findViewById(R.id.national_blw_wheelView);
        List<String> list = getNationalList();
        wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        wheelView.setCurrentItem(0);
        nationalLayout.setOnClickListener(v -> {
            if(nationalDialog!=null){
                nationalDialog.show();
            }else {
                nationalDialog = dialogBtw(nationalBtw,this);
            }
        });
        AppCompatButton cancel = nationalBtw.findViewById(R.id.national_blw_cancelButton);
        AppCompatButton sure = nationalBtw.findViewById(R.id.national_blw_sureButton);
        cancel.setOnClickListener(v -> nationalDialog.dismiss());
        sure.setOnClickListener(v -> {
           nationalTitle.setText(list.get(wheelView.getCurrentItem()));
           messageChange.setNational(list.get(wheelView.getCurrentItem()));
            isChanged = true;
           nationalDialog.dismiss();
        });

    }
    private void initDescribeView() {
        describeLayout.setOnClickListener(v -> StartDescribeActivity(this)); }

    private void initFind() {
        returnButton = findViewById(R.id.my_message_returnButton);
        sureButton = findViewById(R.id.my_message_SureButton);

        pictureLayout = findViewById(R.id.my_message_picture);
        pictureView = findViewById(R.id.my_message_ImageView);

        nameLayout = findViewById(R.id.my_message_name);
        nameTitle = findViewById(R.id.my_message_nameTitle);

        idLayout = findViewById(R.id.my_message_id);
        idTitle = findViewById(R.id.my_message_idTitle);

        genderLayout = findViewById(R.id.my_message_gender);
        genderTitle = findViewById(R.id.my_message_genderTitle);

        professionalTitle = findViewById(R.id.my_message_professionalTitle);

        describeLayout = findViewById(R.id.my_message_Describe);
        describeTitle = findViewById(R.id.my_message_DescribeTitle);

        nationalLayout = findViewById(R.id.my_message_national);
        nationalTitle = findViewById(R.id.my_message_nationalTitle);
    }

    private void initGenderView() {

        View genderBtw = View.inflate(this,R.layout.my_gender_btw,null);
        genderLayout.setOnClickListener(v -> {
            if(genderDialog!=null){
                genderDialog.show();
            }else{
                genderDialog = dialogBtw(genderBtw,this);
            }
        });
        AppCompatTextView man = genderBtw.findViewById(R.id.gender_btw_man);
        AppCompatTextView woman = genderBtw.findViewById(R.id.gender_btw_woman);
        AppCompatTextView cancel = genderBtw.findViewById(R.id.gender_btw_cancel);
        man.setOnClickListener(v -> {
            genderTitle.setText("男");
            messageChange.setGender("男");
            isChanged = true;
            genderDialog.dismiss();
        });
        woman.setOnClickListener(v -> {
            genderTitle.setText("女");
            messageChange.setGender("女");
            isChanged = true;
            genderDialog.dismiss();
        });
        cancel.setOnClickListener(v -> genderDialog.dismiss());
    }

    private void initIdVIew() {
        idLayout.setOnClickListener(v -> Toast.makeText(messageActivity.this,"不可修改",Toast.LENGTH_LONG).show() );
    }

    private void initNameView() {
        nameLayout.setOnClickListener(v -> Toast.makeText(messageActivity.this,"不可修改",Toast.LENGTH_LONG).show());
    }

    private void initImageView() {

        View imageBtw = View.inflate(this, R.layout.my_picture_btw, null);

        AppCompatTextView photo = imageBtw.findViewById(R.id.picture_btw_photo);
        AppCompatTextView album = imageBtw.findViewById(R.id.picture_btw_album);
        AppCompatTextView original = imageBtw.findViewById(R.id.picture_btw_original);
        AppCompatTextView cancel = imageBtw.findViewById(R.id.picture_btw_cancel);
        photo.setOnClickListener(v1 -> photoHelp());
        album.setOnClickListener(v1 -> albumHelp());
        original.setOnClickListener(v1 -> originalHelp());
        cancel.setOnClickListener(v1 -> pictureDialog.dismiss());

        pictureLayout.setOnClickListener(v -> {
            if(pictureDialog!=null){
                pictureDialog.show();
            }
            else{
                pictureDialog = dialogBtw(imageBtw, this);
            }
        });
    }

    private void albumHelp() {
         /*
        打开相册
         */
        openAlbum(this,OPEN_ALBUM);
        pictureDialog.dismiss();

    }

    private void photoHelp() {
        /*
        拍照
         */
        openCamera(this,OPEN_CAMERA);
        pictureDialog.dismiss();
    }

    private void originalHelp() {
        /*
        查看大图
         */
        if(messageChange.getMyImagePath()==null){
            StartOriginalActivity(this,myMessage.getImagePath());
        }
        else{
            StartOriginalActivity(this,messageChange.getMyImagePath());
        }

        pictureDialog.dismiss();
    }
    private void initData() {
        Intent intent = getIntent();
        myMessage = (MyMessage)intent.getSerializableExtra("myMessage");
        assert myMessage != null;
       // pictureView.setImageResource(myMessage.getMyImage());
        Glide.with(this)
                .load(myMessage.getImagePath()).into(pictureView);
        nameTitle.setText(myMessage.getName());
        idTitle.setText(myMessage.getId());
        genderTitle.setText(myMessage.getGender());
        professionalTitle.setText(myMessage.getProfessional());
        describeTitle.setText(myMessage.getDescribe());
        nationalTitle.setText(myMessage.getNational());
        messageChange = new MessageChange();
    }
    public static void StartMessageActivity(Context context, MyMessage myMessage) {
        Intent intent = new Intent(context, messageActivity.class);
        intent.putExtra("myMessage", myMessage);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清除所有缓存 例如：压缩、裁剪、视频、音频所生成的临时文件
        PictureFileUtils.deleteAllCacheDirFile(this);
    }
}