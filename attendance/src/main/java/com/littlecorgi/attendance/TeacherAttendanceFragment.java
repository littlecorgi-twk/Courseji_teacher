package com.littlecorgi.attendance;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.littlecorgi.attendance.logic.AttendanceRepository;
import com.littlecorgi.attendance.logic.model.AllAttendanceResponse;
import com.littlecorgi.attendance.logic.model.AttendanceBean;
import com.littlecorgi.attendance.tools.Time;
import com.littlecorgi.commonlib.AppViewModel;
import com.littlecorgi.commonlib.util.DialogUtil;
import com.littlecorgi.commonlib.util.TimeUtil;
import com.littlecorgi.commonlib.util.UserSPConstant;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 教师端签到统计页面
 *
 * @author littlecorgi 2021/05/07
 */
@Route(path = "/attendance/fragment_attendance")
public class TeacherAttendanceFragment extends Fragment {

    private static final String TAG = "TeacherAttendance";
    private final List<Time> mTimeLists = new ArrayList<>();
    private final List<AttendanceBean> mAttendances = new ArrayList<>();
    private TeacherTimeAdapter mAdapter;
    private long mTeacherId;
    private String mTeacherAvatar;
    private String mTeacherName;

    private ImageView mIvTeacherAvatar;
    private TextView mTvTeacherName;

    private AppViewModel mViewModel;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // 如果有数据，在此处取回
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_teacher_attendanceragment, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        mTeacherId = mViewModel.getTeacherId();
        // mTeacherId = 5;

        SharedPreferences sp =
                requireActivity().getSharedPreferences(UserSPConstant.FILE_NAME, MODE_PRIVATE);
        mTeacherAvatar = sp.getString(UserSPConstant.TEACHER_AVATAR, "");
        mTeacherName = sp.getString(UserSPConstant.TEACHER_NAME, "");

        initView();

        if (!mTeacherAvatar.isEmpty()) {
            Glide.with(this).load(mTeacherAvatar).into(mIvTeacherAvatar);
        }
        if (!mTeacherName.isEmpty()) {
            mTvTeacherName.setText(mTeacherName);
        }
        if (mTeacherId != -1) {
            initData();
            RefreshLayout refreshLayout = mView.findViewById(R.id.srl_flush);
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setOnRefreshListener(v -> {
                initData();
                v.finishRefresh(true);
            });
        }

        RecyclerView recyclerView = mView.findViewById(R.id.teacher_time_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        mAdapter = new TeacherTimeAdapter(mTimeLists);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        return mView;
    }

    private void initView() {
        mIvTeacherAvatar = mView.findViewById(R.id.civ_user_icon);
        mTvTeacherName = mView.findViewById(R.id.teacher_name);
    }

    private void initData() {
        Dialog loading = DialogUtil.writeLoadingDialog(requireContext(), false, "加载中");
        loading.show();
        loading.setCancelable(false);
        AttendanceRepository.getAllAttendanceFromTeacher(mTeacherId).enqueue(
                new Callback<AllAttendanceResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AllAttendanceResponse> call,
                                           @NonNull Response<AllAttendanceResponse> response) {
                        loading.cancel();
                        AllAttendanceResponse allAttendanceResponse = response.body();
                        Log.d(TAG, "onResponse: " + response.toString());
                        assert allAttendanceResponse != null;
                        if (allAttendanceResponse.getStatus() == 800) {
                            mAttendances.clear();
                            mAttendances.addAll(allAttendanceResponse.getData());
                            convertCheckOnToTime();
                        } else {
                            Log.d(TAG, "onResponse: 请求错误" + allAttendanceResponse.getMsg());
                            Toast.makeText(requireContext(), "错误，" + allAttendanceResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllAttendanceResponse> call,
                                          @NonNull Throwable t) {
                        loading.cancel();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void convertCheckOnToTime() {
        mTimeLists.clear();
        for (AttendanceBean attendance : mAttendances) {
            Time time = new Time();
            time.setTime(
                    TimeUtil.INSTANCE.getTimeFromTimestamp(attendance.getStartTime()));
            time.setClass1(attendance.getClassDetail().getName());
            time.setProportion(
                    attendance.getCheckInNum() + "/" + attendance.getClassDetail().getStudentNum());
            time.setAttendanceId(attendance.getId());
            mTimeLists.add(time);
        }
        mAdapter.notifyDataSetChanged();
    }

    class TeacherTimeAdapter extends RecyclerView.Adapter<TeacherTimeAdapter.ViewHolder> {

        private final List<Time> mTimeLists;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView time;
            TextView class1;
            TextView proportion;
            View timeView;

            public ViewHolder(View view) {
                super(view);
                timeView = view;
                this.time = view.findViewById(R.id.teacher_attendance_time);
                this.proportion = view.findViewById(R.id.teacher_attendance_proportion);
                this.class1 = view.findViewById(R.id.teacher_attendance_class);
            }
        }

        public TeacherTimeAdapter(List<Time> times) {
            mTimeLists = times;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.teacher_time_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TeacherTimeAdapter.ViewHolder holder, int position) {
            Time time = mTimeLists.get(position);
            holder.proportion.setText(time.getProportion());
            holder.time.setText(time.getTime());
            holder.class1.setText(time.getClass1());
            holder.timeView.setOnClickListener(v -> {
                TeacherTimeActivity.startTeacherTimeActivity(requireContext(),
                        mTimeLists.get(position).getAttendanceId());
            });
        }

        @Override
        public int getItemCount() {
            return mTimeLists.size();
        }
    }
}