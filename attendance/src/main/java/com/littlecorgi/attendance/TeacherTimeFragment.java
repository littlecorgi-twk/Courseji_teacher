package com.littlecorgi.attendance;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.littlecorgi.attendance.logic.CheckOnRepository;
import com.littlecorgi.attendance.logic.model.AllCheckOnResponse;
import com.littlecorgi.attendance.logic.model.CheckOnBean;
import com.littlecorgi.attendance.logic.model.StudentBean;
import com.littlecorgi.commonlib.util.DialogUtil;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 教师端时间Fragment
 */
public class TeacherTimeFragment extends Fragment {

    private static final String TAG = "TeacherTimeFragment";
    private final List<StudentBean> mNotSignList = new ArrayList<>();
    private final List<StudentBean> mSignList = new ArrayList<>();
    private final List<CheckOnBean> mCheckOnList = new ArrayList<>();
    private final long mAttendanceId;
    private TeacherTimeFragmentAdapter mAdapterSign;
    private TeacherTimeFragmentAdapter mAdapterNotSign;

    public TeacherTimeFragment(long attendanceId) {
        this.mAttendanceId = attendanceId;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_class_students, container, false);
        Button returnButton = view.findViewById(R.id.btn_return);
        returnButton.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            manager.popBackStack();
        });

        final LinearLayoutManager managerNotSign = new LinearLayoutManager(getActivity());

        RecyclerView recyclerViewNotSign = view.findViewById(R.id.student_not_sign_in_recycler);
        mAdapterNotSign = new TeacherTimeFragmentAdapter(mNotSignList);
        recyclerViewNotSign.setLayoutManager(managerNotSign);
        recyclerViewNotSign.setAdapter(mAdapterNotSign);

        final LinearLayoutManager managerSign = new LinearLayoutManager(getActivity());
        RecyclerView recyclerViewSign = view.findViewById(R.id.student_sign_in_recycler);
        mAdapterSign = new TeacherTimeFragmentAdapter(mSignList);
        recyclerViewSign.setLayoutManager(managerSign);
        recyclerViewSign.setAdapter(mAdapterSign);

        initData();

        return view;
    }

    private void initData() {
        Dialog loading = DialogUtil.writeLoadingDialog(requireContext(), false, "加载中");
        loading.show();
        loading.setCancelable(false);
        CheckOnRepository.getCheckOnFromAttendance(mAttendanceId).enqueue(
                new Callback<AllCheckOnResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AllCheckOnResponse> call,
                                           @NonNull Response<AllCheckOnResponse> response) {
                        loading.cancel();
                        AllCheckOnResponse allCheckOnResponse = response.body();
                        Log.d(TAG, "onResponse: " + response.toString());
                        assert allCheckOnResponse != null;
                        if (allCheckOnResponse.getStatus() == 800) {
                            mCheckOnList.clear();
                            mCheckOnList.addAll(allCheckOnResponse.getData());
                            mSignList.clear();
                            mNotSignList.clear();
                            for (CheckOnBean check : mCheckOnList) {
                                if (check.getCheckOnStates() == 1) {
                                    // 已签到
                                    mSignList.add(check.getStudent());
                                } else {
                                    mNotSignList.add(check.getStudent());
                                }
                            }
                            mAdapterNotSign.notifyDataSetChanged();
                            mAdapterSign.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "onResponse: 请求错误" + allCheckOnResponse.getMsg());
                            Toast.makeText(requireContext(), "错误，" + allCheckOnResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllCheckOnResponse> call,
                                          @NonNull Throwable t) {
                        loading.cancel();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    static class TeacherTimeFragmentAdapter
            extends RecyclerView.Adapter<TeacherTimeFragmentAdapter.ViewHolder> {

        private final List<StudentBean> mStudentList;

        static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView studentIcon;
            TextView studentName;

            public ViewHolder(View view) {
                super(view);
                studentIcon = view.findViewById(R.id.student_icon);
                studentName = view.findViewById(R.id.student_name);
            }
        }

        public TeacherTimeFragmentAdapter(List<StudentBean> students) {
            mStudentList = students;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.teacher_class_student_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            StudentBean student = mStudentList.get(position);
            holder.studentName.setText(student.getName());
            Glide.with(holder.studentIcon).load(student.getAvatar()).into(holder.studentIcon);
        }

        @Override
        public int getItemCount() {
            return mStudentList.size();
        }
    }
}
