package com.littlecorgi.attendance.Tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.attendance.R;
import java.util.List;

public class NormalFragmentAdapter extends RecyclerView.Adapter<NormalFragmentAdapter.ViewHolder> {
  private List<Normal> mNormalList;

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView lesson;
    TextView teacher;
    TextView time;

    public ViewHolder(View view) {
      super(view);
      lesson = view.findViewById(R.id.normal_lesson);
      teacher = view.findViewById(R.id.normal_teacher);
      time = view.findViewById(R.id.normal_time);
    }
  }

  public NormalFragmentAdapter(List<Normal> normals) {
    this.mNormalList = normals;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_normal_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Normal normal = mNormalList.get(position);
    holder.lesson.setText(normal.getLesson());
    holder.teacher.setText(normal.getTeacher());
    holder.time.setText(normal.getTime());
  }

  @Override
  public int getItemCount() {
    return mNormalList.size();
  }
}
