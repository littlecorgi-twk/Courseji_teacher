package com.littlecorgi.my.ui.message;

import static com.littlecorgi.my.logic.dao.BTWHelp.dialogBtw;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.OPEN_ALBUM;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.OPEN_CAMERA;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.openAlbum;
import static com.littlecorgi.my.logic.dao.PictureSelectorHelp.openCamera;
import static com.littlecorgi.my.logic.dao.WindowHelp.setWindowStatusBarColor;
import static com.littlecorgi.my.ui.message.OriginalActivity.startOriginalActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import com.littlecorgi.commonlib.BaseActivity;
import com.littlecorgi.commonlib.uploadfiles.logic.FileRetrofitRepository;
import com.littlecorgi.commonlib.uploadfiles.logic.UploadFileResponse;
import com.littlecorgi.commonlib.util.DialogUtil;
import com.littlecorgi.commonlib.util.UserSPConstant;
import com.littlecorgi.my.R;
import com.littlecorgi.my.logic.model.MessageChange;
import com.littlecorgi.my.logic.model.Teacher;
import com.littlecorgi.my.logic.model.TeacherBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ????????????
 */
public class MessageActivity extends BaseActivity {

    private Toolbar mToolbar;
    private AppCompatButton mSureButton;
    private ConstraintLayout mPictureLayout;
    private AppCompatImageView mPictureView;
    private ConstraintLayout mNameLayout;
    private AppCompatTextView mNameTitle;

    private ConstraintLayout mIdLayout;
    private AppCompatTextView mIdTitle;

    private ConstraintLayout mPhoneLayout;
    private AppCompatTextView mPhoneTitle;

    private TeacherBean mMyInfo;
    private MessageChange mMessageChange;

