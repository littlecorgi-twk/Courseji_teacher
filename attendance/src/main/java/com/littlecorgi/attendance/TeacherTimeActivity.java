package com.littlecorgi.attendance;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.widget.Toolbar;
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
 * 具体的签到详情页
 *
 * @author littlecorgi 2021/05/07
 */
public class TeacherTimeActivity extends AppCompatActivity {

    private static final String TAG = "TeacherTimeActivity";
    private final List<StudentBean> mNotSignList = new ArrayList<>();
    private final List<StudentBean> mSignList = new ArrayList<>();
    private final List<CheckOnBean> mCheckOnList = new ArrayList<>();
    private long mAttendanceId;
    private TeacherTimeFragmentAdapter mAdapterSign;
    private TeacherTimeFragmentAdapter mAdapterNotSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_time);

        if (getIntent() != null) {
            mAttendanceId = getIntent().getLongExtra("attendanceId", -1L);
        }

        Toolbar toolbar = findViewById(R.id.teacher_class_toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        });

        final LinearLayoutManager managerNotSign = new LinearLayoutManager(this);
        RecyclerView recyclerViewNotSign = findViewById(R.id.student_not_sign_in_recycler);
        mAdapterNotSign = new TeacherTimeFragmentAdapter(mNotSignList);
        recyclerViewNotSign.setLayoutManager(managerNotSign);
        recyclerViewNotSign.setAdapter(mAdapterNotSign);

        final LinearLayoutManager managerSign = new LinearLayoutManager(this);
        RecyclerView recyclerViewSign = findViewById(R.id.student_sign_in_recycler);
        mAdapterSign = new TeacherTimeFragmentAdapter(mSignList);
        recyclerViewSign.setLayoutManager(managerSign);
        recyclerViewSign.setAdapter(mAdapterSign);

        initData();
    }

    private void initData() {
        Dialog loading = DialogUtil.writeLoadingDialog(this, false, "加载中");
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
                            Toast.makeText(TeacherTimeActivity.this,
                                    "错误，" + allCheckOnResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllCheckOnResponse> call,
                                          @NonNull Throwable t) {
                        loading.cancel();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(TeacherTimeActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 跳转到此activity
     *
     * @param context      上下文
     * @param attendanceId 考勤id
     */
    public static void startTeacherTimeActivity(Context context, long attendanceId) {
        Intent intent = new Intent(context, TeacherTimeActivity.class);
        intent.putExtra("attendanceId", attendanceId);
        context.startActivity(intent);
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