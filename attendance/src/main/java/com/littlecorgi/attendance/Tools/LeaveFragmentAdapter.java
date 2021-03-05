package com.littlecorgi.attendance.Tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.attendance.R;
import java.util.List;

public class LeaveFragmentAdapter extends RecyclerView.Adapter<LeaveFragmentAdapter.ViewHolder> {
  private List<Leave> mLeaveList;

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView lesson;
    TextView teacher;
    TextView type;
    TextView time;
    TextView reason;

    public ViewHolder(View view) {
      super(view);
      lesson = view.findViewById(R.id.leave_lesson);
      teacher = view.findViewById(R.id.leave_teacher);
      type = view.findViewById(R.id.leave_type);
      time = view.findViewById(R.id.leave_time);
      reason = view.findViewById(R.id.leave_reason);
    }
  }

  public LeaveFragmentAdapter(List<Leave> leaveList) {
    this.mLeaveList = leaveList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_leave_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Leave leave = mLeaveList.get(position);
    holder.lesson.setText(leave.getLesson());
    holder.teacher.setText(leave.getTeacher());
    holder.reason.setText(leave.getReason());
    holder.type.setText(leave.getType());
  }

  @Override
  public int getItemCount() {
    return mLeaveList.size();
  }
}
