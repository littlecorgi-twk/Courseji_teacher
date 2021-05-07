package com.littlecorgi.leave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.littlecorgi.leave.databinding.ActivityPassLeaveBinding;
import com.littlecorgi.leave.logic.LeaveRepository;
import com.littlecorgi.leave.logic.model.ApproveLeaveRequest;
import com.littlecorgi.leave.logic.model.ApproveLeaveResponse;
import com.littlecorgi.leave.ui.RecyclerItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author littlecorgi 2021/05/07
 */
public class PassLeaveActivity extends AppCompatActivity {


    private static final String TAG = "PassLeaveActivity";

    private RecyclerItem mRecyclerItem;
    private ActivityPassLeaveBinding mBinding;
    private ApproveLeaveRequest mRequest;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pass_leave);

        if (getIntent() != null) {
            mRecyclerItem = (RecyclerItem) getIntent().getSerializableExtra("recyclerItem");
        }

        initView();
        initEvent();
    }

    private void initView() {
        mDialog = new AlertDialog.Builder(this)
                .setMessage("是否准假")
                .setPositiveButton("准假", (dialog1, which) -> {
                    mRequest.setState(1);
                    mRequest.setDescription("准假");
                    doLeave();
                })
                .setNegativeButton("不准假", (dialog12, which) -> {
                    mRequest.setState(2);
                    mRequest.setDescription("不准假");
                    doLeave();
                }).create();

        Glide.with(this).load(mRecyclerItem.getStudentAvatar())
                .into(mBinding.teacherPassAvatar);
        mBinding.studentNameText.setText(mRecyclerItem.getStudent());
        mBinding.teacherStartNameText.setText(mRecyclerItem.getStartTime());
        mBinding.teacherEndTimeText.setText(mRecyclerItem.getEndTime());
        mBinding.teacherType1Text.setText(mRecyclerItem.getType());
        mBinding.teacherMyPhoneText.setText(mRecyclerItem.getStudentPhone());
        mBinding.teacherOtherPhoneText.setText(mRecyclerItem.getStudentPhone());
        mBinding.teacherReasonText.setText(mRecyclerItem.getReason());
        if (!mRecyclerItem.getPass().equals("待审核")) {
            mBinding.teacherXiaojia.setEnabled(false);
            mBinding.teacherXiaojia.setText(mRecyclerItem.getPass());
            mBinding.teacherXiaojia.setBackgroundResource(R.drawable.button_shape2);
        }
    }

    private void initEvent() {
        Button btnButton = mBinding.getRoot().findViewById(R.id.btn_return);
        Button tvButton = mBinding.getRoot().findViewById(R.id.tv_return);
        if (mRecyclerItem.getPass().equals("待审核")) {
            mBinding.teacherXiaojia.setOnClickListener(v -> {
                mDialog.show();
            });
        }
        tvButton.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        });

        btnButton.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        });
    }

    private void doLeave() {
        LeaveRepository.approveLeaveResponse(mRecyclerItem.getLeaveId(), mRequest).enqueue(
                new Callback<ApproveLeaveResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApproveLeaveResponse> call,
                                           @NonNull Response<ApproveLeaveResponse> response) {
                        mDialog.cancel();
                        ApproveLeaveResponse approveLeaveResponse = response.body();
                        Log.d(TAG, "onResponse: " + response.toString());
                        assert approveLeaveResponse != null;
                        if (approveLeaveResponse.getStatus() == 800) {
                            mBinding.teacherXiaojia
                                    .setText((mRequest.getState() == 1) ? "准假" : "不准假");
                            mBinding.teacherXiaojia.setEnabled(false);
                            mBinding.teacherXiaojia.setBackgroundResource(R.drawable.button_shape2);
                        } else {
                            Log.d(TAG, "onResponse: 请求错误" + approveLeaveResponse.getMsg());
                            Toast.makeText(PassLeaveActivity.this,
                                    "错误，" + approveLeaveResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApproveLeaveResponse> call, @NonNull
                            Throwable t) {
                        mDialog.cancel();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(PassLeaveActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 跳转到PassLeaveActivity
     *
     * @param context      上下文
     * @param recyclerItem 具体的请假列表数据
     */
    public static void startPassLeaveActivity(Context context, RecyclerItem recyclerItem) {
        Intent intent = new Intent(context, PassLeaveActivity.class);
        intent.putExtra("recyclerItem", recyclerItem);
        context.startActivity(intent);
    }
}