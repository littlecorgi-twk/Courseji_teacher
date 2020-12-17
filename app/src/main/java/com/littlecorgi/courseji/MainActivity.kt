package com.littlecorgi.courseji

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.littlecorgi.commonlib.BaseActivity
import com.littlecorgi.commonlib.util.TimeUtil
import com.littlecorgi.courseji.databinding.ActivityMainBinding
import com.littlecorgi.courseji.schedule.ui.ScheduleViewPagerFragmentStateAdapter

class MainActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel by viewModels<MainViewModel>()
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet)
        // 初始化View
        initView()
        // 初始化数据
        initData()
    }

    private fun initView() {
        initNav()
        initBottomSheet()
        initScheduleViewPager()
    }

    /**
     * 初始化导航图标功能
     */
    private fun initNav() {
        // 点击导航按钮显示NavigationView
        mBinding.tvNav.setOnClickListener {
            mBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * 控制底部BottomSheet
     *  1. 设置more按钮的功能
     *  2. 设置当点击BottomSheet时再自动隐藏
     */
    private fun initBottomSheet() {
        // 默认先隐藏，不显示BottomSheet
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        // 点击more按钮显示BottomSheet
        mBinding.tvMore.setOnClickListener {
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        // 点击BottomSheet自动隐藏
        // 事先一定需要将behavior_hidden设置为true
        // 见 https://blog.csdn.net/wjr1949/article/details/105447735
        // 额外需要注意的是，我特地将BottomSheet的高度设置成了parent，这样给用户看起来点击的不是BottomSheet，但是实际上就是BottomSheet
        // 来达到点击外部自动隐藏的效果
        mBinding.bottomSheet.setOnClickListener {
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    /**
     * 初始化课程表ViewPager
     */
    private fun initScheduleViewPager() {
        mBinding.vpSchedule.apply {
            this.adapter = ScheduleViewPagerFragmentStateAdapter(
                this@MainActivity,
                listOf("FragmentA", "FragmentB", "FragmentC")
            )
        }
        mBinding.vpSchedule.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            // 当课程表滑动时
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 如果非本周，则更新Text
                Log.d("ViewPager", "onPageSelected: $position ${mViewModel.currentWeek}")
            }

        })
    }

    private fun initData() {
        initHeadImage()
        initBg()
        initWhatDay()
    }

    /**
     * 加载Nav header的图片
     */
    private fun initHeadImage() {
        Glide.with(this)
            .load(mViewModel.mHeaderImageUrl)
            .into(mBinding.nv.getHeaderView(0).findViewById(R.id.iv_header))
    }

    /**
     * 加载背景图片
     */
    private fun initBg() {
        Glide.with(this)
            .load(mViewModel.mBgImageUrl)
            .into(mBinding.ivBg)
    }

    private fun initWhatDay() {
        // 显示 n月n日
        mBinding.tvDate.text = TimeUtil.getTodayDate()
        // 显示 周n
        mBinding.tvWeekday.text = TimeUtil.getTodayWeekDay()
    }

    override fun onBackPressed() {
        when {
            // 当左侧侧滑栏展开时，按下返回建自动隐藏
            mBinding.drawerLayout.isDrawerOpen(GravityCompat.START) -> mBinding.drawerLayout.closeDrawer(
                GravityCompat.START
            )
            // 当BottomSheet展开时，按下返回建自动隐藏
            mBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED -> mBottomSheetBehavior.state =
                BottomSheetBehavior.STATE_HIDDEN
            // 除去上述结果，按默认返回逻辑返回
            else -> super.onBackPressed()
        }
    }
}