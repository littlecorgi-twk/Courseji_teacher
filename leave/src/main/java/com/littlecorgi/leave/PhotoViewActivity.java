package com.littlecorgi.leave;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import com.littlecorgi.leave.databinding.PhotoViewBinding;
import com.littlecorgi.leave.student.MyImageAdapter;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity {
  ViewPager viewpager;
  TextView mTvImageCount;

  // 点击的下标
  private int currentPosition;
  private List<String> urlLists;

  // DataBinding
  private PhotoViewBinding mBinding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.photo_view);
    viewpager = mBinding.viewPager;
    mTvImageCount = mBinding.mTvImageCount;
    ButterKnife.bind(this);
    initData();
  }

  private void initData() {
    if (null == urlLists) {
      urlLists = new ArrayList<>();
    }
    // 获得点击的位置
    currentPosition = getIntent().getIntExtra("position", 0);
    // 图片集合
    urlLists = getIntent().getStringArrayListExtra("list");
    MyImageAdapter adapter = new MyImageAdapter(this, urlLists);
    viewpager.setAdapter(adapter);
    viewpager.setCurrentItem(currentPosition);
    mTvImageCount.setText(currentPosition + 1 + "/" + urlLists.size());
    viewpager.addOnPageChangeListener(
        new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(
              int position, float positionOffset, int positionOffsetPixels) {}

          @Override
          public void onPageSelected(int position) {
            mTvImageCount.setText(position + 1 + "/" + urlLists.size());
          }

          @Override
          public void onPageScrollStateChanged(int state) {}
        });
  }
}
