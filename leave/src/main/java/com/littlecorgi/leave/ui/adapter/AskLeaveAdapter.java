package com.littlecorgi.leave.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.PassLeaveActivity;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.ui.AskLeaveFragment;
import com.littlecorgi.leave.ui.RecyclerItem;
import java.util.List;

/**
 * @author littlecorgi 2021/5/5
 */
public class AskLeaveAdapter extends RecyclerView.Adapter<AskLeaveAdapter.ViewHolder> {

    private final AskLeaveFragment askLeaveFragment;
    private final List<RecyclerItem> mAskLeaveList;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTypeText;
        TextView mStudentText;
        TextView mTimeText;
        TextView mReasonText;
        TextView mPassText;
        View mTeacherAskLeave;

        public ViewHolder(View view) {
            super(view);
            mTeacherAskLeave = view;
            mTypeText = view.findViewById(R.id.teacher_leave_type);
            mStudentText = view.findViewById(R.id.teacher_leave_student);
            mTimeText = view.findViewById(R.id.teacher_leave_time);
            mPassText = view.findViewById(R.id.teacher_leave_pass);
            mReasonText = view.findViewById(R.id.teacher_leave_reason);
        }
    }

    public AskLeaveAdapter(AskLeaveFragment askLeaveFragment, List<RecyclerItem> leaveList) {
        this.askLeaveFragment = askLeaveFragment;
        mAskLeaveList = leaveList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.teacher_ask_leave_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerItem askLeave = mAskLeaveList.get(position);
        holder.mTypeText.setText(askLeave.getType());
        holder.mPassText.setText(askLeave.getPass());
        holder.mStudentText.setText(askLeave.getStudent());
        holder.mReasonText.setText(askLeave.getReason());
        holder.mTimeText.setText(askLeave.getTime());
        holder.mTeacherAskLeave.setOnClickListener(v -> {
            PassLeaveActivity.startPassLeaveActivity(
                    askLeaveFragment.requireContext(),
                    mAskLeaveList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mAskLeaveList.size();
    }
}
