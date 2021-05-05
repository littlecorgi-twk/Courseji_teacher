package com.littlecorgi.leave;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * 教师端请假页面
 */
public class TeacherLeaveActivity extends AppCompatActivity {

    public TabLayout mTabLayout;
    List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave);
        mTabLayout = findViewById(R.id.tab_layout);

        mFragments.add(new AskLeaveFragment());
        mFragments.add(new HistoryFragment());

        ViewPager viewPager = findViewById(R.id.leave_viewpager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);

        String[] titles = new String[]{"批准请假", "请假历史"};
        for (int i = 0; i < titles.length; i++) {
            Objects.requireNonNull(mTabLayout.getTabAt(i)).setText(titles[i]);
        }
    }

    /**
     * ViewPager的Adapter
     */
    public static class PagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        // 根据Item的位置返回对应位置的Fragment，绑定item和Fragment
        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        // 设置Item的数量
        @Override
        public int getCount() {
            return fragments.size();
        }

        // @Override
        // public int getItemPosition(Object object) {
        //     return POSITION_NONE;
        // }
        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(@NotNull Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getSupportFragmentManager().getFragments();
        if (getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment f : fragments) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
