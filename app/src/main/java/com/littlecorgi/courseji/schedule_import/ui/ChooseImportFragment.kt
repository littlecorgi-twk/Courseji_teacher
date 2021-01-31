package com.littlecorgi.courseji.schedule_import.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.littlecorgi.commonlib.BaseDialogFragment
import com.littlecorgi.courseji.AppDatabase
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.databinding.FragmentChooseImportBinding
import com.littlecorgi.courseji.schedule.vm.ScheduleViewModel
import com.littlecorgi.courseji.schedule_import.Common
import com.littlecorgi.courseji.schedule_import.LoginWebActivity
import com.littlecorgi.courseji.utils.Const
import com.littlecorgi.courseji.utils.Utils

/**
 * 选择导入课程表Fragment
 * @author littlecorgi 2020/12/26
 */
class ChooseImportFragment : BaseDialogFragment<FragmentChooseImportBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_choose_import

    private val viewModel by activityViewModels<ScheduleViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        dataBinding.ibClose.setOnClickListener {
            dismiss()
        }

        /**
         * 从Excel表格导入
         * todo 待完成
         */
        dataBinding.tvExcel.setOnClickListener {
            Toast.makeText(requireContext(), "正在努力开发中\n(；′⌒`)", Toast.LENGTH_SHORT).show()
            Log.d("ChooseImportFragment", "initEvent: 1")
            // showSAFTips {
            //     requireActivity().startActivityForResult(
            //         Intent(activity, LoginWebActivity::class.java).apply {
            //             putExtra("import_type", "excel")
            //             putExtra("tableId", viewModel.table.id)
            //         },
            //         Const.REQUEST_CODE_IMPORT
            //     )
            //     this.dismiss()
            // }
        }

        /**
         * 从学校教务系统导入
         */
        dataBinding.tvSchool.setOnClickListener {
            val dataBase = AppDatabase.getDatabase(requireActivity())
            val tableDao = dataBase.tableDao()
            launch {
                val tableId = tableDao.getDefaultTableId()
                requireActivity().startActivityForResult(
                    Intent(activity, LoginWebActivity::class.java).apply {
                        putExtra("url", "http://www.zfjw.xupt.edu.cn/jwglxt/")
                        putExtra("importType", Common.TYPE_ZF_NEW)
                        putExtra("tableId", tableId)

                    },
                    Const.REQUEST_CODE_IMPORT
                )
                dismiss()
            }
        }
    }

    private fun showSAFTips(block: () -> Unit) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("提示")
            .setMessage("为了避免使用敏感的外部存储读写权限，本应用采用了系统级的文件选择器来选择文件。如果找不到路径，请点选择器右上角的三个点，选择「显示内部存储设备」，然后通过侧栏选择路径。")
            .setNeutralButton("查看图文教程") { _, _ ->
                Utils.openUrl(
                    requireActivity(),
                    "https://support.qq.com/embed/phone/97617/faqs/59884"
                )
            }
            .setPositiveButton("确定") { _, _ ->
                block.invoke()
            }
            .setNegativeButton("取消", null)
            .setCancelable(false)
            .show()
    }
}