package com.littlecorgi.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.attendance.tools.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * 教师端时间Fragment
 */
public class TeacherTimeFragment extends Fragment {

    private final List<Student> mNotSignLists = new ArrayList<>();
    private final List<Student> mSignLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_class_students, container, false);
        Button returnButton = view.findViewById(R.id.btn_return);
        returnButton.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            manager.popBackStack();
        });

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        // initDataNotSign();
        // recyclerViewNotSign = view.findViewById(R.id.student_not_sign_in_recycler);
        // adapterNotSign = new TeacherTimeFragmentAdapter(notSignLists);
        // recyclerViewNotSign.setLayoutManager(manager);
        // recyclerViewNotSign.setAdapter(adapterNotSign);

        initDataSign();
        RecyclerView recyclerViewSign = view.findViewById(R.id.student_sign_in_recycler);
        TeacherTimeFragmentAdapter adapterSign = new TeacherTimeFragmentAdapter(mSignLists);
        recyclerViewSign.setLayoutManager(manager);
        recyclerViewSign.setAdapter(adapterSign);

        return view;
    }

    private void initDataNotSign() {
        Student student1 = new Student("张三", R.drawable.student);
        mNotSignLists.add(student1);
    }

    private void initDataSign() {
        Student student2 = new Student("王五", R.drawable.student);
        mSignLists.add(student2);
        Student student3 = new Student("李四", R.drawable.student);
        mSignLists.add(student3);
        Student student4 = new Student("张三", R.drawable.student);
        mSignLists.add(student4);
        Student student5 = new Student("李明", R.drawable.student);
        mSignLists.add(student5);
    }

    static class TeacherTimeFragmentAdapter
            extends RecyclerView.Adapter<TeacherTimeFragmentAdapter.ViewHolder> {

        private final List<Student> mStudentList;

        static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView studentIcon;
            TextView studentName;

            public ViewHolder(View view) {
                super(view);
                studentIcon = view.findViewById(R.id.student_icon);
                studentName = view.findViewById(R.id.student_name);
            }
        }

        public TeacherTimeFragmentAdapter(List<Student> students) {
            mStudentList = students;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.teacher_class_student_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Student student = mStudentList.get(position);
            holder.studentName.setText(student.getName());
            holder.studentIcon.setImageResource(student.getImageId());
        }

        @Override
        public int getItemCount() {
            return mStudentList.size();
        }
    }
}
