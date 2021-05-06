package com.littlecorgi.leave.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.littlecorgi.commonlib.util.TimeUtil;
import com.littlecorgi.leave.logic.model.LeaveBean;
import com.littlecorgi.leave.ui.RecyclerItem;
import java.util.ArrayList;
import java.util.List;

/**
 * @author littlecorgi 2021/5/6
 */
public class LeaveViewModel extends ViewModel {

    private static final String TAG = "LeaveViewModel";

    private final List<LeaveBean> mLeaveBeans = new ArrayList<>();
    private final List<LeaveBean> mAskLeaveList = new ArrayList<>(); // 待审批的请假
    private final List<LeaveBean> mHistoryList = new ArrayList<>(); // 已审批的请假
    private final List<RecyclerItem> mHistoryRecyclerList = new ArrayList<>();
    private final List<RecyclerItem> mAskLeaveRecyclerList = new ArrayList<>();


    public List<LeaveBean> getLeaveBeans() {
        return mLeaveBeans;
    }

    /**
     *
     */
    public void setLeaveBeans(List<LeaveBean> leaveBeans) {
        mLeaveBeans.clear();
        mLeaveBeans.addAll(leaveBeans);
        mAskLeaveList.clear();
        mHistoryList.clear();
        for (LeaveBean leaveBean : mLeaveBeans) {
            if (leaveBean.getStates() == 0) {
                mAskLeaveList.add(leaveBean);
            } else {
                mHistoryList.add(leaveBean);
            }
        }
        mAskLeaveRecyclerList.clear();
        mAskLeaveRecyclerList.addAll(convertLeaveResponseToRecyclerItem(mAskLeaveList));

        mHistoryRecyclerList.clear();
        mHistoryRecyclerList.addAll(convertLeaveResponseToRecyclerItem(mHistoryList));
    }

    public List<RecyclerItem> getHistoryRecyclerList() {
        return mHistoryRecyclerList;
    }

    public List<RecyclerItem> getAskLeaveRecyclerList() {
        return mAskLeaveRecyclerList;
    }

    private List<RecyclerItem> convertLeaveResponseToRecyclerItem(List<LeaveBean> leaveBeans) {
        ArrayList<RecyclerItem> recyclerItems = new ArrayList<>();
        for (LeaveBean leaveBean : leaveBeans) {
            RecyclerItem item = new RecyclerItem();
            item.setType(leaveBean.getTitle());
            String passStr = "";
            if (leaveBean.getStates() == 0) {
                passStr = "待审核";
            } else if (leaveBean.getStates() == 1) {
                passStr = "准假";
            } else {
                passStr = "不准假";
            }
            item.setPass(passStr);
            item.setStudent(leaveBean.getStudent().getName());
            item.setTime(TimeUtil.INSTANCE.getTimeFromTimestamp(leaveBean.getStartTime()));
            item.setReason(leaveBean.getDescription());
            recyclerItems.add(item);
        }
        Log.d(TAG, "convertLeaveResponseToRecyclerItem: " + leaveBeans.size()
                + recyclerItems.size());
        Log.d(TAG, "convertLeaveResponseToRecyclerItem: " + recyclerItems.toString());
        return recyclerItems;
    }
}