    private Dialog mPictureDialog;
    private Dialog mPhoneDialog;
    private boolean mIsChanged = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == OPEN_CAMERA || requestCode == OPEN_ALBUM) {
                String path = getImagePath(data);
                Glide.with(this).load(path).into(mPictureView);
                mMessageChange.setMyImagePath(path);
                mIsChanged = true;
            }
        }
    }

    private String getImagePath(Intent data) {
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        if (Build.VERSION.SDK_INT >= 29) {
            return localMedia.get(0).getAndroidQToPath();
        } else {
            return localMedia.get(0).getPath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        // ?????????view
        initView();
        // ?????????data
        initData();
    }

    private void initView() {
        initBarColor();
        initFind();
        initImageView();
        initNameView();
        initIdVIew();
        initPhoneView();
        initClick();
    }

    private void initBarColor() {
        setWindowStatusBarColor(this, R.color.blue);
    }

    private void initClick() {
        mToolbar.setNavigationOnClickListener(v -> finish());
        mSureButton.setOnClickListener(v -> saveMessage());
    }

    private void saveMessage() {
        if (mIsChanged) {
            SharedPreferences sp = getSharedPreferences(UserSPConstant.FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            if (mMessageChange.getMyImagePath() != null) {
                // ??????????????????Dialog
                Dialog progressDialog = DialogUtil.writeLoadingDialog(this, false, "???????????????");
                progressDialog.show();
                //??????????????????????????????????????????????????????????????????
                progressDialog.setCanceledOnTouchOutside(true);
                FileRetrofitRepository.INSTANCE
                        .getUploadCall(new File(mMessageChange.getMyImagePath()))
                        .enqueue(new Callback<UploadFileResponse>() {
                            @Override
                            public void onResponse(
                                    @NonNull Call<UploadFileResponse> call,
                                    @NonNull Response<UploadFileResponse> response) {
                                progressDialog.cancel();
                                assert response.body() != null;
                                UploadFileResponse uploadFileResponse = response.body();
                                Log.d("MessageActivity",
                                        "onResponse: json= ??????????????????: " + response.body().toString());
                                mMyInfo.setAvatar(uploadFileResponse.getData());
                                sp.edit().putString(UserSPConstant.TEACHER_AVATAR,
                                        uploadFileResponse.getData()).apply();
                            }

                            @Override
                            public void onFailure(
                                    @NonNull Call<UploadFileResponse> call, @NonNull
                                    Throwable t) {
                                t.printStackTrace();
                                Log.d("MessageActivity", "onFailure: ????????????");
                                showErrorToast(MessageActivity.this, "????????????",
                                        true, Toast.LENGTH_SHORT);
                            }
                        });
            }
            if (mMessageChange.getPhone() != null) {
                mMyInfo.setPhone(mMessageChange.getPhone());
                editor.putString(UserSPConstant.TEACHER_PHONE, mMessageChange.getPhone());
            }
            editor.apply();
            finish();
        }
    }

    private void initPhoneView() {
        View phoneBtw = View.inflate(this, R.layout.my_phone_btw, null);
        AppCompatEditText editText = phoneBtw.findViewById(R.id.phone_edit_text);
        final boolean[] isPhoneOk = {false};
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 11) {
                    editText.setError("???????????????11???");
                    isPhoneOk[0] = false;
                    return;
                }
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                if (!pattern.matcher(s).matches()) {
                    editText.setError("???????????????????????????????????????");
                    isPhoneOk[0] = false;
                    return;
                }
                isPhoneOk[0] = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mPhoneLayout.setOnClickListener(v -> {
            if (mPhoneDialog != null) {
                mPhoneDialog.show();
            } else {
                mPhoneDialog = dialogBtw(phoneBtw, this);
            }
        });
        AppCompatButton cancel = phoneBtw.findViewById(R.id.phone_blw_cancelButton);
        AppCompatButton sure = phoneBtw.findViewById(R.id.phone_blw_sureButton);
        cancel.setOnClickListener(v -> mPhoneDialog.dismiss());
        sure.setOnClickListener(v -> {
            if (isPhoneOk[0]) {
                String newPhone = Objects.requireNonNull(editText.getText()).toString();
                mMessageChange.setPhone(newPhone);
                mIsChanged = true;
                mPhoneDialog.dismiss();
                mPhoneTitle.setText(newPhone);
            } else {
                showErrorToast(MessageActivity.this, "??????????????????", true, Toast.LENGTH_SHORT);
            }
        });

        // // ??????
        // View nationalBtw = View.inflate(this, R.layout.my_national_btw, null);
        // WheelView wheelView = nationalBtw.findViewById(R.id.national_blw_wheelView);
        // List<String> list = getNationalList();
        // wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        // wheelView.setCurrentItem(0);
        // AppCompatButton cancel = nationalBtw.findViewById(R.id.national_blw_cancelButton);
        // AppCompatButton sure = nationalBtw.findViewById(R.id.national_blw_sureButton);
        // cancel.setOnClickListener(v -> mPhoneDialog.dismiss());
        // sure.setOnClickListener(v -> {
        //     mMessageChange.setNational(list.get(wheelView.getCurrentItem()));
        //     mIsChanged = true;
        //     mPhoneDialog.dismiss();
        // });
    }

    private void initFind() {
        mToolbar = findViewById(R.id.my_message_toolbar);
        mSureButton = findViewById(R.id.my_message_SureButton);

        mPictureLayout = findViewById(R.id.my_message_picture);
        mPictureView = findViewById(R.id.my_message_ImageView);

        mNameLayout = findViewById(R.id.my_message_name);
        mNameTitle = findViewById(R.id.my_message_nameTitle);

        mIdLayout = findViewById(R.id.my_message_id);
        mIdTitle = findViewById(R.id.my_message_idTitle);

        mPhoneLayout = findViewById(R.id.my_message_phone);
        mPhoneTitle = findViewById(R.id.my_message_phoneTitle);
    }

    private void initIdVIew() {
        mIdLayout.setOnClickListener(v ->
                Toast.makeText(MessageActivity.this, "????????????", Toast.LENGTH_LONG).show());
    }

    private void initNameView() {
        mNameLayout.setOnClickListener(v ->
                Toast.makeText(MessageActivity.this, "????????????", Toast.LENGTH_LONG).show());
    }

    private void initImageView() {
        View imageBtw = View.inflate(this, R.layout.my_picture_btw, null);
        final AppCompatTextView photo = imageBtw.findViewById(R.id.picture_btw_photo);
        final AppCompatTextView album = imageBtw.findViewById(R.id.picture_btw_album);
        final AppCompatTextView original = imageBtw.findViewById(R.id.picture_btw_original);
        final AppCompatTextView cancel = imageBtw.findViewById(R.id.picture_btw_cancel);
        photo.setOnClickListener(v1 -> photoHelp());
        album.setOnClickListener(v1 -> albumHelp());
        original.setOnClickListener(v1 -> originalHelp());
        cancel.setOnClickListener(v1 -> mPictureDialog.dismiss());

        mPictureLayout.setOnClickListener(v -> {
            if (mPictureDialog != null) {
                mPictureDialog.show();
            } else {
                mPictureDialog = dialogBtw(imageBtw, this);
            }
        });
    }

    private void albumHelp() {
        /*
        ????????????
         */
        openAlbum(this, OPEN_ALBUM);
        mPictureDialog.dismiss();
    }

    private void photoHelp() {
        /*
        ??????
         */
        openCamera(this, OPEN_CAMERA);
        mPictureDialog.dismiss();
    }

    private void originalHelp() {
        /*
        ????????????
         */
        if (mMessageChange.getMyImagePath() == null) {
            startOriginalActivity(this, mMyInfo.getAvatar());
        } else {
            startOriginalActivity(this, mMessageChange.getMyImagePath());
        }
        mPictureDialog.dismiss();
    }

    private void initData() {
        Intent intent = getIntent();
        mMyInfo = ((Teacher) intent.getSerializableExtra("teacherInfo")).getData();
        assert mMyInfo != null;
        Glide.with(this).load(mMyInfo.getAvatar()).into(mPictureView);
        mNameTitle.setText(mMyInfo.getName());
        mPhoneTitle.setText(mMyInfo.getPhone());
        mMessageChange = new MessageChange();
    }

    /**
     * ?????????MessageActivity
     *
     * @param context ?????????
     * @param teacher ????????????
     */
    public static void startMessageActivity(Context context, Teacher teacher) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("teacherInfo", teacher);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ?????????????????? ??????????????????????????????????????????????????????????????????
        PictureFileUtils.deleteAllCacheDirFile(this);
    }
}

