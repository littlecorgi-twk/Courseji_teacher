package com.littlecorgi.leave.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.ui.adapter.HistoryFragmentAdapter;
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
        RecyclerView recyclerView = view.findViewById(R.id.teacher_history_leave_recycler);
        HistoryFragmentAdapter adapter = new HistoryFragmentAdapter(this, mHistoryList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
