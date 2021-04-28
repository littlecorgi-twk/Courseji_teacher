package com.littlecorgi.leave.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史Fragment
 */
public class HistoryFragment extends Fragment {

    private final List<RecyclerItem> mHistoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_history_leave, container, false);
        initLeaveHistory();
        RecyclerView recyclerView = view.findViewById(R.id.teacher_history_leave_recycler);
        HistoryFragmentAdapter adapter = new HistoryFragmentAdapter(mHistoryList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initLeaveHistory() {
        RecyclerItem history = new RecyclerItem("病假", "已批准", "2020-12-23至2020-12-24", "发烧生病", "李明");
        mHistoryList.add(history);
        RecyclerItem history2 = new RecyclerItem("事假", "已批准", "2020-12-23至2020-12-24", "放假回家", "张三");
        mHistoryList.add(history2);
    }

    class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {

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

        public HistoryFragmentAdapter(List<RecyclerItem> recyclerItemList) {
            mHistoryList = recyclerItemList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.teacher_history_leave_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            holder.mHistoryView.setOnClickListener(v -> {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
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
}
