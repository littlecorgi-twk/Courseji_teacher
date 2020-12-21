package com.example.attendance.Tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;

import java.util.List;

public class AbsenceFragmentAdapter extends RecyclerView.Adapter<AbsenceFragmentAdapter.ViewHolder>{

    private List<Absence> mAbsenceList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView lessonText;
        TextView teacherText;
        TextView timeText;
        public ViewHolder(View view){
            super(view);
            lessonText = view.findViewById(R.id.absence_lesson);
            teacherText = view.findViewById(R.id.absence_teacherText);
            timeText = view.findViewById(R.id.absence_timeText);
        }
    }

    public AbsenceFragmentAdapter(List<Absence> absenceList){
        mAbsenceList = absenceList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_absence_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Absence absence = mAbsenceList.get(position);
        holder.lessonText.setText(absence.getLesson());
        holder.teacherText.setText(absence.getTeacher());
        holder.timeText.setText(absence.getTime());
    }

    @Override
    public int getItemCount() {
        return mAbsenceList.size();
    }

}

