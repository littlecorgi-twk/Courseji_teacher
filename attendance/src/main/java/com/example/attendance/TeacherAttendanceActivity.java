package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendance.Tools.Time;

import java.util.ArrayList;
import java.util.List;

public class TeacherAttendanceActivity extends AppCompatActivity {

    private List<Time> timeLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_attendance_activity);
        initData();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.teacher_time_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        TeacherTimeAdapter adapter = new TeacherTimeAdapter(timeLists);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        TextView textView = findViewById(R.id.teacher_name);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.teacher_attendance,new TeacherTimeFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void initData(){
        Time time1 = new Time("12.24  ","计科1907","50/60");
        timeLists.add(time1);
        Time time2 = new Time("12.20  ","计科1906","60/60");
        timeLists.add(time2);
        Time time3 = new Time("12.16  ","计科1907","59/60");
        timeLists.add(time3);
        Time time4 = new Time("12.24  ","计科1907","50/60");
        timeLists.add(time1);
        Time time5 = new Time("12.20  ","计科1906","60/60");
        timeLists.add(time2);
        Time time6 = new Time("12.16  ","计科1907","59/60");

    }

    class TeacherTimeAdapter extends RecyclerView.Adapter<TeacherTimeAdapter.ViewHolder> {
        private List<Time> mTimeLists;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView time;
            TextView class1;
            TextView proportion;
            View timeView;
            public ViewHolder(View view){
                super(view);
                timeView = view;
                this.time = view.findViewById(R.id.teacher_attendance_time);
                this.proportion = view.findViewById(R.id.teacher_attendance_proportion);
                this.class1 = view.findViewById(R.id.teacher_attendance_class);
            }
        }

        public TeacherTimeAdapter(List<Time> times){
            mTimeLists = times;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_time_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);
            holder.timeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.teacher_attendance,new TeacherTimeFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull TeacherTimeAdapter.ViewHolder holder, int position) {
            Time time = mTimeLists.get(position);
            holder.proportion.setText(time.getProportion());
            holder.time.setText(time.getTime());
            holder.class1.setText(time.getClass1());
        }

        @Override
        public int getItemCount() {
            return mTimeLists.size();
        }


    }

}