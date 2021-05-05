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
import com.littlecorgi.leave.ui.adapter.AskLeaveAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 教师端请假页面
 */
public class AskLeaveFragment extends Fragment {

    private final List<RecyclerItem> mAskLeaveLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_ask_leave, container, false);
        initDates();
        RecyclerView recyclerView = view.findViewById(R.id.teacher_pass_leave_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        AskLeaveAdapter adapter = new AskLeaveAdapter(this, mAskLeaveLists);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initDates() {
        RecyclerItem askLeave = new RecyclerItem("病假", "未批准", "2020-12-23至2020-12-24", "发烧感冒",
                "李明");
        mAskLeaveLists.add(askLeave);
        RecyclerItem askLeave2 = new RecyclerItem("事假", "未批准", "2020-12-23至2020-12-24", "放假回家",
                "张三");
        mAskLeaveLists.add(askLeave2);
    }

}
