package com.littlecorgi.leave.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.databinding.TeacherPassLeaveBinding;
import com.littlecorgi.leave.logic.LeaveRepository;
import com.littlecorgi.leave.logic.model.ApproveLeaveRequest;
import com.littlecorgi.leave.logic.model.ApproveLeaveResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 通过请假
 */
public class PassLeaveFragment extends Fragment {

    private static final String TAG = "PassLeaveFragment";

    private final RecyclerItem mRecyclerItem;
    private TeacherPassLeaveBinding mBinding;
    private final ApproveLeaveRequest mRequest;
    private AlertDialog mDialog;

    public PassLeaveFragment(RecyclerItem recyclerItem) {
        this.mRecyclerItem = recyclerItem;
        this.mRequest = new ApproveLeaveRequest();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.teacher_pass_leave, container, false);

        initView();
        initEvent();
        return mBinding.getRoot();
    }

    private void initView() {
        mDialog = new AlertDialog.Builder(requireContext())
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

        Glide.with(requireContext()).load(mRecyclerItem.getStudentAvatar())
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
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            manager.popBackStack();
        });

        btnButton.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
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
                            Toast.makeText(requireContext(), "错误，" + approveLeaveResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApproveLeaveResponse> call, @NonNull
                            Throwable t) {
                        mDialog.cancel();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
