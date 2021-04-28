package com.littlecorgi.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.attendance.tools.Normal;
import com.littlecorgi.attendance.tools.NormalFragmentAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 普通的Fragment
 */
public class NormalFragment extends Fragment {

    private final List<Normal> mNormalList = new ArrayList<>();
    private Button mReturnButton;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_normal, container, false);
        initData();
        RecyclerView recyclerView = view.findViewById(R.id.normal_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        NormalFragmentAdapter adapter = new NormalFragmentAdapter(mNormalList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        mReturnButton = view.findViewById(R.id.btn_return);
        mReturnButton.setOnClickListener(v -> {
            FragmentManager manager1 = requireActivity().getSupportFragmentManager();
            manager1.popBackStack();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReturnButton = requireActivity().findViewById(R.id.btn_return);
        mReturnButton.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            manager.popBackStack();
        });
    }

    private void initData() {
        Normal normal1 = new Normal("英语", "李明", "2020-12-20-10:00");
        mNormalList.add(normal1);
        Normal normal2 = new Normal("数学", "张三", "2020-12-20-10:00");
        mNormalList.add(normal2);
    }
}
