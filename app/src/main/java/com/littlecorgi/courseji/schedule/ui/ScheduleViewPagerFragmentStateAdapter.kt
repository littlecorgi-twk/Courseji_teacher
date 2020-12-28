package com.littlecorgi.courseji.schedule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 课程表ViewPager2的Adapter
 * @author littlecorgi 2020/10/20
 */
class ScheduleViewPagerFragmentStateAdapter(
    fa: FragmentActivity,
    private val classList: List<String>
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return classList.size
    }
    override fun createFragment(position: Int): Fragment {
        return ScheduleFragment().apply {
            arguments = Bundle().apply {
                putString("text", classList[position])
            }
        }
    }
}