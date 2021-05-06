package com.littlecorgi.attendance;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.littlecorgi.attendance.logic.AttendanceRepository;
import com.littlecorgi.attendance.logic.model.AllAttendanceResponse;
import com.littlecorgi.attendance.logic.model.AttendanceBean;
import com.littlecorgi.attendance.tools.Time;
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
 * 教师端签到情况查询Activity
 */
public class TeacherAttendanceActivity extends AppCompatActivity {

    private static final String TAG = "TeacherAttendance";
    private final List<Time> mTimeLists = new ArrayList<>();
    private final List<AttendanceBean> mAttendances = new ArrayList<>();
    private TeacherTimeAdapter mAdapter;
    private long mTeacherId;
    private String mTeacherAvatar;
    private String mTeacherName;

    private ImageView mIvTeacherAvatar;
    private TextView mTvTeacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_attendance_activity);

        SharedPreferences sp = getSharedPreferences(UserSPConstant.FILE_NAME, MODE_PRIVATE);
        mTeacherId = sp.getLong(UserSPConstant.TEACHER_USER_ID, -1L);
        mTeacherAvatar = sp.getString(UserSPConstant.TEACHER_AVATAR, "");
        mTeacherName = sp.getString(UserSPConstant.TEACHER_NAME, "");

        mIvTeacherAvatar = findViewById(R.id.civ_user_icon);
        mTvTeacherName = findViewById(R.id.teacher_name);

        if (!mTeacherAvatar.isEmpty()) {
            Glide.with(this).load(mTeacherAvatar).into(mIvTeacherAvatar);
        }
        if (!mTeacherName.isEmpty()) {
            mTvTeacherName.setText(mTeacherName);
        }
        if (mTeacherId != -1) {
            initData();
            RefreshLayout refreshLayout = findViewById(R.id.srl_flush);
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setOnRefreshListener(v -> {
                initData();
                v.finishRefresh(true);
            });
        }

        RecyclerView recyclerView = findViewById(R.id.teacher_time_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new TeacherTimeAdapter(mTimeLists);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        Dialog loading = DialogUtil.writeLoadingDialog(this, false, "加载中");
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
                            Toast.makeText(TeacherAttendanceActivity.this,
                                    "错误，" + allAttendanceResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllAttendanceResponse> call,
                                          @NonNull Throwable t) {
                        loading.cancel();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(TeacherAttendanceActivity.this, "网络错误", Toast.LENGTH_SHORT)
                                .show();
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
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.teacher_attendance,
                        new TeacherTimeFragment(mTimeLists.get(position).getAttendanceId()));
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        @Override
        public int getItemCount() {
            return mTimeLists.size();
        }
    }
}
