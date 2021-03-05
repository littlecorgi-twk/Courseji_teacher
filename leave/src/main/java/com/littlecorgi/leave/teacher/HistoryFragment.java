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

public class HistoryFragment extends Fragment {

  private List<RecyclerItem> historyList = new ArrayList<>();

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.teacher_history_leave, container, false);
    initLeaveHistory();
    RecyclerView recyclerView = view.findViewById(R.id.teacher_history_leave_recycler);
    HistoryFragmentAdapter adapter = new HistoryFragmentAdapter(historyList);
    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
    return view;
  }

  private void initLeaveHistory() {
    RecyclerItem history = new RecyclerItem("病假", "已批准", "2020-12-23至2020-12-24", "发烧生病", "李明");
    historyList.add(history);
    RecyclerItem history2 = new RecyclerItem("事假", "已批准", "2020-12-23至2020-12-24", "放假回家", "张三");
    historyList.add(history2);
  }

  class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {
    private List<RecyclerItem> mHistoryList;

    class ViewHolder extends RecyclerView.ViewHolder {
      TextView typeText;
      TextView studentText;
      TextView timeText;
      TextView reasonText;
      TextView passText;
      View HistoryView;

      public ViewHolder(View view) {
        super(view);
        HistoryView = view;
        typeText = view.findViewById(R.id.teacher_history_type);
        studentText = view.findViewById(R.id.teacher_history_student);
        timeText = view.findViewById(R.id.teacher_history_time);
        passText = view.findViewById(R.id.teacher_history_pass);
        reasonText = view.findViewById(R.id.teacher_history_reason);
      }
    }

    public HistoryFragmentAdapter(List<RecyclerItem> recyclerItemList) {
      mHistoryList = recyclerItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =
          LayoutInflater.from(parent.getContext())
              .inflate(R.layout.teacher_history_leave_item, parent, false);
      ViewHolder holder = new ViewHolder(view);
      holder.HistoryView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              FragmentManager manager = getActivity().getSupportFragmentManager();
              FragmentTransaction transaction = manager.beginTransaction();
              transaction.replace(R.id.activity_teacher_leave, new PassLeaveFragment());
              transaction.addToBackStack(null);
              transaction.commit();
            }
          });
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      RecyclerItem history = mHistoryList.get(position);
      holder.typeText.setText(history.getType());
      holder.timeText.setText(history.getTime());
      holder.passText.setText(history.getPass());
      holder.reasonText.setText(history.getReason());
      holder.studentText.setText(history.getStudent());
    }

    @Override
    public int getItemCount() {
      return mHistoryList.size();
    }
  }
}
