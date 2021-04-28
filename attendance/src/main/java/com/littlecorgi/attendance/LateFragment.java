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
import com.littlecorgi.attendance.tools.Late;
import com.littlecorgi.attendance.tools.LateFragmentAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 迟到的Fragment
 */
public class LateFragment extends Fragment {

    private final List<Late> mLateList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_late, container, false);
        initData();
        RecyclerView recyclerView = view.findViewById(R.id.late_recycler);
        LateFragmentAdapter adapter = new LateFragmentAdapter(mLateList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        Button returnButton = view.findViewById(R.id.btn_return);
        returnButton.setOnClickListener(v -> {
            FragmentManager manager1 = requireActivity().getSupportFragmentManager();
            manager1.popBackStack();
        });
        return view;
    }

    private void initData() {
        Late late1 = new Late("英语", "李明", "12.21-12.22");
        mLateList.add(late1);
        Late late2 = new Late("英语", "李明", "12.21-12.22");
        mLateList.add(late2);
        Late late3 = new Late("英语", "李明", "12.21-12.22");
        mLateList.add(late3);
    }
}
