package com.example.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Tools.Absence;
import com.example.attendance.Tools.Late;
import com.example.attendance.Tools.LateFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class LateFragment extends Fragment {

    private List<Late> lateList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_late,container,false);
        initData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.late_recycler);
        LateFragmentAdapter adapter = new LateFragmentAdapter(lateList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initData(){
        Late late1 = new Late("英语","李明","12.21-12.22");
        lateList.add(late1);
        Late late2 = new Late("英语","李明","12.21-12.22");
        lateList.add(late2);
        Late late3 = new Late("英语","李明","12.21-12.22");
        lateList.add(late3);
    }


}
