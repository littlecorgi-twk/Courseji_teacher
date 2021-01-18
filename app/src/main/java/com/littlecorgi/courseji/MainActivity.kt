package com.littlecorgi.courseji

import android.animation.StateListAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.littlecorgi.commonlib.BaseActivity
import com.littlecorgi.commonlib.util.TimeUtil
import com.littlecorgi.commonlib.util.colorSL
import com.littlecorgi.commonlib.util.dip
import com.littlecorgi.courseji.databinding.ActivityMainBinding
import com.littlecorgi.courseji.schedule.ui.ScheduleViewPagerFragmentStateAdapter
import com.littlecorgi.courseji.schedule.vm.ScheduleViewModel
import com.littlecorgi.courseji.schedule_import.ui.ChooseImportFragment
import com.littlecorgi.courseji.utils.CourseUtils
import com.tencent.bugly.beta.Beta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.ParseException

@Route(path = "app/MainActivity")
class MainActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel by viewModels<ScheduleViewModel>()
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mWeekToggleGroup: MaterialButtonToggleGroup

    private val mScheduleViewPagerScrollerListener = object : ViewPager2.OnPageChangeCallback() {
        // 当课程表滑动时
        override fun onPageSelected(position: Int) {
            mViewModel.selectedWeek = position + 1
            try {
                if (mViewModel.currentWeek > 0) {
                    if (mViewModel.selectedWeek == mViewModel.currentWeek) {
                        val text = "第${mViewModel.selectedWeek}周"
                        mBinding.tvWeek.text = text
                        mBinding.tvWeekday.text = CourseUtils.getWeekday()
                    } else {
                        val text = "第${mViewModel.selectedWeek}周"
                        mBinding.tvWeek.text = text
                        mBinding.tvWeekday.text = "非本周"
                    }
                } else {
                    val text = "第${mViewModel.selectedWeek}周"
                    mBinding.tvWeek.text = text
                    mBinding.tvWeekday.text = "还没有开学哦"
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            // super.onPageSelected(position)
            // // 如果非本周，则更新Text
            // Log.d("ViewPager", "onPageSelected: $position ${mViewModel.currentWeek}")
            // val text = "第${position + 1}周"
            // mBinding.tvWeek.text = text
            // Log.d("MainActivity8888", "initScheduleViewPager: 第${position}周")
        }
    }

    private val mBottomSheetCallback = object :
        BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                mBinding.bottomSheetSvWeek.smoothScrollTo(
                    if (mViewModel.selectedWeek > 4) (mViewModel.selectedWeek - 4) * dip(56) else 0,
                    0
                )
                if (mWeekToggleGroup.checkedButtonId != mViewModel.selectedWeek) {
                    mWeekToggleGroup.check(mViewModel.selectedWeek)
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet)
        mWeekToggleGroup = mBinding.bottomSheetTgWeek
        // Bugly升级 检测是否有新版本
        Beta.checkUpgrade()
        // 初始化View
        initView()
        // 初始化数据
        initData()
    }

    private fun initView() {
        //todo
        // 此处需要先执行数据库操作，读取到table，之后才能走后面的初始化流程，
        // 但是数据库的读取属于耗时任务，此时会阻塞后面的View操作，所以会造成UI显示暂停1s左右
        lifecycleScope.launch {
            mViewModel.table = mViewModel.getDefaultTable()
            initWeekToggleGroup()
            initNav()
            initImport()
            initBottomSheet()
            initScheduleViewPager()
        }
        // 上面todo的解决方案，但是还未完成
        // 此方法中会进行读取数据库操作，执行耗时任务，所以单领出来，让他去读取数据库，并将读取出来的数据更新View显示，
        // 所以上面的方法中与mViewModel.table这个变量相关的，必须放到此方法中去执行，不然会显示"变量在初始化前被使用"
        // initViewAfterTableInitialized()
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
     * 初始化导入图标功能
     */
    private fun initImport() {
        // 点击导航按钮显示NavigationView
        mBinding.tvImport.setOnClickListener {
            ChooseImportFragment().show(supportFragmentManager, "importDialog")
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
        mBottomSheetBehavior.addBottomSheetCallback(mBottomSheetCallback)
    }

    /**
     * 初始化课程表ViewPager
     */
    private fun initScheduleViewPager() {
        val classList = MutableList(mViewModel.table.maxWeek) { i ->
            "Fragment$i"
        }
        mBinding.vpSchedule.apply {
            this.adapter = ScheduleViewPagerFragmentStateAdapter(this@MainActivity, classList)
        }
        mBinding.vpSchedule.registerOnPageChangeCallback(mScheduleViewPagerScrollerListener)
    }

    /**
     * 初始化周数选择组件
     */
    private fun initWeekToggleGroup() {
        // // 事先初始化好table
        // lifecycleScope.launch {
        //     mViewModel.table = mViewModel.getDefaultTable()
        // 获得当前周数
        mViewModel.currentWeek =
            CourseUtils.countWeek(mViewModel.table.startDate, mViewModel.table.sundayFirst)
        // 设置默认的选中周数
        mViewModel.selectedWeek = mViewModel.currentWeek
        if (mViewModel.currentWeek > 0) {
            if (mViewModel.currentWeek <= mViewModel.table.maxWeek) {
                val text = "第${mViewModel.currentWeek}周"
                // 其实此处和上面ViewPager的滚动监听的设置text重复了
                mBinding.tvWeek.text = text
                Log.d("MainActivity8888", "initWeekToggleGroup: 第${mViewModel.currentWeek}周")
            } else {
                mBinding.tvWeek.text = "当前周已超出设定范围"
                //TODO 完善周数管理逻辑，当超过时可以选择手动设置上学周期
            }
        } else {
            mBinding.tvWeek.text = "还没有开学哦"
        }
        // 添加Buton
        mWeekToggleGroup.removeAllViews()
        mWeekToggleGroup.clearChecked()
        for (i in 1..mViewModel.table.maxWeek) {
            mWeekToggleGroup.addView(
                MaterialButton(this@MainActivity).apply {
                    setTextColor(colorSL(R.color.mtrl_text_btn_text_color_selector))
                    val space = dip(8)
                    setPadding(space, 0, space, 0)
                    backgroundTintList = colorSL(R.color.mtrl_btn_text_btn_bg_color_selector)
                    rippleColor = colorSL(R.color.mtrl_btn_text_btn_ripple_color)
                    elevation = 0f
                    stateListAnimator = StateListAnimator()
                }.apply {
                    strokeColor = colorSL(R.color.mtrl_btn_stroke_color_selector)
                    strokeWidth = dip(1)
                }.apply {
                    id = i
                    text = i.toString()
                    textSize = 12f
                },
                dip(48), dip(48)
            )
        }
        lifecycleScope.launch {
            delay(1000)
            if (mWeekToggleGroup.checkedButtonId != mViewModel.selectedWeek) {
                mWeekToggleGroup.check(mViewModel.selectedWeek)
            }
            // 滑动动画
            mBinding.bottomSheetSvWeek.smoothScrollTo(
                if (mViewModel.selectedWeek > 4) (mViewModel.selectedWeek - 4) * dip(
                    56
                ) else 0, 0
            )
        }
        // 点击事件
        mWeekToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                mBinding.vpSchedule.currentItem = checkedId - 1
            }
        }
    }
// }

    private fun initViewAfterTableInitialized() {
        lifecycleScope.launch {
            mViewModel.table = mViewModel.getDefaultTable()
        }
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

    override fun onDestroy() {
        mBinding.vpSchedule.unregisterOnPageChangeCallback(mScheduleViewPagerScrollerListener)
        mWeekToggleGroup.clearOnButtonCheckedListeners()
        super.onDestroy()
    }
}