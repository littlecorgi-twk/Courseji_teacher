package com.littlecorgi.leave.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.ui.adapter.HistoryFragmentAdapter;
import com.littlecorgi.leave.ui.viewmodel.LeaveViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史Fragment
 */
public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";
    private final List<RecyclerItem> mHistoryList = new ArrayList<>();
    private HistoryFragmentAdapter mAdapter;
    private LeaveViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(requireActivity()).get(LeaveViewModel.class);

        View view = inflater.inflate(R.layout.teacher_history_leave, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.teacher_history_leave_recycler);
        mAdapter = new HistoryFragmentAdapter(this, mHistoryList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: " + getClass().getSimpleName() + "进入屏幕");
        if (mViewModel != null) {
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initData() {
        mHistoryList.clear();
        mHistoryList.addAll(mViewModel.getHistoryRecyclerList());
        Log.d(TAG, "initData: " + mHistoryList.toString());
        mAdapter.notifyDataSetChanged();
    }
}
