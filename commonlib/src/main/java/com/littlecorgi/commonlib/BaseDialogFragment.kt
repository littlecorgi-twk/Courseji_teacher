package com.littlecorgi.commonlib

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.google.android.material.card.MaterialCardView
import com.littlecorgi.commonlib.util.dip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * BaseDialogFragment
 * @author littlecorgi 2020/12/26
 */
abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {

    fun launch(block: suspend CoroutineScope.() -> Unit): Job = lifecycleScope.launch {
        lifecycle.whenStarted(block)
    }

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var dataBinding: T

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(requireContext().dip(280), ViewGroup.LayoutParams.WRAP_CONTENT)
        val root = inflater.inflate(R.layout.fragment_base_dialog, container, false)
        val cardView = root.findViewById<MaterialCardView>(R.id.base_card_view)
        LayoutInflater.from(context).inflate(layoutId, cardView, true)
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, cardView, true)
        return root
    }
}