package com.littlecorgi.commonlib

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Fragment的基类
 * @author littlecorgi 2021/4/9
 */
abstract class BaseFragment : Fragment() {

    protected abstract fun onActivityCreated()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                // 想做啥做点啥
                owner.lifecycle.removeObserver(this)
                onActivityCreated()
            }
        })
    }

    /**
     * fragment中的返回键
     * <p>
     * 默认返回false，交给Activity处理
     * 返回true：执行fragment中需要执行的逻辑
     * 返回false：执行activity中的 onBackPressed<p/>
     */
    public open fun onBackPressed() = false
}
