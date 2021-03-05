package com.littlecorgi.leave.teacher;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.littlecorgi.leave.R;
import java.util.ArrayList;
import java.util.List;

public class TeacherLeaveActivity extends AppCompatActivity {

  public TabLayout tabLayout;
  List<Fragment> fragments = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_teacher_leave);
    ViewPager viewPager = findViewById(R.id.leave_viewpager);
    tabLayout = findViewById(R.id.tab_layout);
    String[] titles = new String[] {"批准请假", "请假历史"};

    fragments.add(new AskLeaveFragment());
    fragments.add(new HistoryFragment());

    PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);

    for (int i = 0; i < titles.length; i++) {
      tabLayout.getTabAt(i).setText(titles[i]);
    }
  }

  public class PagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
    }

    // 根据Item的位置返回对应位置的Fragment，绑定item和Fragment
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
    public int getItemPosition(Object object) {
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
      for (Fragment mFragment : fragments) {
        mFragment.onActivityResult(requestCode, resultCode, data);
      }
    }
  }
}
