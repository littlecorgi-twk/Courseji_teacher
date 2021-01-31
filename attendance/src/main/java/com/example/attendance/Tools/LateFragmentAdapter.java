package com.example.attendance.Tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;


import java.util.List;

public class LateFragmentAdapter extends RecyclerView.Adapter<LateFragmentAdapter.ViewHolder> {
    private List<Late> mLateList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView lessonText;
        TextView teacherText;
        TextView timeText;

        public ViewHolder(View view){
            super(view);
            lessonText = view.findViewById(R.id.late_lesson);
            teacherText = view.findViewById(R.id.late_teacher);
            timeText = view.findViewById(R.id.late_time);
        }
    }

    public LateFragmentAdapter(List<Late> lateList){
        this.mLateList = lateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_late_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Late late = mLateList.get(position);
        holder.timeText.setText(late.getTime());
        holder.teacherText.setText(late.getTeacher());
        holder.lessonText.setText(late.getLesson());
    }

    @Override
    public int getItemCount() {
        return mLateList.size();
    }

}
