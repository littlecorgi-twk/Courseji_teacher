package com.example.leave.student;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.leave.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistroyFragment extends Fragment {

    private boolean isGetData = false;
    private List<History> historyList = new ArrayList<>();
    LeaveHistoryAdapter adapter1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_history_leave,container,false);
        File f = new File("/data/data/com.example.leave/shared_prefs/data.xml");
        if (f.exists()){
            initHistories();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_leave);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter1 = new LeaveHistoryAdapter(historyList);
            recyclerView.setAdapter(adapter1);
        }
        return view;
    }
    private void initHistories(){
        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String name = pref.getString("name","");
        String type1 = pref.getString("type1","");
        String startTime = pref.getString("startTime","");
        String endTime = pref.getString("endTime","");
        String leaveSituation = pref.getString("leaveSituation","");
        History history = new History(type1,name,startTime+"至"+endTime,leaveSituation,"未销假");
        historyList.add(history);
    }


    class LeaveHistoryAdapter extends RecyclerView.Adapter<LeaveHistoryAdapter.ViewHolder> {
        private List<History> mHistoryList;
         class ViewHolder extends RecyclerView.ViewHolder{
            TextView leaveTypeText;
            TextView leavePeopleText;
            TextView leaveTimeText;
            TextView leaveReasonText;
            TextView leaveBackText;
            View historyView;


            public ViewHolder(View view){
                super(view);
                historyView = view;
                leaveTypeText = (TextView) view.findViewById(R.id.leaveTypeText);
                leavePeopleText = (TextView) view.findViewById(R.id.leavePeopleText);
                leaveTimeText = (TextView) view.findViewById(R.id.leaveTimeText);
                leaveReasonText = (TextView) view.findViewById(R.id.leaveReasonText);
                leaveBackText = (TextView) view.findViewById(R.id.leaveBackText);
            }
        }

        public LeaveHistoryAdapter(List<History> historyList){
            mHistoryList = historyList;
        }


        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_leave_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);
            holder.historyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PeopleHistoryFragment peopleHistoryFragment = new PeopleHistoryFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.student_leave,peopleHistoryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull LeaveHistoryAdapter.ViewHolder holder, int position) {
            History history = mHistoryList.get(position);
            holder.leaveTypeText.setText(history.getLeaveTypeText());
            holder.leavePeopleText.setText(history.getLeavePeopleText());
            holder.leaveTimeText.setText(history.getLeaveTimeText());
            holder.leaveReasonText.setText(history.getLeaveReasonText());
            holder.leaveBackText.setText(history.getLeaveBackText());
        }

        @Override
        public int getItemCount() {
            return mHistoryList.size();
        }
    }



}
