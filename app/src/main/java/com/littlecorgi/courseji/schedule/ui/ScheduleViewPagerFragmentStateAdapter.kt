package com.littlecorgi.courseji.schedule.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 课程表ViewPager2的Adapter
 * @author littlecorgi 2020/10/20
 */
class ScheduleViewPagerFragmentStateAdapter(
    fa: FragmentActivity,
    private var maxWeek: Int
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = maxWeek

    override fun createFragment(position: Int): Fragment {
        return ScheduleFragment.newInstance(position + 1)
    }
}