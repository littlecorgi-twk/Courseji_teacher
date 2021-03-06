package com.littlecorgi.leave.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.littlecorgi.leave.R;

public class PassLeaveFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_pass_leave, container, false);
        Button button = (Button) view.findViewById(R.id.teacher_xiaojia);
        Button tvButton = (Button) view.findViewById(R.id.tv_return);
        Button btnButton = (Button) view.findViewById(R.id.btn_return);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setText("已批准");
                        button.setBackgroundResource(R.drawable.button_shape2);
                    }
                });
        tvButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.popBackStack();
                    }
                });

        btnButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.popBackStack();
                    }
                });
        return view;
    }
}
