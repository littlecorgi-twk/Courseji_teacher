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
import com.example.attendance.Tools.AbsenceFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class AbsenceFragment extends Fragment {

    private List<Absence> absenceLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_absence, container, false);
        initData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.absence_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        AbsenceFragmentAdapter adapter = new AbsenceFragmentAdapter(absenceLists);
        recyclerView.setAdapter(adapter);
        return view;
    }



    public void initData() {
        Absence absence1 = new Absence("英语", "李明", "2020-12-20-10:00");
        absenceLists.add(absence1);
        Absence absence2 = new Absence("数学", "张三", "2020-12-20-10:00");
        absenceLists.add(absence2);
    }

}
