package com.littlecorgi.leave.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.littlecorgi.commonlib.AppViewModel;
import com.littlecorgi.leave.R;
import com.littlecorgi.leave.ui.adapter.PagerAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 教师端请假页面
 *
 * @author littlecorgi 2021/05/07
 */
public class TeacherLeaveFragment extends Fragment {

    public TabLayout mTabLayout;
    List<Fragment> mFragments = new ArrayList<>();
    private View mView;
    private AppViewModel mViewModel;

    public TeacherLeaveFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // 在此添加数据
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_teacher_leave, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        initView();
        return mView;
    }

    private void initView() {
        mTabLayout = mView.findViewById(R.id.tab_layout);

        mFragments.add(new AskLeaveFragment(mViewModel.getTeacherId()));
        // mFragments.add(new AskLeaveFragment(5));
        mFragments.add(new HistoryFragment());

        ViewPager viewPager = mView.findViewById(R.id.leave_viewpager);
        PagerAdapter adapter = new PagerAdapter(getParentFragmentManager(), mFragments);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);

        String[] titles = new String[] {"批准请假", "请假历史"};
        for (int i = 0; i < titles.length; i++) {
            Objects.requireNonNull(mTabLayout.getTabAt(i)).setText(titles[i]);
        }
    }
}