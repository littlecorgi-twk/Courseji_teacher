package com.littlecorgi.leave.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.commonlib.AppViewModel;
import com.littlecorgi.commonlib.util.DialogUtil;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.logic.LeaveRepository;
import com.littlecorgi.leave.logic.model.AllLeaveResponse;
import com.littlecorgi.leave.ui.adapter.AskLeaveAdapter;
import com.littlecorgi.leave.ui.viewmodel.LeaveViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 教师端请假页面
 */
public class AskLeaveFragment extends Fragment {

    private static final String TAG = "AskLeaveFragment";
    private final List<RecyclerItem> mAskLeaveLists = new ArrayList<>();
    private long teacherId;
    private LeaveViewModel mLeaveViewModel;
    private AppViewModel mViewModel;
    private AskLeaveAdapter mAdapter;

    public AskLeaveFragment(long teacherId) {
        this.teacherId = teacherId;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.teacher_ask_leave, container, false);
        mLeaveViewModel = new ViewModelProvider(requireActivity()).get(LeaveViewModel.class);
        mViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        if (teacherId != -1) {
            initData();
        }

        SmartRefreshLayout refreshLayout = view.findViewById(R.id.ask_leave_refresh);
        refreshLayout.setPrimaryColorsId(R.color.blue_title, android.R.color.white);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(v -> {
            teacherId = mViewModel.getTeacherId();
            initData();
            v.finishRefresh(true);
        });

        RecyclerView recyclerView = view.findViewById(R.id.teacher_pass_leave_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mAdapter = new AskLeaveAdapter(this, mAskLeaveLists);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private void initData() {
        Dialog dialog = DialogUtil.writeLoadingDialog(requireContext(), false, "请求数据中");
        dialog.show();
        dialog.setCancelable(false);
        LeaveRepository.getLeaveFromTeacher(teacherId).enqueue(new Callback<AllLeaveResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllLeaveResponse> call,
                                   @NonNull Response<AllLeaveResponse> response) {
                dialog.cancel();
                AllLeaveResponse allLeaveResponse = response.body();
                Log.d(TAG, "onResponse: " + response.toString());
                assert allLeaveResponse != null;
                if (allLeaveResponse.getStatus() == 800) {
                    mLeaveViewModel.setLeaveBeans(allLeaveResponse.getData());
                    mAskLeaveLists.clear();
                    mAskLeaveLists.addAll(mLeaveViewModel.getAskLeaveRecyclerList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse: 请求错误" + allLeaveResponse.getMsg()
                            + allLeaveResponse.getErrorMsg());
                    Toast.makeText(requireContext(), "错误，" + allLeaveResponse.getMsg(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllLeaveResponse> call, @NonNull Throwable t) {
                dialog.cancel();
                Log.d(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(requireContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
