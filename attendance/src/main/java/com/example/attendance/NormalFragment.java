package com.example.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Tools.Absence;
import com.example.attendance.Tools.Normal;
import com.example.attendance.Tools.NormalFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class NormalFragment extends Fragment {
    private List<Normal> normalList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_normal,container,false);
        initData();
        RecyclerView recyclerView = view.findViewById(R.id.normal_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        NormalFragmentAdapter adapter = new NormalFragmentAdapter(normalList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void initData() {
        Normal normal1 = new Normal("英语", "李明", "2020-12-20-10:00");
        normalList.add(normal1);
        Normal normal2 = new Normal("数学", "张三", "2020-12-20-10:00");
        normalList.add(normal2);
    }
}
