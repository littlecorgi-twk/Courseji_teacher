package com.littlecorgi.leave.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.ui.HistoryFragment;
import com.littlecorgi.leave.ui.PassLeaveFragment;
import com.littlecorgi.leave.ui.RecyclerItem;
import java.util.List;

/**
 * @author littlecorgi 2021/5/5
 */
public class HistoryFragmentAdapter
        extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {

    private final HistoryFragment historyFragment;
    private final List<RecyclerItem> mHistoryList;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTypeText;
        TextView mStudentText;
        TextView mTimeText;
        TextView mReasonText;
        TextView mPassText;
        View mHistoryView;

        public ViewHolder(View view) {
            super(view);
            mHistoryView = view;
            mTypeText = view.findViewById(R.id.teacher_history_type);
            mStudentText = view.findViewById(R.id.teacher_history_student);
            mTimeText = view.findViewById(R.id.teacher_history_time);
            mPassText = view.findViewById(R.id.teacher_history_pass);
            mReasonText = view.findViewById(R.id.teacher_history_reason);
        }
    }

    public HistoryFragmentAdapter(HistoryFragment historyFragment,
                                  List<RecyclerItem> recyclerItemList) {
        this.historyFragment = historyFragment;
        mHistoryList = recyclerItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_history_leave_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.mHistoryView.setOnClickListener(v -> {
            FragmentManager manager = historyFragment.requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_teacher_leave, new PassLeaveFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerItem history = mHistoryList.get(position);
        holder.mTypeText.setText(history.getType());
        holder.mTimeText.setText(history.getTime());
        holder.mPassText.setText(history.getPass());
        holder.mReasonText.setText(history.getReason());
        holder.mStudentText.setText(history.getStudent());
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }
}
