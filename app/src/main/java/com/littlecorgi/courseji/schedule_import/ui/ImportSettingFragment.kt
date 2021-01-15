package com.littlecorgi.courseji.schedule_import.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.littlecorgi.commonlib.BaseDialogFragment
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.databinding.FragmentImportSettingBinding
import com.littlecorgi.courseji.schedule_import.vm.ImportViewModel

class ImportSettingFragment : BaseDialogFragment<FragmentImportSettingBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_import_setting

    private val viewModel by activityViewModels<ImportViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.tvCover.setOnClickListener {
            viewModel.importId = requireActivity().intent.extras!!.getInt("tableId", -1)
            viewModel.newFlag = false
            dismiss()
        }

        dataBinding.tvNew.setOnClickListener {
            launch {
                viewModel.importId = viewModel.getNewId()
                viewModel.newFlag = true
                dismiss()
            }
        }

        dataBinding.tvCancel.setOnClickListener {
            dismiss()
            requireActivity().finish()
        }
    }
}