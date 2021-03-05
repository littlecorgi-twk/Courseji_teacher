package com.littlecorgi.leave.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.R;
import java.util.ArrayList;
import java.util.List;

public class AskLeaveFragment extends Fragment {

  private List<RecyclerItem> askLeaveLists = new ArrayList<>();

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.teacher_ask_leave, container, false);
    initDates();
    RecyclerView recyclerView = view.findViewById(R.id.teacher_pass_leave_recycler);
    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    AskLeaveAdapter adapter = new AskLeaveAdapter(askLeaveLists);
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
    return view;
  }

  private void initDates() {
    RecyclerItem askLeave = new RecyclerItem("病假", "未批准", "2020-12-23至2020-12-24", "发烧感冒", "李明");
    askLeaveLists.add(askLeave);
    RecyclerItem askLeave2 = new RecyclerItem("事假", "未批准", "2020-12-23至2020-12-24", "放假回家", "张三");
    askLeaveLists.add(askLeave2);
  }

  class AskLeaveAdapter extends RecyclerView.Adapter<AskLeaveAdapter.ViewHolder> {
    private List<RecyclerItem> mAskLeaveList;

    class ViewHolder extends RecyclerView.ViewHolder {
      TextView typeText;
      TextView studentText;
      TextView timeText;
      TextView reasonText;
      TextView passText;
      View teacherAskLeave;

      public ViewHolder(View view) {
        super(view);
        teacherAskLeave = view;
        typeText = view.findViewById(R.id.teacher_leave_type);
        studentText = view.findViewById(R.id.teacher_leave_student);
        timeText = view.findViewById(R.id.teacher_leave_time);
        passText = view.findViewById(R.id.teacher_leave_pass);
        reasonText = view.findViewById(R.id.teacher_leave_reason);
      }
    }

    public AskLeaveAdapter(List<RecyclerItem> leaveList) {
      mAskLeaveList = leaveList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =
          LayoutInflater.from(parent.getContext())
              .inflate(R.layout.teacher_ask_leave_item, parent, false);
      final ViewHolder holder = new ViewHolder(view);
      holder.teacherAskLeave.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              PassLeaveFragment passLeaveFragment = new PassLeaveFragment();
              FragmentManager manager = getActivity().getSupportFragmentManager();
              FragmentTransaction transaction = manager.beginTransaction();
              transaction.replace(R.id.activity_teacher_leave, passLeaveFragment);
              transaction.addToBackStack(null);
              transaction.commit();
            }
          });
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      RecyclerItem askLeave = mAskLeaveList.get(position);
      holder.typeText.setText(askLeave.getType());
      holder.passText.setText(askLeave.getPass());
      holder.studentText.setText(askLeave.getStudent());
      holder.reasonText.setText(askLeave.getReason());
      holder.timeText.setText(askLeave.getTime());
    }

    @Override
    public int getItemCount() {
      return mAskLeaveList.size();
    }
  }
}
