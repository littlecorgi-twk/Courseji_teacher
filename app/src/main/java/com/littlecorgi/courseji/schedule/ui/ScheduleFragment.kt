package com.littlecorgi.courseji.schedule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.littlecorgi.commonlib.lifecycle.MyFragmentLifecycleCallbacks
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.databinding.FragmentScheduleBinding

/**
 * 课程表Fragment
 * @author littlecorgi 2020/10/20
 */
class ScheduleFragment : Fragment() {

    private lateinit var mBinding: FragmentScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        mBinding.tvText.text = arguments?.getString("text") ?: "null"
        // 监听Fragment生命周期变化
        parentFragmentManager.registerFragmentLifecycleCallbacks(
            MyFragmentLifecycleCallbacks(
                ScheduleFragment::class.java.simpleName + arguments?.getString(
                    "text"
                )
            ),
            false
        )
        return mBinding.root
    }


}